package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.CreditInfoView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.CreditInfoBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/27.
 */

public class CreditInfoPresenter extends BasePresenter<CreditInfoView> {
    public void requestCreditInfo(String token, String id) {
//        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestCreditInfo(requestBody), new Consumer<HttpRespond<CreditInfoBean>>() {
            @Override
            public void accept(HttpRespond<CreditInfoBean> creditInfoBeanHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showCreditInfo(creditInfoBeanHttpRespond);
            }
        });
    }

    public void queryAgain(int id) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", UserManager.getInstance().getToken());
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().againcheck(requestBody), new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mView.hideLoadingView();
                JSONObject result = new JSONObject(s);
                if (result.optInt("result") == 1) {
                    getView().onQuerySucceed(result.optString("message"));
                } else {
                    getView().onQueryFailed(result.optString("message"));
                }
            }
        });
    }
}
