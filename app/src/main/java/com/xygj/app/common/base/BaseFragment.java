package com.xygj.app.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Fragment 基类，封装一些常用的功能
 * 注意：使用的是v4包下的Fragment {@link Fragment}
 * Created by xuyougen on 2018/3/1.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 初始化控件，交给子类实现
     */
    protected abstract void initView();

    /**
     * 初始化数据，交给子类实现
     */
    protected abstract void initData();

}
