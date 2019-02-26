package com.xygj.app.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Activity 基类，封装了一些通用的功能
 * Created by xuyougen on 2018/3/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    //所有子类Activity集合
    private static final List<WeakReference<BaseActivity>> activityList = new ArrayList<>();
    //当前类的弱引用对象
    private WeakReference<BaseActivity> mWeakReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //每启动一个Activity，将添加到集合中
        mWeakReference = new WeakReference<BaseActivity>(this);
        activityList.add(mWeakReference);
        //设置布局
        setContentView(setContentLayoutRes());
        //ButterKnife 不需要就注释掉
        ButterKnife.bind(this);
        //开始进行初始化
        initView();
        initData();
    }

    /**
     * 设置布局资源
     *
     * @return
     */
    protected abstract @LayoutRes
    int setContentLayoutRes();

    /**
     * 初始化控件，交给子类实现
     */
    protected abstract void initView();

    /**
     * 初始化数据，交给子类实现
     */
    protected abstract void initData();

    /**
     * 设置透明状态栏（API21，5.0之后才能用）
     *
     * @param color       通知栏颜色，完全透明填 Color.TRANSPARENT 即可
     * @param isLightMode 是否为亮色模式（黑色字体，需要6.0 以后支持，否则显示无效）
     */
    protected void requestTranslucentStatusBar(int color, boolean isLightMode) {
        //大于5.0才设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //大于6.0 并且是亮色模式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLightMode) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            getWindow().setStatusBarColor(color);
        }
    }

    /**
     * 结束所有的Activity
     */
    public static void closeAllActivities() {
        for (WeakReference<BaseActivity> baseActivityWeakReference : activityList) {
            BaseActivity baseActivity = baseActivityWeakReference.get();
            if (baseActivity != null) {
                baseActivity.finish();
            }
        }
    }

    /**
     * 结束所有的Activity，除了。。。传入的class类型
     */
    public static void closeAllActivitiesExcept(Class<? extends BaseActivity> clazz) {
        for (WeakReference<BaseActivity> baseActivityWeakReference : activityList) {
            BaseActivity baseActivity = baseActivityWeakReference.get();
            if (baseActivity != null) {
                if (baseActivity.getClass().equals(clazz)) {
                    continue;
                }
                baseActivity.finish();
            }
        }
    }

    /**
     * finish Activity，提供给xml布局控件的onclick使用
     *
     * @param view
     */
    public void finishPage(View view) {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
