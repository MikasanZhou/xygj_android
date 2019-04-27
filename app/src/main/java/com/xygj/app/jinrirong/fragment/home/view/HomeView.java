package com.xygj.app.jinrirong.fragment.home.view;


import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoanCateAndLocation;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.NewMessageBean;

/**
 * Created by xuyougen on 2018/4/17.
 */

public interface HomeView extends BaseView {
    void onGetBannerSucceed(List<HomeBanner> bannerList);

    void onGetLoanCategorySucceed(LoanCateAndLocation data);

    void onGetRecommendLoanListSucceed(List<LoanProduct> data);
    void onGetRecommendLoanListFiled();

    void onGetHotLoanListSucceed(List<LoanProduct> data);

    void onGetCommonNewsSucceed(List<CommonNews> commonNewsList);

    void onNewMessage(HttpRespond<NewMessageBean> respond);
}
