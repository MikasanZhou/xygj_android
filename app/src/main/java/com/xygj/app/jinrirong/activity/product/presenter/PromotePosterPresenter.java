package com.xygj.app.jinrirong.activity.product.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.PromotePosterView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PosterBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/25.
 */

public class PromotePosterPresenter extends BasePresenter<PromotePosterView> {
    public void requestPromotePoster(String token, int id) {
        mView.showLoadingView();
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
        addTask(RetrofitHelper.getInstance().getService().requestPromotePoster(requestBody), new Consumer<HttpRespond<PosterBean>>() {
            @Override
            public void accept(HttpRespond<PosterBean> posterBeanHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showPromotePoster(posterBeanHttpRespond);
            }
        });
    }
}
