package com.xygj.app.jinrirong.activity.user.presenter;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.PayModeView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PayInfoBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/23.
 */

public class PayModePresenter extends BasePresenter<PayModeView> {
    public void payQueryCreditInfo(String token, String payInfo) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("dynamic", payInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data---", requestData.toString() );
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().payQueryCreditInfo(requestBody), new Consumer<HttpRespond<PayInfoBean>>() {
            @Override
            public void accept(HttpRespond<PayInfoBean> respond) throws Exception {
                mView.hideLoadingView();
                if (respond.result==1) {
                    mView.onPayDone(respond);
                }else{
                    mView.onPayFailed(respond.message);
                }
            }
        });
    }

    public void buyProxy(String token, String payInfo) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("dynamic", payInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().payProxy(requestBody), new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mView.hideLoadingView();
                JSONObject result = new JSONObject(s);
                if (result.optInt("result") == 1) {
                    HttpRespond<PayInfoBean> httpRespond = new HttpRespond<>();
                    httpRespond.result = 1;
                    httpRespond.message = result.optString("message");
                    httpRespond.data = new Gson().fromJson(result.optString("data"), PayInfoBean.class);
                    mView.onPayDone(httpRespond);
                } else {
                    mView.onPayFailed(result.optString("message"));
                }
            }
        });
    }
}
