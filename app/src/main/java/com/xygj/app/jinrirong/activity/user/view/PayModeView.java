package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PayInfoBean;

/**
 * Created by Yangli on 2018/4/23.
 */

public interface PayModeView extends BaseView {
    void onPayDone(HttpRespond<PayInfoBean> respond);
    void onPayFailed(String message);
}
