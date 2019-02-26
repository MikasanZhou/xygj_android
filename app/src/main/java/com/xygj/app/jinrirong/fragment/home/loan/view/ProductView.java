package com.xygj.app.jinrirong.fragment.home.loan.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProductBean;

/**
 *
 * Created by Yangli on 2018/4/23.
 */

public interface ProductView extends BaseView {
    void showRecProductLists(HttpRespond<List<ProductBean>> respond);

    void showProductLists(HttpRespond<List<ProductBean>> respond);
}
