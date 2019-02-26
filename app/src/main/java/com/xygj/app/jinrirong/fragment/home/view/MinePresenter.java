package com.xygj.app.jinrirong.fragment.home.view;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ModuleBean;
import com.xygj.app.jinrirong.model.NewMessageBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/19.
 */

public class MinePresenter extends BasePresenter<MineView> {
    public void requestMemberInfo(String token) {
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
        addTask(RetrofitHelper.getInstance().getService().requestMemberInfo(requestBody), new Consumer<HttpRespond<String>>() {
            @Override
            public void accept(HttpRespond<String> stringHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showMineData(stringHttpRespond);
            }
        });
        addTask(RetrofitHelper.getInstance().getService().requestMemberInfo2(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                if (httpRespond.result!=1) {
                    getView().onTokenInvalidate();
                }
            }
        });
    }

    public void requestModuleInfo(String token) {
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
        addTask(RetrofitHelper.getInstance().getService().requestMemberModule(requestBody), new Consumer<HttpRespond<List<ModuleBean>>>() {
            @Override
            public void accept(HttpRespond<List<ModuleBean>> listHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showModule(listHttpRespond);
            }
        });
    }

    public void getMessageFlag(String token) {
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
        addTask(RetrofitHelper.getInstance().getService().newMessage(requestBody), new Consumer<HttpRespond<NewMessageBean>>() {
            @Override
            public void accept(HttpRespond<NewMessageBean> respond) throws Exception {
                getView().onNewMessage(respond);
            }
        });
    }
}
