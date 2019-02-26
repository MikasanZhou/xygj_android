package com.xygj.app.jinrirong.fragment.user.presenter;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.user.view.ProductDescView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;

/**
 * Created by Yangli on 2018/4/23.
 */

public class ProductDescPresenter extends BasePresenter<ProductDescView> {

    public void getHtmlData(int id) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getHtmlContent(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, id), new Consumer<HttpRespond<HtmlData>>() {
            @Override
            public void accept(HttpRespond<HtmlData> respond) throws Exception {
                mView.hideLoadingView();
                if (respond.result == 1) {
                    mView.showHtmlContent(respond);
                }
            }
        });
    }
}
