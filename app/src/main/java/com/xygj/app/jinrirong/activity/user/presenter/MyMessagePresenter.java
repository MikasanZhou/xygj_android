package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.MyMessageView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.MessageBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/18.
 */

public class MyMessagePresenter extends BasePresenter<MyMessageView> {
    public void requestMassages(String token) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestMessages(requestBody), new Consumer<HttpRespond<List<List<MessageBean>>>>() {
            @Override
            public void accept(HttpRespond<List<List<MessageBean>>> listHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showMessageTypes(listHttpRespond);
            }
        });
    }

    public void markMessages(String token, int type) {
        //mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("token", token);
            requestData.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().markMessages(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                //mView.hideLoadingView();
                mView.onMarkMessages(httpRespond);
            }
        });
    }
}
