package com.xygj.app.jinrirong.activity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ScreenUtils;
import com.xygj.app.jinrirong.activity.product.presenter.CreditCardCenterMorePresenter;
import com.xygj.app.jinrirong.activity.product.view.CreditCardCenterMoreView;
import com.xygj.app.jinrirong.adpter.CreditCardListAdapter;
import com.xygj.app.jinrirong.adpter.SingleChooseAdapter;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.credit_card.BankType;
import com.xygj.app.jinrirong.model.credit_card.CardType;
import com.xygj.app.jinrirong.model.credit_card.MoneyType;
import com.xygj.app.jinrirong.model.credit_card.YearFeeType;

public class CreditCardCenterMoreActivity extends BaseMvpActivity<CreditCardCenterMoreView, CreditCardCenterMorePresenter> implements CreditCardCenterMoreView, OnLoadmoreListener {


    private static final String EXTRA_BANK_ID = "bank_id";
    private int curPage = 0;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_credit_card_center_more;
    }

    private int bankId = 0;
    private int cardId = 0;
    private int coinId = 0;
    private int yearId = 0;

    public static Intent getIntent(Context context, int bankId) {
        Intent intent = new Intent(context, CreditCardCenterMoreActivity.class);
        intent.putExtra(EXTRA_BANK_ID, bankId);
        return intent;
    }

    @BindView(R.id.rv_credit_card)
    RecyclerView mRvCreditCard;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;

    CreditCardListAdapter mCardListAdapter;

    @Override
    protected void initView() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                curPage = 0;
                getData();
            }
        });
        mCardListAdapter = new CreditCardListAdapter(this, mCreditCardList);
        mRvCreditCard.setAdapter(mCardListAdapter);
        mRvCreditCard.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected CreditCardCenterMorePresenter createPresenter() {
        return new CreditCardCenterMorePresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankId = getIntent().getIntExtra(EXTRA_BANK_ID, 0);
//        if (bankId > 0) {
//            Toast.makeText(this, "分类进入的" + bankId, Toast.LENGTH_SHORT).show();
//        }
        getData();
    }

    private void getData() {
        mPresenter.getBankTypeList();
        mPresenter.getCardTypeList();
        mPresenter.getMoneyTypeList();
        mPresenter.getYearFeeTypeList();
        mPresenter.getCreditCardBy(bankId, cardId, coinId, yearId, curPage, 10);
    }


    private PopupWindow mPwChooseBankType;
    private PopupWindow mPwChooseCardType;
    private PopupWindow mPwChooseMoneyType;
    private PopupWindow mPwChooseYearFeeType;

    @BindView(R.id.fl_choose_bank)
    FrameLayout mFlChooseBank;

    @BindView(R.id.fl_choose_card_type)
    FrameLayout mFlChooseCard;

    @BindView(R.id.fl_choose_coin_type)
    FrameLayout mFlChooseMoney;

    @BindView(R.id.fl_choose_year_fee)
    FrameLayout mFlChooseYearFee;


    //选择银行
    @OnClick(R.id.fl_choose_bank)
    public void chooseBank() {
        if (mPwChooseBankType == null) {
            if (mBankTypeList == null) {
                Toast.makeText(this, "分类信息获取失败，请刷新", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> stringList = new ArrayList<>();

            for (BankType bankType : mBankTypeList) {
                stringList.add(bankType.getName());
            }

            mPwChooseBankType = generateSingleChoosePopupWindow(this, stringList, new SingleChooseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    bankId = mBankTypeList.get(position).getID();
                    reSearchData();
                    mPwChooseBankType.dismiss();
                }
            });

        }
        mPwChooseBankType.showAsDropDown(mFlChooseBank);
    }

    private void reSearchData() {
        mCreditCardList.clear();
        mCardListAdapter.notifyDataSetChanged();
        mPresenter.getCreditCardBy(bankId, cardId, coinId, yearId, curPage, 10);
    }

    //选择卡种
    @OnClick(R.id.fl_choose_card_type)
    public void chooseCardType() {
        if (mPwChooseCardType == null) {
            if (mCardTypeList == null) {
                Toast.makeText(this, "分类信息获取失败，请刷新", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> stringList = new ArrayList<>();

            for (CardType cardType : mCardTypeList) {
                stringList.add(cardType.getName());
            }

            mPwChooseCardType = generateSingleChoosePopupWindow(this, stringList, new SingleChooseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    cardId = mCardTypeList.get(position).getID();
                    reSearchData();
                    mPwChooseCardType.dismiss();
                }
            });

        }
        mPwChooseCardType.showAsDropDown(mFlChooseCard);
    }

    //选择币种
    @OnClick(R.id.fl_choose_coin_type)
    public void chooseCoinType() {
        if (mPwChooseMoneyType == null) {
            if (mMoneyTypeList == null) {
                Toast.makeText(this, "分类信息获取失败，请刷新", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> stringList = new ArrayList<>();

            for (MoneyType moneyType : mMoneyTypeList) {
                stringList.add(moneyType.getName());
            }

            mPwChooseMoneyType = generateSingleChoosePopupWindow(this, stringList, new SingleChooseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    coinId = mMoneyTypeList.get(position).getID();
                    reSearchData();
                    mPwChooseMoneyType.dismiss();
                }
            });

        }
        mPwChooseMoneyType.showAsDropDown(mFlChooseMoney);
    }

    //选择年费
    @OnClick(R.id.fl_choose_year_fee)
    public void chooseYearFee() {
        if (mPwChooseYearFeeType == null) {
            if (mYearFeeTypeList == null) {
                Toast.makeText(this, "分类信息获取失败，请刷新", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> stringList = new ArrayList<>();

            for (YearFeeType yearFeeType : mYearFeeTypeList) {
                stringList.add(yearFeeType.getName());
            }

            mPwChooseYearFeeType = generateSingleChoosePopupWindow(this, stringList, new SingleChooseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    yearId = mYearFeeTypeList.get(position).getID();
                    reSearchData();
                    mPwChooseYearFeeType.dismiss();
                }
            });

        }
        mPwChooseYearFeeType.showAsDropDown(mFlChooseYearFee);
    }

    List<BankType> mBankTypeList;
    List<CardType> mCardTypeList;
    List<MoneyType> mMoneyTypeList;
    List<YearFeeType> mYearFeeTypeList;

    @Override
    public void onGetBankTypeListSucceed(List<BankType> bankTypeList) {
        mBankTypeList = bankTypeList;
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetCardTypeListSucceed(List<CardType> cardTypeList) {
        mCardTypeList = cardTypeList;
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetMoneyTypeListSucceed(List<MoneyType> moneyTypeList) {
        mMoneyTypeList = moneyTypeList;
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetYearFeeTypeListSucceed(List<YearFeeType> yearFeeTypeList) {
        mYearFeeTypeList = yearFeeTypeList;
        mRefreshLayout.setRefreshing(false);
    }

    List<CreditCard> mCreditCardList = new ArrayList<>();

    @Override
    public void onGetCreditCardListSucceed(List<CreditCard> creditCardList) {
        refreshLayout.finishLoadmore();
        mRefreshLayout.setRefreshing(false);
        if (curPage == 0) {
            mCreditCardList.clear();
        }
        mCreditCardList.addAll(creditCardList);
        mCardListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCreditCardListFailed(String msg) {
        refreshLayout.finishLoadmore();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingView() {
        hideLoadingDialog();
    }

    @Override
    public void onTokenInvalidate() {

    }

    @Override
    public void onNetworkConnectFailed() {

    }

    @Override
    public void showLoadingView() {
        showLoadingDialog();
    }

    public PopupWindow generateSingleChoosePopupWindow(Context context, List<String> strList, SingleChooseAdapter.OnItemClickListener listener) {
        //获取屏幕宽度和dp值
        int screenWidth = ScreenUtils.getScreenWidth(context);
        float density = context.getResources().getDisplayMetrics().density * 2;

        View layout = getLayoutInflater().inflate(R.layout.layout_popup_window_single_check, null);
        final PopupWindow popupWindow = new PopupWindow(layout, screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        RecyclerView recyclerView = layout.findViewById(R.id.rv_single_choose);

        SingleChooseAdapter singleChooseAdapter = new SingleChooseAdapter(
                strList,
                context,
                ContextCompat.getColor(this, R.color.colorLightGray),
                ContextCompat.getColor(this, R.color.colorPrimary)
        );

        singleChooseAdapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(singleChooseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(density * 2);
        }
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimStyle);
        return popupWindow;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.getCreditCardBy(bankId, cardId, coinId, yearId, ++curPage, 10);
    }
}
