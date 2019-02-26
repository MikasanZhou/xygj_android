package com.xygj.app.jinrirong.activity.product.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.CreditCardCenterView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.credit_card.Bank;
import io.reactivex.functions.Consumer;

/**
 * Created by xuyougen on 2018/4/23.
 */

public class CreditCardCenterPresenter extends BasePresenter<CreditCardCenterView> {

    //    client=android&package=android.ceshi&ver=v1.1&isrec=1&rows=6
    public void getCreditCardRecommendList(int rows) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getCreditCardRecommendList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                1,
                rows), new Consumer<HttpRespond<List<CreditCard>>>() {
            @Override
            public void accept(HttpRespond<List<CreditCard>> listHttpRespond) throws Exception {
                if (listHttpRespond.result==1) {
                    getView().OnGetCreditCardRecommendListSucceed(listHttpRespond.data);
                }
                getView().hideLoadingView();
            }
        });
    }

    //    client=android&package=android.ceshi&ver=v1.1&isrec=1&rows=6
    public void getQuickOpenCardList(int rows) {
        addTask(RetrofitHelper.getInstance().getService().getCreditCardRecommendList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                0,
                rows), new Consumer<HttpRespond<List<CreditCard>>>() {
            @Override
            public void accept(HttpRespond<List<CreditCard>> listHttpRespond) throws Exception {
                if (listHttpRespond.result==1) {
                    getView().OnGetQuickOpenCardListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getCreditCardBankList() {
        addTask(RetrofitHelper.getInstance().getService().getCreditCardBankList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER), new Consumer<HttpRespond<List<Bank>>>() {
            @Override
            public void accept(HttpRespond<List<Bank>> listHttpRespond) throws Exception {
                if (listHttpRespond.result==1) {
                    getView().onGetCreditCardBankListSucceed(listHttpRespond.data);
                }
            }
        });
    }
}
