package com.droidshuang.piechart;

/**
 * Created by rees_yang on 2017/10/13.
 */

public class Series {
    private float mVal = 0.0f;
    private int mXindex = 0;

    public Series(float mVal, int mXindex) {
        this.mVal = mVal;
        this.mXindex = mXindex;
    }

    public float getmVal() {
        return mVal;
    }

    public void setmVal(float mVal) {
        this.mVal = mVal;
    }

    public int getmXindex() {
        return mXindex;
    }

    public void setmXindex(int mXindex) {
        this.mXindex = mXindex;
    }
}
