package com.example.xushiyun.vectordrawablelearning.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by xushiyun on 2018/1/23.
 * Project Name: VectorDrawableLearning
 * Package Name: com.example.xushiyun.vectordrawablelearning.views
 * File Name:    DrawPadView
 * Descripetion: Todo
 */

public class PathMorphingBezierView extends View implements View.OnClickListener{
    //首先确定起点和终点的坐标

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    //二阶和三阶的区别是有几个中间点的区别,中间点的个数
    private float mFlagPoint1X;
    private float mFlagPoint1Y;
    private float mFlagPoint2X;
    private float mFlagPoint2Y;

    private Path mPath;

    private Paint mPaintBezier;
    private Paint mPaintFlag;

    //问文字画笔
    private Paint mPaintFlagText;

    //标记是否触发多点触控
    private boolean isSecondPoint = false;

    private ValueAnimator mValueAnimator;

    public PathMorphingBezierView(Context context) {
        super(context);
    }

    public PathMorphingBezierView(Context context, @Nullable AttributeSet attrs) {
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

    public PathMorphingBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPoint1X = mStartPointX;
        mFlagPoint1Y = mStartPointY;
        mFlagPoint2X = mEndPointX;
        mFlagPoint2Y = mEndPointY;

        //然后通过android的bezier曲线绘制api绘制相应的曲线
        mValueAnimator = ValueAnimator.ofFloat(mStartPointY, h);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.setDuration(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPoint1X = (float) mValueAnimator.getAnimatedValue();
                mFlagPoint2Y = (float) mValueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //首先将坐标移动到相应的起始点
        mPath.moveTo(mStartPointX, mStartPointY);
        //quad和rquad区别是前者是绝对坐标,后者是相对坐标,这里选择绝对坐标的方式来绘制
        //前者是控制点坐标,后者是终点坐标
//        mPath.quadTo(mFlagPoint1X, mFlagPoint1Y, mEndPointX, mEndPointY);
//        cubicTo同样有相对和绝对两个表示方式
        mPath.cubicTo(mFlagPoint1X, mFlagPoint1Y, mFlagPoint2X, mFlagPoint2Y, mEndPointX, mEndPointY);

        canvas.drawPoint(mStartPointX, mStartPointY, mPaintFlag);
        canvas.drawText("起点", mStartPointX, mStartPointY, mPaintFlagText);
        canvas.drawPoint(mFlagPoint1X, mFlagPoint1Y, mPaintFlag);
        canvas.drawText("控制点1", mFlagPoint1X, mFlagPoint1Y, mPaintFlagText);
        canvas.drawText("控制点2", mFlagPoint2X, mFlagPoint2Y, mPaintFlagText);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintFlag);
        canvas.drawText("终点", mEndPointX, mEndPointY, mPaintFlagText);
        //绘制相关点之间的连线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPoint1X, mFlagPoint1Y, mPaintFlag);
        canvas.drawLine(mFlagPoint1X, mFlagPoint1Y, mFlagPoint2X, mFlagPoint2Y, mPaintFlag);
        canvas.drawLine(mFlagPoint2X, mFlagPoint2Y, mEndPointX, mEndPointY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //这里不需要使用手动来进行动画的操作,这里只需要使用ValueAnimator即可
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
