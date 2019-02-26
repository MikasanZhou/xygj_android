package com.xygj.app.jinrirong.activity.user.view;


import com.xygj.app.common.base.BaseView;

/**
 * Created by xuyougen on 2018/3/13.
 */

public interface UpdatePwdView extends BaseView {
    void onChangePwdSucceed(String msg);
    void onChangePwdFailed(String msg);
}
