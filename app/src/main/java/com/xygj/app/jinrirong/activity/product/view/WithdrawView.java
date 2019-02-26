package com.xygj.app.jinrirong.activity.product.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/24.
 */

public interface WithdrawView extends BaseView {
    void onWithdrawSubmitted(HttpRespond respond);
}
