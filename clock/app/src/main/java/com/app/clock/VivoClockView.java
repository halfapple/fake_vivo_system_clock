package com.app.clock;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class VivoClockView extends View {

    //bg
    private float mCentX;
    private float mCentY;
    private float mRadius;
    private int bgColor = Color.parseColor("#000000");

    //marker
    private float marker1_width = 4;
    private int marker1_color = Color.parseColor("#D6D6D6");
    private float marker2_width = 4;
    private int marker2_color = Color.parseColor("#919191");
    private float marker_length = 20;
    private float marker_margin = 10;

    //hour minute second
    private float secondHandWidth = 6;
    private int secondHandColor;

    private float minuteHandWidth = 8;
    private int minuteHandColor;

    private float hourHandWidth = 10;
    private int hourHandColor;

    //center circle
    private float mCenterCircleRadius1;
    private float mCenterCircleRadius2;

    private Paint mPaint;

    private int currentHour;
    private int currentMin;
    private int currentSec;

    public VivoClockView(Context context) {
        this(context, null);
    }

    public VivoClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VivoClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VivoClockView, defStyleAttr, 0);
        int count = ta.getIndexCount();

        for (int i = 0; i < count; i++) {

            int index = ta.getIndex(i);

            switch (index) {
                case R.styleable.VivoClockView_bg_color:
                    bgColor = ta.getColor(index, Color.parseColor("#000000"));
                    break;

                case R.styleable.VivoClockView_marker_margin:
                    marker_margin = ta.getDimension(index, 15);
                    break;

                case R.styleable.VivoClockView_marker_length:
                    marker_length = ta.getDimension(index, 20);
                    break;

                case R.styleable.VivoClockView_marker_color1:
                    marker1_color = ta.getColor(index, Color.parseColor("#D6D6D6"));
                    break;

                case R.styleable.VivoClockView_marker_color2:
                    marker2_color = ta.getColor(index, Color.parseColor("#919191"));
                    break;

                case R.styleable.VivoClockView_hourColor:
                    hourHandColor = ta.getColor(index, Color.parseColor("#ffffff"));
                    break;

                case R.styleable.VivoClockView_hourHandWidth:
                    hourHandWidth = ta.getDimension(index, 10f);
                    break;

                case R.styleable.VivoClockView_minColor:
                    minuteHandColor = ta.getColor(index, Color.parseColor("#ffffff"));
                    break;

                case R.styleable.VivoClockView_minHandWidth:
                    minuteHandWidth = ta.getDimension(index, 8f);
                    break;

                case R.styleable.VivoClockView_secHandWidth:
                    secondHandWidth = ta.getDimension(index, 6f);
                    break;

                case R.styleable.VivoClockView_secColor:
                    secondHandColor = ta.getColor(index, Color.parseColor("#00000000"));
                    break;

                case R.styleable.VivoClockView_centerCircleR1:
                    mCenterCircleRadius1 = ta.getDimension(index, 4f);
                    break;

                case R.styleable.VivoClockView_centerCircleR2:
                    mCenterCircleRadius2 = ta.getDimension(index, 6f);
                    break;
            }
        }

        ta.recycle();

        setPara();
    }

    public void setTime(int hour, int min, int sec) {
        this.currentHour = hour;
        this.currentMin = min;
        this.currentSec = sec;

        invalidate();
    }

    private void setPara() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);;
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = 0;
        int height = 0;

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + size + getPaddingRight();
                break;

            case MeasureSpec.AT_MOST:
                //todo
                break;
        }

        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + size + getPaddingBottom();
                break;

            case MeasureSpec.AT_MOST:
                //todo
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadius = w < h ? w / 2 : h / 2;
        mCentX = w / 2;
        mCentY = h / 2;
    }




    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(bgColor);
        canvas.drawCircle(mCentX, mCentY, mRadius, mPaint);

        //marker
        canvas.translate(mCentX, mCentY);
        for (int i = 0; i < 12; i++) {

            if (i % 3 == 0) {
                mPaint.setStrokeWidth(marker1_width);
                mPaint.setColor(marker1_color);
            } else {
                mPaint.setStrokeWidth(marker2_width);
                mPaint.setColor(marker2_color);
            }

            canvas.drawLine(0, -mRadius + marker_length + marker_margin, 0,
                    -mRadius + marker_margin, mPaint);
            canvas.rotate(30);
        }

        //hour, min, sec
        canvas.save();
        canvas.rotate(currentHour*30 + (currentMin / 2f));
        mPaint.setColor(hourHandColor);
        mPaint.setStrokeWidth(hourHandWidth);
        canvas.drawLine(0, 0, 0, -mRadius  / 2, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(currentMin*6 + (currentSec / 10f));
        mPaint.setColor(minuteHandColor);
        mPaint.setStrokeWidth(minuteHandWidth);
        canvas.drawLine(0, 0, 0, -mRadius + marker_length + 3*marker_margin, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(currentSec*6);
        mPaint.setColor(secondHandColor);
        mPaint.setStrokeWidth(secondHandWidth);
        canvas.drawLine(0, 0, 0, -mRadius + marker_length + 3*marker_margin, mPaint);
        canvas.restore();

        mPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(0, 0, mCenterCircleRadius2, mPaint);

        mPaint.setColor(Color.parseColor("#ff7f50"));
        canvas.drawCircle(0, 0, mCenterCircleRadius1, mPaint);


    }


}











