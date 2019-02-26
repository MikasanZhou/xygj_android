package com.xygj.app.jinrirong.activity.product.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProductBean;

/**
 * Created by Yangli on 2018/4/23.
 */

public interface ProductListView extends BaseView {
    void showProductList(HttpRespond<List<ProductBean>> respond);
}
