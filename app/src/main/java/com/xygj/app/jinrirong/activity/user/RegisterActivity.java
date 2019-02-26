package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.CountDownTextView;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.dialog.VerifyCaptchaDialog;
import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.activity.user.presenter.RegisterPresenter;
import com.xygj.app.jinrirong.activity.user.view.RegisterView;
import com.xygj.app.jinrirong.model.HtmlData;

public class RegisterActivity extends BaseMvpActivity<RegisterView, RegisterPresenter> implements RegisterView {

    @BindView(R.id.sw_protocol)
    Switch mSwProtocol;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.cdt_countdown)
    CountDownTextView mCountDownTextView;
    private VerifyCaptchaDialog dialog;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @OnClick(R.id.btn_register)
    public void register() {
        if (!mSwProtocol.isChecked()) {
            Toast.makeText(this, "须得同意协议", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etPhone.getText())) {
            ToastUtils.showShort(this, "手机号不能为空");
        } else if (TextUtils.isEmpty(etSmsCode.getText())) {
            ToastUtils.showShort(this, "验证码不能为空");
        } else if (TextUtils.isEmpty(etPsw.getText())) {
            ToastUtils.showShort(this, "密码不能为空");
        } else {
            mPresenter.register(etPhone.getText().toString(), etSmsCode.getText().toString(),
                    etPsw.getText().toString(), etInviteCode.getText().toString());
        }
    }

    /**
     * 查看
     */
    @OnClick(R.id.tv_reg_protocol)
    public void readRegProtocol() {
        mPresenter.getRegisterProtocol();
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.cdt_countdown)
    public void getSmsCode() {
        mPresenter.getCaptcha();
    }

    /**
     * 立即登录
     */
    @OnClick(R.id.tv_login_now)
    public void loginNow() {
        finish();
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
    public void onRegisterSucceed(String msg) {

    }

    @Override
    public void onRegisterFailed(String msg) {

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
    public void onRegisterComplete(HttpRespond respond) {
        ToastUtils.showShort(this, respond.message);
        if (respond.result == 1) {
            finish();
        }
    }

    @Override
    public void onGetRegisterProtocolSucceed(HtmlData htmlData) {
        startActivity(MyWebViewActivity.getIntent(this, htmlData.getTitle(), htmlData.getContents()));
    }
}
