package com.xygj.app.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 懒加载 Fragment 基类，即子类 Fragment 对用户可见时，才进行数据加载
 * 继承自{@link BaseFragment}
 * Created by xuyougen on 2018/3/1.
 */

public abstract class BaseLazyLoadFragment extends BaseFragment {

    //标记，数据是否已经加载
    private boolean mIsDataLoaded;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startLoadData();
    }

    /**
     * 当前fragment是否可见
     * 例如：当ViewPager左右切换时，就会调用此方法，设置当前fragment是否对用户可见
     *
     * @param isVisibleToUser true 为可见，反之不可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //当对用户可见时，进行加载数据
        if (isVisibleToUser) {
            startLoadData();
        }
        onVisibilityChanged(isVisibleToUser);
    }

    /**
     * 当fragment显示状态发生改变时
     */
    protected void onVisibilityChanged(boolean isVisibleToUser) {
    }

    /**
     * 开始加载数据
     */
    private void startLoadData() {
        if (getUserVisibleHint() && getView() != null) {
            //如果数据未加载，加载数据
            if (!mIsDataLoaded) {
                lazyLoadData();
                mIsDataLoaded = true;
            }
        }
    }

    /**
     * 具体需要加载的数据交给子类来实现
     */
    protected abstract void lazyLoadData();

}
