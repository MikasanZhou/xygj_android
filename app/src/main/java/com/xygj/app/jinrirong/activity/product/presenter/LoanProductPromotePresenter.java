package com.xygj.app.jinrirong.activity.product.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.LoanProductPromoteView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.RankListBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/25.
 */

public class LoanProductPromotePresenter extends BasePresenter<LoanProductPromoteView> {
    public void getTopImg() {
        addTask(RetrofitHelper.getInstance().getService().getHomeBanner(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, 6, 1),
                new Consumer<HttpRespond<List<HomeBanner>>>() {
                    @Override
                    public void accept(HttpRespond<List<HomeBanner>> listHttpRespond) throws Exception {getView().showTopImg(listHttpRespond);
                    }
                }
        );
    }

    public void requestPromoteInfo(String token, int id) {
        getView().showLoadingView();
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
        addTask(RetrofitHelper.getInstance().getService().requestPromoteInfo(requestBody), new Consumer<HttpRespond<RankListBean>>() {
            @Override
            public void accept(HttpRespond<RankListBean> rankListBeanHttpRespond) throws Exception {
                getView().hideLoadingView();
                getView().showPromoteInfo(rankListBeanHttpRespond);
            }
        });
    }
}
