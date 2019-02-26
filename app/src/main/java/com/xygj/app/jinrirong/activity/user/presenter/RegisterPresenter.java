package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.activity.user.view.RegisterView;
import com.xygj.app.jinrirong.model.HtmlData;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuyougen on 2018/4/12.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {
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
                Constants.CLIENT, Constants.VER, Constants.PACKAGE, phoneNum, 2, captcha
        ), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onSendSmsComplete(httpRespond);
            }
        });
    }

    public void register(String phoneNum, String smsCode, String psw, String inviteCode) {
        getView().showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("Mobile", phoneNum);
            requestData.put("Code", smsCode);
            requestData.put("Password", psw);
            requestData.put("Rid", inviteCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().register(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                getView().hideLoadingView();
                mView.onRegisterComplete(httpRespond);
            }
        });
    }

    public void getRegisterProtocol() {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getHtmlContent(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, Constants.REGISTER_PROTOCOL), new Consumer<HttpRespond<HtmlData>>() {
            @Override
            public void accept(HttpRespond<HtmlData> registerProtocolHttpRespond) throws Exception {
                getView().hideLoadingView();
                if (registerProtocolHttpRespond.result == 1) {
                    getView().onGetRegisterProtocolSucceed(registerProtocolHttpRespond.data);
                }
            }
        });
    }
}
