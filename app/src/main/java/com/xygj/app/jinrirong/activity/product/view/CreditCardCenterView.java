package com.xygj.app.jinrirong.activity.product.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.credit_card.Bank;

/**
 * Created by xuyougen on 2018/4/23.
 */

public interface CreditCardCenterView extends BaseView{
    void OnGetCreditCardRecommendListSucceed(List<CreditCard> recommendCardList);

    void onGetCreditCardBankListSucceed(List<Bank> bankList);

    void OnGetQuickOpenCardListSucceed(List<CreditCard> creditCardList);
}
