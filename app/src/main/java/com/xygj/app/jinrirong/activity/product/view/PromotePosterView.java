package com.xygj.app.jinrirong.activity.product.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PosterBean;

/**
 * Created by Yangli on 2018/4/25.
 */

public interface PromotePosterView extends BaseView {
    void showPromotePoster(HttpRespond<PosterBean> respond);
}
