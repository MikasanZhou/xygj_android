package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.SettingView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuyougen on 2018/3/26.
 */

public class SettingPresenter extends BasePresenter<SettingView> {
    public void getAboutUsInfo() {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getHtmlContent(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, Constants.ABOUT_US), new Consumer<HttpRespond<HtmlData>>() {
            @Override
            public void accept(HttpRespond<HtmlData> htmlDataHttpRespond) throws Exception {
                mView.hideLoadingView();
                if (htmlDataHttpRespond.result == 1) {
                    mView.onGetAboutUsInfoSucceed(htmlDataHttpRespond.data);
                } else {
                    mView.onGetAboutUsInfoFailed(htmlDataHttpRespond.message);
                }
            }
        });
    }

    public void getVersionDesc() {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getHtmlContent(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, Constants.VERSION_INTRO), new Consumer<HttpRespond<HtmlData>>() {
            @Override
            public void accept(HttpRespond<HtmlData> htmlDataHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onGetVersionDescSucceed(htmlDataHttpRespond);
            }
        });
    }

    public void signOut(String token) {
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
        addTask(RetrofitHelper.getInstance().getService().signOut(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond httpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onSignOut(httpRespond);
            }
        });
    }
}
