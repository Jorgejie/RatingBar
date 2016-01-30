package org.jaaksi.view.fcy1.view;

import android.content.Context;

/**
 * 单位转换工具类
 *
 * @author wanghl-a
 */
public class DensityUtil
{
	/**
	 * dip转换px
	 *
	 * @param context
	 *            上下文
	 * @param dpValue
	 *            dip值
	 * @return px值
	 */
	public static int dip2px(Context context, float dpValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转换dip
	 *
	 * @param context
	 *            上下文
	 * @param pxValue
	 *            px值
	 * @return dip值
	 */
	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
