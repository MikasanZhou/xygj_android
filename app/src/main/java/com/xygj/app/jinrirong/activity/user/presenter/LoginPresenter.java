package com.xygj.app.jinrirong.activity.user.presenter;

import android.util.Log;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.common.md5.MakeToken;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoginData;
import com.xygj.app.jinrirong.model.TimeStampBean;
import com.xygj.app.jinrirong.activity.user.view.LoginView;
import io.reactivex.functions.Consumer;

/**
 * Created by xuyougen on 2018/4/12.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private static final String TAG = "LoginPresenter";

    public void doLogin(final String phone, final String psw) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getTime(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER), new Consumer<HttpRespond<TimeStampBean>>() {
            @Override
            public void accept(HttpRespond<TimeStampBean> timeStampBeanHttpRespond) throws Exception {
                String token = MakeToken.getToken(phone, psw, timeStampBeanHttpRespond.data.id,
                        timeStampBeanHttpRespond.data.val);
                String iv = MakeToken.getIv(psw, timeStampBeanHttpRespond.data.id);
                String key = MakeToken.getKey(phone, timeStampBeanHttpRespond.data.val);
                Log.e(TAG, "localToken: " + token);
                Log.e(TAG, "localIv: " + iv);
                Log.e(TAG, "localKey: " + key);
                addTask(RetrofitHelper.getInstance().getService().login(
                        Constants.CLIENT, Constants.PACKAGE, Constants.VER, phone,
                        token, timeStampBeanHttpRespond.data.id, timeStampBeanHttpRespond.data.val,
                        UserManager.getInstance().getDeviceToken(),psw,Constants.LOGIN_TYPE), new Consumer<HttpRespond<LoginData>>() {
                    @Override
                    public void accept(HttpRespond<LoginData> respond) throws Exception {
                        if (respond.result == 1) {
                            Log.e(TAG, "token: " + respond.data.token);
                            Log.e(TAG, "iv: " + respond.data.iv);
                            Log.e(TAG, "key: " + respond.data.key);
                            getView().onLoginComplete(respond);
                        } else {
                            getView().onLoginFailed(respond);
                        }
                        getView().hideLoadingView();
                    }
                });
            }
        });
    }

    public void getSmsCode(String phoneNum, String captcha) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getSmsCode(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE, phoneNum, 2, captcha
        ), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onSendSmsComplete(httpRespond);
            }
        });
    }

    public void getSmsNoCode(String phoneNum) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getSmsNoCode(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE, phoneNum, 2
        ), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onSendSmsComplete(httpRespond);
            }
        });
    }
}
