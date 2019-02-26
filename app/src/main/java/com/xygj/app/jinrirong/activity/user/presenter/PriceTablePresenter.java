package com.xygj.app.jinrirong.activity.user.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.PriceTableView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PriceBean;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by Yangli on 2018/5/30.
 */

public class PriceTablePresenter extends BasePresenter<PriceTableView> {
    public void getPriceTable() {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getPriceList(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER), new Consumer<HttpRespond<List<PriceBean>>>() {
            @Override
            public void accept(HttpRespond<List<PriceBean>> listHttpRespond) throws Exception {
                getView().hideLoadingView();
                getView().showPriceTable(listHttpRespond);
            }
        });
    }
}
