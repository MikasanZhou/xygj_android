package com.xygj.app.jinrirong.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by xuyougen on 2018/4/16.
 */

public class CustomScrollView extends NestedScrollView {
    private OnScrollOffsetChangedListener mScrollOffsetChangedListener;

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollOffsetChangedListener != null) {
            mScrollOffsetChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setScrollOffsetChangedListener(OnScrollOffsetChangedListener scrollOffsetChangedListener) {
        mScrollOffsetChangedListener = scrollOffsetChangedListener;
    }

    public interface OnScrollOffsetChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
