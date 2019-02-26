package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/21.
 */

public interface ForgetPwdView extends BaseView {
    void showCaptchaDialog(HttpRespond<CaptchaBean> respond);

    void onSendSmsComplete(HttpRespond respond);

    void onVerifySmsCodeDone(HttpRespond respond);
}
