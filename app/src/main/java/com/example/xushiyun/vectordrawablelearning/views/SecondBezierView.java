package com.example.xushiyun.vectordrawablelearning.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xushiyun on 2018/1/23.
 * Project Name: VectorDrawableLearning
 * Package Name: com.example.xushiyun.vectordrawablelearning.views
 * File Name:    SecondBezierView
 * Descripetion: Todo
 */

public class SecondBezierView extends View {
    //首先确定起点和终点的坐标
    //高阶Bezier曲线都可以通过二三阶曲线进行绘制

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private float mFlagPointX;
    private float mFlagPointY;

    private Path mPath;

    private Paint mPaintBezier;
    private Paint mPaintFlag;

    //问文字画笔
    private Paint mPaintFlagText;

    public SecondBezierView(Context context) {
        super(context);
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //在构造方法中对pen进行初始化
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintFlag = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(3);//设置不同的宽度= =,以便于区别
        mPaintFlag.setStyle(Paint.Style.STROKE);

        mPaintFlagText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlagText.setStyle(Paint.Style.STROKE);
        mPaintFlagText.setTextSize(20);
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointX = w / 2;
        mFlagPointY = h / 2 - 300;

        //然后通过android的bezier曲线绘制api绘制相应的曲线

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //首先将坐标移动到相应的起始点
        mPath.moveTo(mStartPointX, mStartPointY);
        //quad和rquad区别是前者是绝对坐标,后者是相对坐标,这里选择绝对坐标的方式来绘制
        //前者是控制点坐标,后者是终点坐标
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);
        canvas.drawPoint(mStartPointX, mStartPointY, mPaintFlag);
        canvas.drawText("起点", mStartPointX, mStartPointY, mPaintFlagText);
        canvas.drawPoint(mFlagPointX, mFlagPointY, mPaintFlag);
        canvas.drawText("控制点", mFlagPointX, mFlagPointY, mPaintFlagText);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintFlag);
        canvas.drawText("终点", mEndPointX, mEndPointY, mPaintFlagText);
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointX, mFlagPointY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mFlagPointX = event.getX();
                mFlagPointY = event.getY();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
