package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.UpdatePwdPresenter;
import com.xygj.app.jinrirong.activity.user.view.UpdatePwdView;
import com.xygj.app.jinrirong.config.UserManager;

public class UpdatePwdActivity extends BaseMvpActivity<UpdatePwdView, UpdatePwdPresenter> implements UpdatePwdView, TextWatcher {

    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.et_pwd_old)
    EditText mEtPwdOld;
    @BindView(R.id.et_pwd_new)
    EditText mEtPwdNew;
    @BindView(R.id.et_pwd_new_confirm)
    EditText mEtPwdNewConfirm;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.btn_save)
    public void updatePwd() {
        String p = mEtPwdOld.getText().toString();
        String p1 = mEtPwdNew.getText().toString();
        String p2 = mEtPwdNewConfirm.getText().toString();
        if (TextUtils.isEmpty(p) || TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2)) {
            ToastUtils.showShort(this, "相关密码不能为空");
            return;
        }
        if (p1.equals(p2)) {
            JSONObject json = new JSONObject();
            try {
                json.put("oldpwd", p);
                json.put("newpwd", p1);
                json.put("surepwd", p2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPresenter.changePwd(UserManager.getInstance().getToken(),
                    SafeUtils.encrypt(this, json.toString()));
        } else {
            Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        mEtPwdNew.addTextChangedListener(this);
        mEtPwdOld.addTextChangedListener(this);
        mEtPwdNewConfirm.addTextChangedListener(this);
    }


    @Override
    public void onChangePwdSucceed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onChangePwdFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        changeBtnState();
    }

    private void changeBtnState() {
        if (mEtPwdOld.length() >= 6 && mEtPwdNew.length() >= 6 && mEtPwdNewConfirm.length() >= 6) {
            mBtnSave.setEnabled(true);
        } else {
            mBtnSave.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
        hideLoadingDialog();
    }

    @Override
    protected UpdatePwdPresenter createPresenter() {
        return new UpdatePwdPresenter();
    }
}
