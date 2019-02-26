package com.xygj.app.jinrirong.activity.product.view;


import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.credit_card.BankType;
import com.xygj.app.jinrirong.model.credit_card.CardType;
import com.xygj.app.jinrirong.model.credit_card.MoneyType;
import com.xygj.app.jinrirong.model.credit_card.YearFeeType;

/**
 * Created by xuyougen on 2018/4/23.
 */

public interface CreditCardCenterMoreView extends BaseView {
    void onGetBankTypeListSucceed(List<BankType> bankTypeList);

    void onGetCardTypeListSucceed(List<CardType> cardTypeList);

    void onGetMoneyTypeListSucceed(List<MoneyType> moneyTypeList);

    void onGetYearFeeTypeListSucceed(List<YearFeeType> yearFeeTypeList);

    void onGetCreditCardListSucceed(List<CreditCard> creditCardList);

    void onGetCreditCardListFailed(String msg);
}
