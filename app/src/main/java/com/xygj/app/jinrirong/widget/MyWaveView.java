package com.xygj.app.jinrirong.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 个人页面水波背景View
 * Created by xuyougen on 2018/3/5.
 */

public class MyWaveView extends View {
    private Paint mPaint;
    private Path mWavePath1;
    private Path mWavePath2;
    private LinearGradient mLinearGradient;

    //动画进度
    private float mAnimProgress = 0f;
    //水波颜色
    private int mWaveColor = Color.parseColor("#28FFFFFF");
    private int mWaveColor2 = Color.WHITE;

    //渐变背景的起始和结束颜色
    private int mGradientStartColor = Color.rgb(72, 187, 245);
    private int mGradientEndColor = Color.parseColor("#5461eb");

    public MyWaveView(Context context) {
        this(context, null);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLinearGradient = new LinearGradient(
                0, 0,
                w, 0,
                mGradientStartColor, mGradientEndColor,
                Shader.TileMode.CLAMP);

        mWavePath1 = new Path();
        mWavePath1.moveTo(0, h);
        mWavePath1.lineTo(0, h / 2);
        mWavePath1.rQuadTo(w / 4, h / 16, w / 2, 0);
        mWavePath1.rQuadTo(w / 4, -h / 16, w / 2, 0);
        mWavePath1.rQuadTo(w / 4, h / 16, w / 2, 0);
        mWavePath1.rQuadTo(w / 4, -h / 16, w / 2, 0);
        mWavePath1.rLineTo(0, h / 2);
        mWavePath1.close();

        mWavePath2 = new Path();
        mWavePath2.moveTo(0, h);
        mWavePath2.lineTo(0, h / 2);
        mWavePath2.rQuadTo(w / 4, h / 14, w / 2, 0);
        mWavePath2.rQuadTo(w / 4, -h / 14, w / 2, 0);
        mWavePath2.rQuadTo(w / 4, h / 14, w / 2, 0);
        mWavePath2.rQuadTo(w / 4, -h / 14, w / 2, 0);
        mWavePath2.rQuadTo(w / 4, h / 14, w / 2, 0);
        mWavePath2.rQuadTo(w / 4, -h / 14, w / 2, 0);
        mWavePath2.rLineTo(0, h / 2);
        mWavePath2.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setShader(null);
        mPaint.setColor(mWaveColor);

        canvas.save();
        canvas.translate(-getWidth() * mAnimProgress, getHeight() / 3.2f);
        canvas.drawPath(mWavePath1, mPaint);
        canvas.restore();

        mPaint.setColor(mWaveColor2);

        canvas.save();
        canvas.translate(-getWidth() * 2 * mAnimProgress, getHeight() / 2.8f);
        canvas.drawPath(mWavePath2, mPaint);
        canvas.restore();
    }
}
