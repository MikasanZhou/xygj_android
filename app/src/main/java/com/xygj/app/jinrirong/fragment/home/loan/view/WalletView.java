package com.xygj.app.jinrirong.fragment.home.loan.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/24.
 */

public interface WalletView extends BaseView {
    void showMoneyData(HttpRespond<String> respond);
}
