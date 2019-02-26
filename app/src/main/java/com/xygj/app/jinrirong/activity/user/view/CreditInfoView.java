package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CreditInfoBean;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/27.
 */

public interface CreditInfoView extends BaseView {
    void showCreditInfo(HttpRespond<CreditInfoBean> respond);

    void onQuerySucceed(String string);

    void onQueryFailed(String string);
}
