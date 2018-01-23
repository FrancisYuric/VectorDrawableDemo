package com.example.xushiyun.vectordrawablelearning.views;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.example.xushiyun.vectordrawablelearning.BezierUtil;

/**
 * Created by xushiyun on 2018/1/23.
 * Project Name: VectorDrawableLearning
 * Package Name: com.example.xushiyun.vectordrawablelearning.views
 * File Name:    BezierEvaluator
 * Descripetion: Todo
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF mFlagPoint;
    public BezierEvaluator(PointF flagPoint) {
        mFlagPoint = flagPoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(fraction, startValue, mFlagPoint, endValue);
    }
}
