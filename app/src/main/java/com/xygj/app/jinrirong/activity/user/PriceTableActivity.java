package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.jinrirong.activity.user.presenter.PriceTablePresenter;
import com.xygj.app.jinrirong.activity.user.view.PriceTableView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PriceBean;

public class PriceTableActivity extends BaseMvpActivity<PriceTableView, PriceTablePresenter> implements PriceTableView {
    @BindView(R.id.iv_table1)
    ImageView ivTable1;
    @BindView(R.id.iv_table2)
    ImageView ivTable2;

    @Override
    protected PriceTablePresenter createPresenter() {
        return new PriceTablePresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_price_table;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getPriceTable();
    }

    @Override
    public void showLoadingView() {
        showLoadingDialog();
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
    public void showPriceTable(HttpRespond<List<PriceBean>> listHttpRespond) {
        if (listHttpRespond.result == 1) {
            List<PriceBean> beans = listHttpRespond.data;
            Glide.with(this).load(beans.get(0).getPicUrl()).into(ivTable1);
            Glide.with(this).load(beans.get(1).getPicUrl()).into(ivTable2);
        }
    }
}
