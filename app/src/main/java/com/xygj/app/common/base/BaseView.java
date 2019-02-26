package com.xygj.app.common.base;

/**
 * Created by xuyougen on 2018/4/11.
 */

public interface BaseView {
    public void showLoadingView();
    public void hideLoadingView();
    public void onTokenInvalidate();
    public void onNetworkConnectFailed();
}
