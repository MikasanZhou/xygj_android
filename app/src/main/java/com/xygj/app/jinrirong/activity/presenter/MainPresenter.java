package com.xygj.app.jinrirong.activity.presenter;

import android.util.Log;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.common.utils.AppUtils;
import com.xygj.app.jinrirong.MyApplication;
import com.xygj.app.jinrirong.activity.view.MainView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PopBean;
import com.xygj.app.jinrirong.model.UpdateBean;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/5/3.
 */

public class MainPresenter extends BasePresenter<MainView> {
    public void getPopWindowData() {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getPopInfo(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE), new Consumer<HttpRespond<PopBean>>() {
            @Override
            public void accept(HttpRespond<PopBean> popBeanHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onGetPopInfo(popBeanHttpRespond);
            }
        });
    }

    public void checkUpdate() {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().checkUpdate(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE), new Consumer<HttpRespond<UpdateBean>>() {
            @Override
            public void accept(HttpRespond<UpdateBean> updateBeanHttpRespond) {
                if (updateBeanHttpRespond.result == 1) {
                    int serverVer = Integer.parseInt(updateBeanHttpRespond.data.getVer().replaceAll("\\.", ""));
                    int localVer = Integer.parseInt(AppUtils.getVersionName(MyApplication.getContext()).replaceAll("\\.", ""));
                    if (serverVer > localVer) {
                        getView().onGetUpdate(updateBeanHttpRespond.data);
                    }
                }
            }
        });
    }

    public void sendData(String address, String ip) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", UserManager.getInstance().getToken());
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            JSONObject data = new JSONObject();
            data.put("ip", ip);
            data.put("city", address);
            requestData.put("dynamic", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data---参数", requestData.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().sendAddressAndIP(requestBody), new Consumer<String>() {
            @Override
            public void accept(String str) {
                Log.e("data---上传", str);
            }
        });
    }
}
