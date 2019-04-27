package com.xygj.app.jinrirong.activity.selectlist.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.LoanProduct;

import java.util.List;

/**
 * Created by Administrator on 2019/4/20 0020.
 */

public interface ProductListView extends BaseView {

    void onGetLoanListSucceed(List<LoanProduct> loanProductList);

    void onGetLoanListFailed(String message);

}
