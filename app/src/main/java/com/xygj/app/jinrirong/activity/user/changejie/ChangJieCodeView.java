package com.xygj.app.jinrirong.activity.user.changejie;

import com.xygj.app.common.base.BaseView;

public interface ChangJieCodeView extends BaseView {
    void onSendPaySmsCode(String respond);
    void onPayFinish(String respond);
}
