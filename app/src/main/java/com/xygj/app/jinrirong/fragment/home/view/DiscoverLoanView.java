package com.xygj.app.jinrirong.fragment.home.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.IHaveInfo;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.MoneyInfo;
import com.xygj.app.jinrirong.model.NeedInfo;

/**
 * Created by xuyougen on 2018/4/17.
 */

public interface DiscoverLoanView extends BaseView {
    void onGetMoneyTypeListSucceed(List<MoneyInfo> moneyInfoList);

    void onGetIHaveTypeListSucceed(List<IHaveInfo> iHaveInfoList);

    void onGetNeedListSucceed(List<NeedInfo> needInfoList);

    void onGetLoanListSucceed(List<LoanProduct> loanProductList);

    void onGetLoanListFailed(String message);

    void onGetTermListSucceed(List<MoneyInfo> data);
}
