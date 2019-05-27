package com.ilifesmart.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 单位转换工具类
 */
public class DensityUtils {
    private DensityUtils() {  
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static void getWindowSize(Context context, int[] result) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        result[0] = point.x;
        result[1] = point.y;
    }
}