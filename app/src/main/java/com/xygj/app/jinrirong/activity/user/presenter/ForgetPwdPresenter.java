package com.xygj.app.jinrirong.activity.user.presenter;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.ForgetPwdView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;

/**
 * Created by Yangli on 2018/4/21.
 */

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView> {
    public void getCaptcha() {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getCaptcha(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE), new Consumer<HttpRespond<CaptchaBean>>() {
            @Override
            public void accept(HttpRespond<CaptchaBean> httpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showCaptchaDialog(httpRespond);
            }
        });
    }

    public void getSmsCode(String phoneNum, String captcha) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getSmsCode(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE, phoneNum, 1, captcha
        ), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onSendSmsComplete(httpRespond);
            }
        });
    }

    public void verifySmsCode(String phoneNum, String smsCode) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().verifySmsCode(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE, phoneNum, smsCode
        ), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                mView.hideLoadingView();
                mView.onVerifySmsCodeDone(respond);
            }
        });
    }
}
