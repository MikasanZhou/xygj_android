package com.xygj.app.jinrirong.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.CountDownTextView;
import com.xygj.app.jinrirong.activity.user.presenter.ForgetPwdPresenter;
import com.xygj.app.jinrirong.activity.user.view.ForgetPwdView;
import com.xygj.app.jinrirong.dialog.VerifyCaptchaDialog;
import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.HttpRespond;

public class ForgetPwdActivity extends BaseMvpActivity<ForgetPwdView, ForgetPwdPresenter> implements ForgetPwdView {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.cdt_countdown)
    CountDownTextView mCountDownTextView;
    private VerifyCaptchaDialog dialog;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, true);
    }

    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter();
    }

    @OnClick(R.id.btn_next)
    public void nextStepToResetPwd() {
        if (TextUtils.isEmpty(etPhone.getText())) {
            ToastUtils.showShort(this, "手机号不能为空");
        } else if (TextUtils.isEmpty(etSmsCode.getText())) {
            ToastUtils.showShort(this, "验证码不能为空");
        } else {
            mPresenter.verifySmsCode(etPhone.getText().toString(), etSmsCode.getText().toString());
        }
    }

    @OnClick(R.id.cdt_countdown)
    public void getSmsCode() {
        if (TextUtils.isEmpty(etPhone.getText())) {
            ToastUtils.showShort(this, "手机号不能为空");
        } else {
            mPresenter.getCaptcha();
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

    }

    @Override
    public void showCaptchaDialog(HttpRespond<CaptchaBean> respond) {
        dialog = VerifyCaptchaDialog.newInstance(
                etPhone.getText().toString(), respond.data.captchaUrl, new VerifyCaptchaDialog.OnVerifyListener() {
                    @Override
                    public void onOkClick(String captcha) {
                        mPresenter.getSmsCode(etPhone.getText().toString(), captcha);
                    }

                });
        dialog.show(getSupportFragmentManager(), "captcha");
    }

    @Override
    public void onSendSmsComplete(HttpRespond respond) {
        ToastUtils.showShort(this, respond.message);
        if (respond.result == 1) {
            dialog.dismiss();
            mCountDownTextView.startCountDown(60);
        }
    }

    @Override
    public void onVerifySmsCodeDone(HttpRespond respond) {
        if (respond.result == 1) {
            Intent intent = new Intent(this, ResetPwdActivity.class);
            intent.putExtra("phone_num", etPhone.getText().toString());
            startActivity(intent);
            finish();
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }
}
