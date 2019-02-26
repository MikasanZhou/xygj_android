package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.ResetPwdPresenter;
import com.xygj.app.jinrirong.activity.user.view.ResetPwdView;
import com.xygj.app.jinrirong.model.HttpRespond;

public class ResetPwdActivity extends BaseMvpActivity<ResetPwdView, ResetPwdPresenter> implements ResetPwdView {
    @BindView(R.id.et_pwd_new)
    EditText etPwdNew;
    @BindView(R.id.et_pwd_new_confirm)
    EditText etPwdNewConfirm;
    @BindView(R.id.btn_done)
    Button btnDone;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_reset_pwd;
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
    protected ResetPwdPresenter createPresenter() {
        return new ResetPwdPresenter();
    }

    @OnClick(R.id.btn_done)
    public void onDoneClick() {
        if (TextUtils.isEmpty(etPwdNew.getText()) || TextUtils.isEmpty(etPwdNewConfirm.getText())) {
            ToastUtils.showShort(this, "密码不能为空");
        } else if (!etPwdNew.getText().toString().equals(etPwdNewConfirm.getText().toString())) {
            ToastUtils.showShort(this, "两次输入的密码不一致");
        } else {
            String phoneNum = getIntent().getStringExtra("phone_num");
            mPresenter.submitNewPwd(phoneNum, etPwdNew.getText().toString());
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
    public void onResetPwdDone(HttpRespond respond) {
        ToastUtils.showShort(this, respond.message);
        if (respond.result == 1) {
            finish();
        }
    }
}
