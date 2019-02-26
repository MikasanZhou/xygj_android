package com.xygj.app.jinrirong.fragment.home.loan.presenter;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.loan.view.CustomerListView;
import com.xygj.app.jinrirong.model.ClientListBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/24.
 */

public class CustomerListPresenter extends BasePresenter<CustomerListView> {
    public void getClientList(String date, int page) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("token", UserManager.getInstance().getToken());
            requestData.put("dates", date);
            requestData.put("page", page);
            requestData.put("rows", 40);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().getClientList(requestBody), new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                JSONObject result = new JSONObject(s);
                if (result.optInt("result") == 1) {
                    HttpRespond<ClientListBean> httpRespond = new HttpRespond<>();
                    httpRespond.result = 1;
                    httpRespond.message = result.optString("message");
                    httpRespond.data = new Gson().fromJson(result.optString("data"), ClientListBean.class);
                    getView().showClientList(httpRespond);
                } else {
                    getView().onRequestFailed(result.optString("message"));
                }
            }
        });
    }
}
