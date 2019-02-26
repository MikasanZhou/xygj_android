package com.xygj.app.jinrirong.activity.product.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.CreditCardCenterMoreView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.credit_card.BankType;
import com.xygj.app.jinrirong.model.credit_card.CardType;
import com.xygj.app.jinrirong.model.credit_card.MoneyType;
import com.xygj.app.jinrirong.model.credit_card.YearFeeType;
import io.reactivex.functions.Consumer;

/**
 * Created by xuyougen on 2018/4/23.
 */

public class CreditCardCenterMorePresenter extends BasePresenter<CreditCardCenterMoreView> {

    public void getBankTypeList() {
        addTask(RetrofitHelper.getInstance().getService().getCreditCardBankType(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                1), new Consumer<HttpRespond<List<BankType>>>() {
            @Override
            public void accept(HttpRespond<List<BankType>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetBankTypeListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getCardTypeList() {
        addTask(RetrofitHelper.getInstance().getService().getCreditCardType(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                2), new Consumer<HttpRespond<List<CardType>>>() {
            @Override
            public void accept(HttpRespond<List<CardType>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetCardTypeListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getMoneyTypeList() {
        addTask(RetrofitHelper.getInstance().getService().getCreditCardCoinType(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                3), new Consumer<HttpRespond<List<MoneyType>>>() {
            @Override
            public void accept(HttpRespond<List<MoneyType>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetMoneyTypeListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getYearFeeTypeList() {
        addTask(RetrofitHelper.getInstance().getService().getCreditCardYearFeeType(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                4), new Consumer<HttpRespond<List<YearFeeType>>>() {
            @Override
            public void accept(HttpRespond<List<YearFeeType>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetYearFeeTypeListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getCreditCardBy(int bankId, int cardId, int cionId, int yearFeeId, int page, int rows) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getCreditCardBy(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                bankId,
                cardId,
                cionId,
                yearFeeId,
                page,
                rows), new Consumer<HttpRespond<List<CreditCard>>>() {
            @Override
            public void accept(HttpRespond<List<CreditCard>> listHttpRespond) throws Exception {
                if (listHttpRespond.result==1) {
                    getView().onGetCreditCardListSucceed(listHttpRespond.data);
                }else{
                    getView().onGetCreditCardListFailed(listHttpRespond.message);
                }
                getView().hideLoadingView();
            }
        });
    }
}
