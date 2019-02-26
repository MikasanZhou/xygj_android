package com.xygj.app.jinrirong.activity.product.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.LoanProduct;

/**
 * Created by xuyougen on 2018/4/17.
 */

public interface ProductDetailView extends BaseView {
    void onGetProductDetailSucceed(LoanProduct loanProduct);
}
