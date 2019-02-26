package com.xygj.app.jinrirong.fragment.home.income.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/26.
 */

public interface PromoteIncomeView extends BaseView {
    void showPromoteList(HttpRespond<String> respond);
}
