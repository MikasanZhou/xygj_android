package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.RongKeStoreView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.RongKeBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuyougen on 2018/4/25.
 */

public class RongKeStorePresenter extends BasePresenter<RongKeStoreView> {
    public void getRongKeInfo() {

        JSONObject params = new JSONObject();

        try {
            params.put("client", Constants.CLIENT);
            params.put("package", Constants.PACKAGE);
            params.put("ver", Constants.VER);
            params.put("token", UserManager.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), params.toString());
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getRongKeInfo(requestBody), new Consumer<HttpRespond<RongKeBean>>() {
            @Override
            public void accept(HttpRespond<RongKeBean> rongKeBeanHttpRespond) throws Exception {
                if (rongKeBeanHttpRespond.result == 1) {
                    getView().onGetRongKeInfoSucceed(rongKeBeanHttpRespond.data);
                } else if (rongKeBeanHttpRespond.result == 0) {
                    getView().onGetRongKeInfoFailed(rongKeBeanHttpRespond.message);
                } else {
                    getView().onTokenInvalidate();
                }
                getView().hideLoadingView();
            }
        });
    }
}
