package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoginData;

/**
 * Created by xuyougen on 2018/4/12.
 */

public interface LoginView extends BaseView {
    void onLoginSucceed(String msg);

    void onLoginFailed(String msg);

    void onLoginComplete(HttpRespond<LoginData> respond);
    void onLoginFailed(HttpRespond<LoginData> respond);

    void onSendSmsComplete(HttpRespond respond);
}
