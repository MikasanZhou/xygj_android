package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.CreditInfoPresenter;
import com.xygj.app.jinrirong.activity.user.view.CreditInfoView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.CreditInfoBean;
import com.xygj.app.jinrirong.model.HttpRespond;

public class CreditInfoActivity extends BaseMvpActivity<CreditInfoView, CreditInfoPresenter> implements CreditInfoView {
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.btn_query_again)
    Button btnQuery;

    @BindView(R.id.wv_web)
    WebView mWebView;

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_no)
    TextView mTvCardNo;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    private CreditInfoBean infoBean;
    private String id;

    @Override
    protected CreditInfoPresenter createPresenter() {
        return new CreditInfoPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_credit_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        id = getIntent().getStringExtra("id");
        mPresenter.requestCreditInfo(UserManager.getInstance().getToken(), id);
    }

    @OnClick(R.id.btn_pay)
    public void onPayClick() {
        PayModeActivity.jumpToPay(this, PayModeActivity.TYPE_ZX, "征信查询余额",
                "￥" + infoBean.getPayment(), id);
        finish();
    }

    @OnClick(R.id.btn_query_again)
    public void queryCreditAgain() {
        mPresenter.queryAgain(Integer.valueOf(id));
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
    public void showCreditInfo(HttpRespond<CreditInfoBean> respond) {
        if (respond.result == 1) {
            infoBean = respond.data;
            if (respond.data.getStatus() == 4) {
//                tvInfo.setText(infoBean.getDetailurl());
                mWebView.setVisibility(View.VISIBLE);
                mWebView.loadUrl(infoBean.getDetailurl());
                return;
            }

            mLlContainer.setVisibility(View.VISIBLE);

            // 1.待付款   2已付款   3查询失败  4已查询    5已取消
            if (respond.data.getStatus() == 1) {
                btnPay.setVisibility(View.VISIBLE);
            }
            if (respond.data.getStatus() == 3) {
                btnQuery.setVisibility(View.VISIBLE);
            }
            mTvName.setText(infoBean.getTrueName());
            mTvCardNo.setText(infoBean.getIdCardNo());
            mTvPhone.setText(infoBean.getMobile());
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    @Override
    public void onQuerySucceed(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onQueryFailed(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        finish();
    }
}
