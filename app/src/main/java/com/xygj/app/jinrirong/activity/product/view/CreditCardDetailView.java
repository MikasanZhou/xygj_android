package com.xygj.app.jinrirong.activity.product.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CreditCardDetail;

/**
 * Created by xuyougen on 2018/4/23.
 */

public interface CreditCardDetailView extends BaseView {
    void onGetCreditCardDetailSucceed(CreditCardDetail cardDetail);
}
