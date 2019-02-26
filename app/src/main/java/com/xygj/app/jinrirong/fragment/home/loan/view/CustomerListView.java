package com.xygj.app.jinrirong.fragment.home.loan.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.ClientListBean;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/24.
 */

public interface CustomerListView extends BaseView {
    void showClientList(HttpRespond<ClientListBean> respond);

    void onRequestFailed(String message);
}
