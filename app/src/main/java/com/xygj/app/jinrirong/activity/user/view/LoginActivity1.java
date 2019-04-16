package com.xygj.app.jinrirong.activity.user.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.SPUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.CountDownTextView;
import com.xygj.app.jinrirong.activity.user.ForgetPwdActivity;
import com.xygj.app.jinrirong.activity.user.RegisterActivity;
import com.xygj.app.jinrirong.activity.user.presenter.LoginPresenter;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoginData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity1 extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cdt_countdown)
    CountDownTextView cdtCountdown;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick(R.id.tv_forget_pwd)
    public void gotoForgetPwd() {
        startActivity(new Intent(this, ForgetPwdActivity.class));
    }

    @OnClick(R.id.tv_register)
    public void gotoRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void login() {
        if (TextUtils.isEmpty(etPhone.getText()) || TextUtils.isEmpty(etPsw.getText())) {
            ToastUtils.showShort(this, "手机号和验证码不能为空");
        } else
            mPresenter.doLogin(etPhone.getText().toString(), etPsw.getText().toString());
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.cdt_countdown)
    public void getSmsCode() {
        if (TextUtils.isEmpty(etPhone.getText())) {
            ToastUtils.showShort(this, "手机号不能为空");
        } else {
            mPresenter.getSmsNoCode(etPhone.getText().toString());
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
    public void onLoginSucceed(String msg) {

    }

    @Override
    public void onLoginFailed(String msg) {

    }

    @Override
    public void onLoginComplete(HttpRespond<LoginData> respond) {
        // 登录成功后修改本地登录标记，并存储用户名(手机号)、token、加解密参数
        UserManager.getInstance().saveLoginData(etPhone.getText().toString(), respond.data.token);
        SPUtils.put(this, "safe_iv", respond.data.iv);
        SPUtils.put(this, "safe_key", respond.data.key);
        ToastUtils.showShort(this, "恭喜您，登录成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onLoginFailed(HttpRespond<LoginData> respond) {
        Toast.makeText(this, respond.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendSmsComplete(HttpRespond respond) {
        ToastUtils.showShort(this, respond.message);
        if (respond.result == 1) {
            cdtCountdown.startCountDown(60);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
