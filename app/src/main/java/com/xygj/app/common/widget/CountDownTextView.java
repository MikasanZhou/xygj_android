package com.xygj.app.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

/**
 * 用于倒计时的 TextView
 * 使用方法 startCountDown(int sec)
 * Created by xuyougen on 2018/4/12.
 */

public class CountDownTextView extends android.support.v7.widget.AppCompatTextView {

    private RectF mRectF;
    private Paint mPaint;
    private CountDownHandler mCountDownHandler;

    //正常描边色
    private int mNormalStrokeColor;
    //禁用描边色
    private int mDisabledStrokeColor = Color.parseColor("#BBBBBB");
    //描边的宽度
    private int mStrokeWidth = 1;

    private String mDefaultString;
    private String mSecString = "秒";

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        //从资源文件获取颜色
//        mNormalStrokeColor = ContextCompat.getColor(context, R.color.colorPrimary);
//        mDisabledStrokeColor = ContextCompat.getColor(context, R.color.colorLightGray);
        mCountDownHandler = new CountDownHandler(this);
        mDefaultString = getText().toString();
        mNormalStrokeColor = getCurrentTextColor();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(mStrokeWidth, mStrokeWidth, w - 2 * mStrokeWidth, h - 2 * mStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isEnabled()) {
            mPaint.setColor(mNormalStrokeColor);
            this.setTextColor(mNormalStrokeColor);
        } else {
            mPaint.setColor(mDisabledStrokeColor);
            this.setTextColor(mDisabledStrokeColor);
        }
        canvas.drawRoundRect(mRectF, getHeight() / 2, getHeight() / 2, mPaint);
    }

    public void startCountDown(int time) {
        if (!mCountDownHandler.isCountDownFinished()) return;
        mCountDownHandler.startCountDown(time, mDefaultString, mSecString);
    }

    public void setNormalStrokeColor(@ColorInt int normalStrokeColor) {
        mNormalStrokeColor = normalStrokeColor;
        invalidate();
    }

    public void setDisabledStrokeColor(@ColorInt int disabledStrokeColor) {
        mDisabledStrokeColor = disabledStrokeColor;
        invalidate();
    }

    public void setNormalStrokeColorRes(@ColorRes int colorRes) {
        mNormalStrokeColor = ContextCompat.getColor(getContext(), colorRes);
        invalidate();
    }

    public void setDisabledStrokeColorRes(@ColorRes int colorRes) {
        mDisabledStrokeColor = ContextCompat.getColor(getContext(), colorRes);
        invalidate();
    }

    private static class CountDownHandler extends Handler {
        private int mCountDownTime;
        private WeakReference<CountDownTextView> mWeakReference;
        private static final int WHAT_COUNT_DOWN_TIME = 0x23;

        private String mSecStr;
        private String mResetStr;

        CountDownHandler(CountDownTextView countDownTextView) {
            mWeakReference = new WeakReference<CountDownTextView>(countDownTextView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!(mCountDownTime <= 0)) {
                mCountDownTime--;
                if (mWeakReference.get() != null) {
                    mWeakReference.get().setText(formatNumStr(mCountDownTime) + mSecStr);
                    this.sendEmptyMessageDelayed(WHAT_COUNT_DOWN_TIME, 1000);
                } else {
                    this.removeCallbacksAndMessages(null);
                }
            } else {
                if (mWeakReference.get() != null) {
                    mWeakReference.get().setText(mResetStr);
                    mWeakReference.get().setEnabled(true);
                }
                this.removeCallbacksAndMessages(null);
            }
        }

        @SuppressLint("SetTextI18n")
        void startCountDown(int countDownTime, String resetStr, String secStr) {
            mSecStr = secStr;
            mResetStr = resetStr;
            mCountDownTime = countDownTime;
            if (mCountDownTime <= 0) return;
            if (mWeakReference.get() != null) {
                mWeakReference.get().setText(formatNumStr(mCountDownTime) + mSecStr);
                mWeakReference.get().setEnabled(false);
            }
            this.sendEmptyMessageDelayed(WHAT_COUNT_DOWN_TIME, 1000);
        }

        boolean isCountDownFinished() {
            return mCountDownTime <= 0;
        }

        private String formatNumStr(int num) {
            return num < 10 ? "0" + num : String.valueOf(num);
        }
    }
}
