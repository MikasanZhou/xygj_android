package com.xygj.app.jinrirong.activity.product.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.RankListBean;

/**
 * Created by Yangli on 2018/4/25.
 */

public interface LoanProductPromoteView extends BaseView {
    void showTopImg(HttpRespond<List<HomeBanner>> respond);

    void showPromoteInfo(HttpRespond<RankListBean> respond);
}
