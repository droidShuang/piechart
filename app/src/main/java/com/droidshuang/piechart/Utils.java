package com.droidshuang.piechart;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by rees_yang on 2017/10/13.
 */

public class Utils {

    private static Resources mRes;

    /**
     * initialize method, called inside the Char.init() method.
     * @param res
     */
    public static void init(Resources res) {
        mRes = res;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on
     * device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we
     *            need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on
     *         device density
     */
    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = mRes.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    /**
     * This method converts device specific pixels to density independent
     * pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into
     *            db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = mRes.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

}
