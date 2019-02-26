package com.xygj.app.jinrirong.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.MainActivity;
import com.xygj.app.jinrirong.activity.user.changejie.ChangJieCodeActivity;
import com.xygj.app.jinrirong.activity.user.presenter.PayModePresenter;
import com.xygj.app.jinrirong.activity.user.view.PayModeView;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.config.ApiFactory;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PayInfoBean;

public class PayModeActivity extends BaseMvpActivity<PayModeView, PayModePresenter> implements PayModeView {
    @BindView(R.id.tv_payment_name)
    TextView tvPaymentName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_ali_state)
    ImageView ivAliState;
    @BindView(R.id.iv_wx_state)
    ImageView ivWxState;
    @BindView(R.id.iv_balance_state)
    ImageView ivBalanceState;
    @BindView(R.id.iv_cj_state)
    ImageView ivCjState;
    @BindView(R.id.btn_pay)
    Button btnPay;
    public static final int TYPE_ZX = 1, TYPE_DL = 2;
    private int type;
    private int currentPayMode = -1;
    private String extra;

    @Override
    protected PayModePresenter createPresenter() {
        return new PayModePresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_pay_mode;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected void initData() {
        super.initData();
        type = getIntent().getIntExtra("type_value", -1);
        extra = getIntent().getStringExtra("pay_extra");
        String payType = getIntent().getStringExtra("pay_type");
        String textPayment = getIntent().getStringExtra("text_payment");
        tvPaymentName.setText(payType);
        tvPrice.setText(textPayment);

    }

    public static void jumpToPay(Activity activity, int type, String payContent,
                                 String payment, String extra) {
        Intent intent = new Intent(activity, PayModeActivity.class);
        intent.putExtra("type_value", type);
        intent.putExtra("pay_type", payContent);
        intent.putExtra("text_payment", payment);
        intent.putExtra("pay_extra", extra); // 订单相关
        activity.startActivity(intent);
    }

    @OnClick(R.id.rl_pay_by_ali)
    public void onAliPayClick() {
        currentPayMode = 2;
        ivAliState.setSelected(true);
        ivWxState.setSelected(false);
        ivBalanceState.setSelected(false);
        ivCjState.setSelected(false);
    }

    @OnClick(R.id.rl_pay_by_wx)
    public void onWxPayClick() {
        currentPayMode = 1;
        ivAliState.setSelected(false);
        ivWxState.setSelected(true);
        ivCjState.setSelected(false);
        ivBalanceState.setSelected(false);
    }

    @OnClick(R.id.rl_pay_by_balance)
    public void onBalancePayClick() {
        currentPayMode = 3;
        ivAliState.setSelected(false);
        ivWxState.setSelected(false);
        ivCjState.setSelected(false);
        ivBalanceState.setSelected(true);
    }

    @OnClick(R.id.rl_pay_by_changjie)
    public void rl_pay_by_changjie() {
        currentPayMode = 5;
        ivAliState.setSelected(false);
        ivWxState.setSelected(false);
        ivBalanceState.setSelected(false);
        ivCjState.setSelected(true);
    }

    @OnClick(R.id.btn_pay)
    public void onPayClick() {
        JSONObject json = new JSONObject();
        try {
            json.put("paytype", currentPayMode);
            json.put("id", extra);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (currentPayMode == -1) {
            ToastUtils.showShort(this, "请选择一种支付方式");
        } else {
            switch (type) {
                case TYPE_ZX:
                    mPresenter.payQueryCreditInfo(UserManager.getInstance().getToken(),
                            SafeUtils.encrypt(this, json.toString()));
                    break;
                case TYPE_DL:
                    mPresenter.buyProxy(UserManager.getInstance().getToken(),
                            SafeUtils.encrypt(this, json.toString()));
                    break;
                default:
            }
        }
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
        hideLoadingView();
    }

    @Override
    public void onPayDone(HttpRespond<PayInfoBean> respond) {
        if (type == TYPE_ZX) {
            switch (currentPayMode) {
                case 5: // 畅捷支付
                    ChangJieCodeActivity.start(this, respond.data.orderId, tvPrice.getText().toString(), ChangJieCodeActivity.PayType.ZhengXin);
                    break;
                case 3: // 余额
                    Intent intent = new Intent(this, QueryHistoryActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2: // 支付宝
                    String aliPayUrl = ApiFactory.PAY_URL + "Alipay/payzenxin/index?id=" +
                            respond.data.id + "&oid=" + respond.data.orderId;
                    startActivity(MyWebViewActivity.getIntent(this,
                            "支付宝支付", aliPayUrl));
                    break;
                case 1: // 微信
                    String wxPayUrl = ApiFactory.PAY_URL + "Wachet/payzenxin2/index?id=" +
                            respond.data.id + "&oid=" + respond.data.orderId;
                    startActivity(MyWebViewActivity.getIntent(this,
                            "微信支付", wxPayUrl));
                    break;
            }
        } else {
            switch (currentPayMode) {
                case 5: // 畅捷支付
                    ChangJieCodeActivity.start(this, respond.data.orderId, tvPrice.getText().toString());
                    break;
                case 3: // 余额
                    ToastUtils.showShort(this, respond.message);
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case 2: // 支付宝
                    String aliPayUrl = ApiFactory.PAY_URL + "Alipay/index?id=" +
                            respond.data.id + "&oid=" + respond.data.orderId;
                    startActivity(MyWebViewActivity.getIntent(this,
                            "支付宝支付", aliPayUrl));
                    break;
                case 1: // 微信
                    String wxPayUrl = ApiFactory.PAY_URL + "Wachet/index2?id=" +
                            respond.data.id + "&oid=" + respond.data.orderId;
                    startActivity(MyWebViewActivity.getIntent(this,
                            "微信支付", wxPayUrl));
                    break;
            }
        }
        finish();
    }

    @Override
    public void onPayFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
