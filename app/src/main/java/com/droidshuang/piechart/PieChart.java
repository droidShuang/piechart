package com.droidshuang.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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
    private float[] mAbsoluteAngles;
    private float mOffsetTop;
    private float mOffsetBottom;
    private float mOffsetLeft;
    private float mOffsetRight;
    private DataSet dataSet;
    private float mShift = 20f;
    private float mTextSize = 12f;
    private Context context;
    private List<Integer> colorList;
    private float radius;
    //强调突出的序列
    private int hightLightIndex = 0;

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
        series.add(new Series(10f, 0));
        series.add(new Series(10f, 1));
        series.add(new Series(10f, 2));
        series.add(new Series(10f, 3));
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
        drawHightlight(canvas);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getHightLightIndex() {
        return hightLightIndex;
    }

    public void setHightLightIndex(int hightLightIndex) {
        this.hightLightIndex = hightLightIndex;
    }

    private void drawStroke(Canvas canvas) {
        float padding = 10f;
        mOffsetRight = getWidth();
        mOffsetBottom = getHeight() / 2;
        radius = getWidth() / 2;
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
            if (i == 0) {
                mChartPaint.setColor(Color.WHITE);
            }

            canvas.drawArc(mCircleBox, angle, mDrawAngles[i], true, mChartPaint);
            angle = angle + mDrawAngles[i];
        }

    }
    //偏移圆心
    private void drawHightlight(Canvas canvas) {
        if (hightLightIndex == -1)
            return;
        int index = hightLightIndex;
        float angle = 0f;
        float sliceDegrees = mDrawAngles[index];
        float chartAngle = 0f;
        if (index == 0) {
            chartAngle = mDrawAngles[0];
        } else {
            for (int i = 0; i < index; i++) {
                chartAngle = chartAngle + mDrawAngles[i];
            }
        }
        float shiftangle = 360f - chartAngle - sliceDegrees ;

        float xShift = getRadius()/20 * (float) Math.cos(shiftangle);
        float yShift = getRadius()/20 * (float) Math.sin(shiftangle);
        RectF highlighted = new RectF(mCircleBox.left + xShift, mCircleBox.top - yShift, mCircleBox.right +xShift, mCircleBox.bottom - yShift);

        canvas.drawArc(highlighted, chartAngle , sliceDegrees, true, mChartPaint);

    }

    private void calcAngles() {
        mDrawAngles = new float[dataSet.getmYVals().size()];
        mAbsoluteAngles = new float[dataSet.getmYVals().size()];
        for (int i = 0; i < dataSet.getmYVals().size(); i++) {

            mDrawAngles[i] = calcAngle(dataSet.getmYVals().get(i).getmVal());
            if (i == 0) {
                mAbsoluteAngles[i] = mDrawAngles[0];
            } else {
                mAbsoluteAngles[i] = mAbsoluteAngles[i - 1] + mDrawAngles[i];
            }
        }
    }

    private float calcAngle(float value) {
        return value / dataSet.getmYSum() * 360f;
    }

    public float distanceToCenter(float x, float y) {
        PointF centerPoint = getCenter();
        float xDist;
        float yDist;
        if (x > centerPoint.x) {
            xDist = x - centerPoint.x;
        } else {
            xDist = centerPoint.x - x;
        }

        if (y > centerPoint.y) {
            yDist = y - centerPoint.y;
        } else {
            yDist = centerPoint.y - y;
        }
        float dist = (float) Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0));
        return dist;
    }

    public float getAngleForPoint(float x, float y) {
        PointF c = getCenter();
        double tx = x - c.x;
        double ty = y - c.y;
        double length = Math.sqrt(tx * tx + ty * ty);
        double r = Math.asin(ty / length);

        float angle = (float) Math.toDegrees(r);
        //第一象限
        if (x > c.x && y > c.y) {

        }
        //第四象限
        else if (x > c.x && y < c.y) {
            angle = 360f - angle;
        }
        //第二象限
        else if (x < c.x && y > c.y) {
            angle = angle + 90f;
        }
        //第三象限
        else {
            angle = angle + 180f;
        }
        if (x > c.x) {
            angle = 360f - angle;
        }
        angle = angle + 90;

        if (angle > 360f) {
            angle = angle - 360f;
        }


        return angle;
    }

    //获取该角度所属数据的index
    public int getIndexForAngle(float angle) {
        List<Series> yValues = dataSet.getmYVals();
        float chartAngle = 0f;
        for (int i = 0; i < yValues.size(); i++) {
            chartAngle = chartAngle + yValues.get(i).getmVal();
            if ((360f - chartAngle) < angle) {
                return i;
            }
        }
        return -1;
    }

    private PointF getCenter() {
        return new PointF(getWidth() / 2, getHeight() / 2);
    }
}
