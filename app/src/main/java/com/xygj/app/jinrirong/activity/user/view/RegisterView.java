package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.HtmlData;

/**
 * Created by xuyougen on 2018/4/12.
 */

public interface RegisterView extends BaseView {
    void onRegisterSucceed(String msg);

    void onRegisterFailed(String msg);

    void showCaptchaDialog(HttpRespond<CaptchaBean> respond);

    void onSendSmsComplete(HttpRespond respond);

    void onRegisterComplete(HttpRespond respond);

    void onGetRegisterProtocolSucceed(HtmlData htmlData);
}
