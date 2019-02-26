package com.xygj.app.jinrirong.fragment.home.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/25.
 */

public interface LoanView extends BaseView {
    void showTopImg(HttpRespond<List<HomeBanner>> respond);
}
