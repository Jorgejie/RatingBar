package org.jaaksi.view.fcy1.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class MyRatingBar extends LinearLayout {
    private int mCount = 1;
    private int mSpacing = 0;
    private int mWidth = 10;
    private int mHeight = 10;
    private int mResId;

    /**
     * 是否允许点击改变星星状态
     */
    private boolean mClickEnable = true;
    private float mRatings = 0;

    private TextView mDescView;
    private String[] mDescArray;
    private OnSeekChangedListener mOnSeekChangedListener;

    private final List<ImageView> mList = new ArrayList<>();

    public MyRatingBar(Context context) {
        super(context);
    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.MyRatingBar);
            mClickEnable = typedArray
                .getBoolean(R.styleable.MyRatingBar_click_enable, true);
            mCount = typedArray.getInteger(R.styleable.MyRatingBar_num, 1);
            mSpacing = typedArray
                .getDimensionPixelSize(R.styleable.MyRatingBar_space, 1);
            mResId = typedArray.getResourceId(
                R.styleable.MyRatingBar_rating_background, -2);
            try {
                mWidth = typedArray.getDimensionPixelSize(
                    R.styleable.MyRatingBar_rating_width, 0);
            } catch (Exception ex) {
                mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            try {
                mHeight = typedArray.getDimensionPixelSize(
                    R.styleable.MyRatingBar_rating_height, 0);
            } catch (Exception ex) {
                mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            typedArray.recycle();
        }
        initView();
    }

    private void initView() {
//        removeAllViews();
//        mList.clear();
        for (int i = 0; i < mCount; i++) {
            ImageView imageView = new ImageView(getContext());
            mList.add(imageView);

            if (mClickEnable) {
                imageView.setOnClickListener(new MyClickListener(i));
            } else {
//                imageView.setClickable(false);
            }
            LayoutParams params = new LayoutParams(mWidth, mHeight);
            if (i == 0) {
                params.leftMargin = 0;
            } else {
                params.leftMargin = mSpacing;
            }
            imageView.setImageResource(mResId);

            addView(imageView, params);

        }
    }

    public void setOnSeekChangedListener(OnSeekChangedListener onSeekChangedListener) {
        mOnSeekChangedListener = onSeekChangedListener;
    }

    public int getMax() {
        return mCount;
    }

    public float getRatings() {
        return mRatings;
    }

    /**
     * 设置半颗星，必须不可点击时才有效
     *
     * @param count
     */
    public void setRatings(float count) {
        this.mRatings = count;
        int a = (int) count;
        setRating(a);
        int round = Math.round(count);
        if (!mClickEnable && round != a && round <= mCount) {
            mList.get(a).setEnabled(false);
        }
    }

    /**
     * @param count
     */
    public void setRating(int count) {
        mRatings = count;
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(i < count);
            mList.get(i).setEnabled(true);
        }
    }

    /**
     * 绑定一个TextView和数组
     *
     * @param textView
     * @param descArray 描述数组 size应该与星条个数一致，本地数据可以在arrays.xml中配置
     */
    public void bindDescView(TextView textView, String[] descArray,
                             boolean isInit) {
        this.mDescView = textView;
        this.mDescArray = descArray;
        if (isInit) {
            initDescContent((int) getRatings());
        }
    }

    /**
     * 绑定一个TextView和数组并根据星星个数初始化
     *
     * @param textView
     * @param descArray 描述数组 size应该与星条个数一致，本地数据可以在arrays.xml中配置
     */
    public void bindDescView(TextView textView, String[] descArray) {
        bindDescView(textView, descArray, true);
    }

    /**
     * @param count 选中的星星个数
     */
    private void initDescContent(int count) {
        if (mDescView != null && mDescArray != null
            && mDescArray.length >= mCount + 1) { // 有0个也需要设置文案的需要
            mDescView.setText(mDescArray[count]);
        }

    }

    private class MyClickListener implements OnClickListener {
        private int position;

        public MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            int curr = position + 1;
            if (mRatings != curr) {
                if (mOnSeekChangedListener != null) {
                    mOnSeekChangedListener.onSeekChanged((int) mRatings, curr);
                }
                ImageView imageView = (ImageView) v;
                imageView.setSelected(true);
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelected(i <= position);
                }
                initDescContent(curr);
                mRatings = curr;
            }
        }
    }

    public interface OnSeekChangedListener {
        /**
         * @param lastCount 上次rating count
         * @param count     rating count
         */
        void onSeekChanged(int lastCount, int count);
    }

}
