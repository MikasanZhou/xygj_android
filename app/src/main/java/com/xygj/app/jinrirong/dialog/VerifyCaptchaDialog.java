package com.xygj.app.jinrirong.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;

/**
 * 获取短信验证码之前的图形验证对话框
 * Created by Administrator on 2018/3/10.
 */

public class VerifyCaptchaDialog extends DialogFragment {
    @BindView(R.id.cet_phone_num)
    EditText cetPhoneNum;
    @BindView(R.id.cet_captcha)
    EditText cetCaptcha;
    @BindView(R.id.iv_captcha)
    ImageView ivCaptcha;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private static String phoneNum;
    private static String captchaUrl;
    private static OnVerifyListener verifyListener;

    public static VerifyCaptchaDialog newInstance(String phone, String captcha, OnVerifyListener listener) {
        phoneNum = phone;
        captchaUrl = captcha;
        verifyListener = listener;
        return new VerifyCaptchaDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_verify_captcha, container, false);
        ButterKnife.bind(this, view);
        cetPhoneNum.setText(phoneNum);
        String url = captchaUrl + "?a=" + System.currentTimeMillis();
        Glide.with(getActivity()).load(url).into(ivCaptcha);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @OnClick(R.id.iv_captcha)
    public void onCaptchaClicked() {
        String url = captchaUrl + "?a=" + System.currentTimeMillis();
        Glide.with(getActivity()).load(url).into(ivCaptcha);
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.btn_ok)
    public void onOkClicked() {
        verifyListener.onOkClick(cetCaptcha.getText().toString());
    }

    public interface OnVerifyListener {
        void onOkClick(String captcha);
    }
}
