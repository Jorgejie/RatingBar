package org.jaaksi.view.fcy1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.jaaksi.view.fcy1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fcy on 2015/12/17.<br/>
 * 利用CheckBox实现的自定义 RatingBar 支持显示半颗星（仅不可点击时）
 * 半颗星与整个星不同时支持，不要同时使用setRating()和setRatings()
 */
public class MyRatingBar extends LinearLayout
{
	int num = 1;
	int spacing = 0;
	int width = 10;
	int height = 10;
	int resId;

	/**
	 * 是否允许点击改变星星状态
	 */
	private boolean isClickEnable = true;

	private List<CheckBox> list = new ArrayList<>();

	public MyRatingBar(Context context)
	{
		super(context);
	}

	public MyRatingBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		if (attrs != null)
		{
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
			        R.styleable.MyRatingBar);
			isClickEnable = typedArray
			        .getBoolean(R.styleable.MyRatingBar_click_enable, true);
			num = typedArray.getInteger(R.styleable.MyRatingBar_num, 1);
			spacing = typedArray
			        .getDimensionPixelSize(R.styleable.MyRatingBar_space, 1);
			resId = typedArray.getResourceId(
			        R.styleable.MyRatingBar_rating_background, -2);
			try
			{
				width = typedArray.getDimensionPixelSize(
				        R.styleable.MyRatingBar_rating_width, 0);
			} catch (Exception ex)
			{
				width = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			try
			{
				height = typedArray.getDimensionPixelSize(
				        R.styleable.MyRatingBar_rating_height, 0);
			} catch (Exception ex)
			{
				height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}

			typedArray.recycle();
		}
		initView();
	}

	public int getMax()
	{
		return num;
	}

	/**
	 * @return 选中的星星个数
	 */
	public int getRating()
	{
		for (int i = list.size(); i > 0; i--)
		{
			if (list.get(i - 1).isChecked())
			{
				return i;
			}
		}
		return 0;
	}

	private float ratings = 0;

	/**
	 * 必须调用setRatings
	 * 
	 * @return
	 */
	public float getRatings()
	{
		return ratings;
	}

	/**
	 * 设置半颗星，必须不可点击时才有效
	 * 
	 * @param count
	 */
	public void setRatings(float count)
	{
		this.ratings = count;
		int a = (int) count;
		setRating(a);
		int round = Math.round(count);
		if (!isClickEnable && round != a && round <= num)
		{
			list.get(a).setEnabled(false);
		}
	}

	/**
	 * @param count
	 */
	public void setRating(int count)
	{
		// ratings = count;
		for (int i = 0; i < list.size(); i++)
		{
			if (i < count)
			{
				list.get(i).setChecked(true);
			}
			else
			{
				list.get(i).setChecked(false);
			}
			list.get(i).setEnabled(true);
		}
	}

	private void initView()
	{
		removeAllViews();
		// list = new ArrayList<CheckBox>()
		for (int i = 0; i < num; i++)
		{
			CheckBox checkBox = new CheckBox(getContext());
			list.add(checkBox);

			if (isClickEnable)
			{ // checkbox的clickable=false时，如果不设置 点击事件，将不能被点击
				checkBox.setOnClickListener(new MyClickListener(i));
			}
			else
			{
				checkBox.setClickable(false);
				// 如果列表item需要相应点击事件，可在item根布局添加android:descendantFocusability="blocksDescendants"
				// checkBox.setFocusableInTouchMode(false);
				// checkBox.setFocusable(false);
			}
			LayoutParams params = new LayoutParams(width, height);
			if (i == 0)
			{
				params.leftMargin = 0;
			}
			else
			{
				params.leftMargin = spacing;
			}
			// checkBox.setButtonDrawable(null); // 这么设置是无效的

			if (resId != -2)
			{
				if (height == ViewGroup.LayoutParams.WRAP_CONTENT)
				{
					checkBox.setButtonDrawable(resId);
				}
				else
				{
					checkBox.setBackgroundResource(resId);
					checkBox.setButtonDrawable(new ColorDrawable());
				}

			}

			addView(checkBox, params);

		}
	}

	private TextView descView;
	private String[] descArray;

	/**
	 * 绑定一个TextView和数组
	 * 
	 * @param textView
	 * @param descArray
	 *            描述数组 size应该与星条个数一致，本地数据可以在arrays.xml中配置
	 */
	public void bindDescView(TextView textView, String[] descArray)
	{
		this.descView = textView;
		this.descArray = descArray;
		initDescContent(getRating());
	}

	/**
	 *
	 * @param count
	 *            选中的星星个数
	 */
	private void initDescContent(int count)
	{
		if (descView != null && descArray != null
		        && descArray.length >= num + 1)
		{ // 有0个也需要设置文案的需要
			descView.setText(descArray[count]);
		}

	}

	class MyClickListener implements OnClickListener
	{
		private int position;

		public MyClickListener(int position)
		{
			this.position = position;
		}

		@Override
		public void onClick(View v)
		{
			CheckBox checkBox = (CheckBox) v;
			checkBox.setChecked(true);
			for (int i = 0; i < list.size(); i++)
			{
				if (i <= position)
				{
					list.get(i).setChecked(true);
				}
				else
				{
					list.get(i).setChecked(false);
				}
			}
			initDescContent(position + 1);

		}
	}

}
