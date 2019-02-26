package com.xygj.app.jinrirong.activity.user.changejie;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.widget.CountDownTextView;
import com.xygj.app.jinrirong.activity.MainActivity;
import com.xygj.app.jinrirong.activity.user.QueryHistoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangJieCodeActivity extends BaseMvpActivity<ChangJieCodeView, ChangJieCodePresenter> implements ChangJieCodeView {
    private static final String EXTRA_ID = "oid";
    private static final String EXTRA_MONEY = "money";
    private static final String EXTRA_PAYTYPE = "paytype";

    public static final int TYPE_DL = 0;
    public static final int TYPE_ZX = 1;

    @BindView(R.id.ordermoney_tv)
    TextView mTvOrderMoney;
    @BindView(R.id.cdt_countdown)
    CountDownTextView mCdtCountdown;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_id_card)
    EditText mEtIdCard;
    @BindView(R.id.et_bank_card)
    EditText mEtBankCard;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.phonecode_et)
    EditText mEtPhoneCode;

    private String oid, money;
    private PayType payType = PayType.DaiLi;

    public enum PayType {
        DaiLi,
        ZhengXin
    }

    public static void start(Context context, String oid, String money) {
        Intent intent = new Intent(context, ChangJieCodeActivity.class);
        intent.putExtra(EXTRA_ID, oid);
        intent.putExtra(EXTRA_MONEY, money);
        context.startActivity(intent);
    }

    public static void start(Context context, String oid, String money, PayType payType) {
        Intent intent = new Intent(context, ChangJieCodeActivity.class);
        intent.putExtra(EXTRA_ID, oid);
        intent.putExtra(EXTRA_MONEY, money);
        intent.putExtra(EXTRA_PAYTYPE, payType);
        context.startActivity(intent);
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_change_jie_code;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        oid = getIntent().getStringExtra(EXTRA_ID);
        money = getIntent().getStringExtra(EXTRA_MONEY);
        payType = (PayType) getIntent().getSerializableExtra(EXTRA_PAYTYPE);
        if (payType == null) {
            payType = PayType.DaiLi;
        }
        if (TextUtils.isEmpty(oid) || TextUtils.isEmpty(money)) {
            Toast.makeText(this, "非法进入", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mTvOrderMoney.setText(money);
    }

    @Override
    protected void initData() {
    }


    @Override
    protected ChangJieCodePresenter createPresenter() {
        return new ChangJieCodePresenter();
    }

    @Override
    public void onSendPaySmsCode(String respond) {
        Log.e("data---重发验证码", respond);
        try {
            JSONObject jsonObject = new JSONObject(respond);
            Toast.makeText(this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
            if (jsonObject.optInt("result") == 1) {
                mCdtCountdown.startCountDown(60);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "获取失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPayFinish(String respond) {
        Log.e("data---支付结果", respond);
        try {
            JSONObject jsonObject = new JSONObject(respond);
            Toast.makeText(this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
            if (jsonObject.optInt("result") == 1) {
                switch (payType) {
                    case ZhengXin:
                        startActivity(new Intent(this, QueryHistoryActivity.class));
                        finish();
                        break;
                    case DaiLi:
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                        break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        hideLoadingView();
        finish();
    }

    @Override
    public void onNetworkConnectFailed() {

    }


    @OnClick(R.id.cdt_countdown)
    public void onCdtCountdownClicked() {
        if (checkAll()) {
            mPresenter.sendPaySmsCode(
                    oid,
                    mEtBankCard.getText().toString(),
                    mEtIdCard.getText().toString(),
                    mEtName.getText().toString(),
                    mEtPhone.getText().toString(),
                    payType
            );
        } else {
            Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAll() {
        return mEtName.length() > 0 && mEtIdCard.length() > 0 && mEtBankCard.length() > 0 && mEtPhone.length() > 0;
    }

    @OnClick(R.id.btn_next)
    public void onBtnNextClicked() {
        String msgCode = mEtPhoneCode.getText().toString().trim();
        if (TextUtils.isEmpty(msgCode)) {
            Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.commitPay(oid, msgCode, payType);
        }
    }
}
