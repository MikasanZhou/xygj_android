package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.QueryResultBean;

/**
 * Created by Yangli on 2018/4/25.
 */

public interface QueryCreditView extends BaseView {
    void onQuerySubmitted(HttpRespond<QueryResultBean> respond);
}
