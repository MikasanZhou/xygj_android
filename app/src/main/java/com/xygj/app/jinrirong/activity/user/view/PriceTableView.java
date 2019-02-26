package com.xygj.app.jinrirong.activity.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PriceBean;

/**
 *
 * Created by Yangli on 2018/5/30.
 */

public interface PriceTableView extends BaseView {
    void showPriceTable(HttpRespond<List<PriceBean>> listHttpRespond);
}
