package com.xygj.app.jinrirong.activity.user.CreditDetail;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.jinrirong.activity.user.CreditDetail.presenter.CreditListPresenter;
import com.xygj.app.jinrirong.activity.user.CreditDetail.view.CreditListView;

public class CreditListActivity extends BaseMvpActivity<CreditListView, CreditListPresenter> implements CreditListView {


    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_credit_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected CreditListPresenter createPresenter() {
        return new CreditListPresenter();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onTokenInvalidate() {

    }

    @Override
    public void onNetworkConnectFailed() {

    }
}
