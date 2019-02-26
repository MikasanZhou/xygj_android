package com.xygj.app.jinrirong.activity.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HistoryBean;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/25.
 */

public interface QueryHistoryView extends BaseView {
    void showQueryList(HttpRespond<List<HistoryBean>> respond);
}
