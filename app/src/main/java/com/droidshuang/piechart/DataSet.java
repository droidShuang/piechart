package com.droidshuang.piechart;

import java.util.List;

/**
 * Created by rees_yang on 2017/10/16.
 */

public class DataSet {
    private List<Series> mYVals;
    private List<String> mXVals;

    private float mYMax = 0.0f;
    private float mYMin = 0.0f;
    private float mYSum = 0.0f;


    public DataSet(List<Series> mYVals) {
        this.mYVals = mYVals;
        if (mYVals == null || mYVals.isEmpty()) {
            return;
        }
        calcMinMax();
        calcYSum();
    }

    /**
     * Name: calcMinMax
     * Params:
     * RETURN:
     * Author: rees_yang
     * Comment: //计算y的最大值、最小值
     * Date: 2017/10/16
     */
    private void calcMinMax() {
        mYMax = mYVals.get(0).getmVal();
        mYMin = mYVals.get(0).getmVal();
        for (Series series :
                mYVals) {
            if (series.getmVal() > mYMax) {
                mYMax = series.getmVal();
            }
            if (series.getmVal() < mYMin) {
                mYMin = series.getmVal();
            }
        }
    }

    private void calcYSum() {
        for (Series series : mYVals) {
            mYSum = mYSum + series.getmVal();
        }
    }

    public List<Series> getmYVals() {
        return mYVals;
    }

    public void setmYVals(List<Series> mYVals) {
        this.mYVals = mYVals;
    }

    public List<String> getmXVals() {
        return mXVals;
    }

    public void setmXVals(List<String> mXVals) {
        this.mXVals = mXVals;
    }

    public float getmYMax() {
        return mYMax;
    }

    public void setmYMax(float mYMax) {
        this.mYMax = mYMax;
    }

    public float getmYMin() {
        return mYMin;
    }

    public void setmYMin(float mYMin) {
        this.mYMin = mYMin;
    }

    public float getmYSum() {
        return mYSum;
    }

    public void setmYSum(float mYSum) {
        this.mYSum = mYSum;
    }
}
