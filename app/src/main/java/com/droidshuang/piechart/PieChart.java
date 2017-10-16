package com.droidshuang.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rees_yang on 2017/10/16.
 */

public class PieChart extends View {
    private Paint mTextPaint;
    private Paint mChartPaint;
    private Paint mStrokePaint;
    private RectF mCircleBox;
    private float mChartAngle;
    private float[] mDrawAngles;
    private float mOffsetTop;
    private float mOffsetBottom;
    private float mOffsetLeft;
    private float mOffsetRight;
    private DataSet dataSet;

    private float mTextSize = 12f;
    private Context context;
    private List<Integer> colorList;

    public PieChart(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        Utils.init(context.getResources());
        mOffsetBottom = 0.0f;
        mOffsetTop = 0.0f;
        mOffsetLeft = 0.0f;
        mOffsetRight = 0.0f;
        mChartAngle = 0.0f;
        mTextPaint = new Paint();
        mTextPaint.setTextSize(Utils.convertDpToPixel(mTextSize));
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.BLACK);
        List<Series> series = new ArrayList<>();
        series.add(new Series(30f, 0));
        series.add(new Series(9f, 1));
        series.add(new Series(35f, 2));
        series.add(new Series(12f, 3));
        series.add(new Series(40f, 4));
        dataSet = new DataSet(series);
        colorList = new ArrayList<>();
        colorList.add(Color.RED);
        colorList.add(Color.YELLOW);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.DKGRAY);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (dataSet == null || dataSet.getmXVals().isEmpty()) {
//            return;
//        }
        drawStroke(canvas);
        drawData(canvas);

    }

    private void drawStroke(Canvas canvas) {
        float padding = 10f;
        mOffsetRight = getWidth();
        mOffsetBottom = getHeight() / 2;
        //mCircleBox = new RectF(mOffsetLeft + padding, mOffsetTop + padding, mOffsetRight - padding, mOffsetBottom - padding);
        mCircleBox = new RectF(100, 100, 500, 500);
        //     canvas.drawRect(mOffsetLeft + padding, mOffsetTop + padding, mOffsetRight - padding, mOffsetBottom - padding, mStrokePaint);
        canvas.drawRect(100, 100, 500, 500, mStrokePaint);
    }

    private void drawData(Canvas canvas) {
        calcAngles();
        float angle = mChartAngle;
        for (int i = 0; i < dataSet.getmYVals().size(); i++) {
            mChartPaint.setColor(colorList.get(i));

            canvas.drawArc(mCircleBox, angle, mDrawAngles[i], true, mChartPaint);
            angle = angle + mDrawAngles[i];
        }

    }

    private void calcAngles() {
        mDrawAngles = new float[dataSet.getmYVals().size()];
        for (int i = 0; i < dataSet.getmYVals().size(); i++) {
            mDrawAngles[i] = calcAngle(dataSet.getmYVals().get(i).getmVal());
        }
    }

    private float calcAngle(float value) {

        return value / dataSet.getmYSum() * 360f;
    }
}
