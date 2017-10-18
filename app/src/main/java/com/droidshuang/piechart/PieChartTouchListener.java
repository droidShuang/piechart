package com.droidshuang.piechart;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rees_yang on 2017/10/18.
 */

public class PieChartTouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    PieChart chart;

    public PieChartTouchListener(PieChart chart) {

        this.chart = chart;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        float distance = chart.distanceToCenter(x, y);
        if (distance > chart.getRadius())
            return true;
        chart.setHightLightIndex(chart.getIndexForAngle(chart.getAngleForPoint(x, y)));

        return true;
    }
}
