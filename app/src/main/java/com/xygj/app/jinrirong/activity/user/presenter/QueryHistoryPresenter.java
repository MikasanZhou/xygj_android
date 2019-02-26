package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.QueryHistoryView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HistoryBean;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/25.
 */

public class QueryHistoryPresenter extends BasePresenter<QueryHistoryView> {
    public void requestQueryHistory(String token, int page) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("page", page);
            requestData.put("rows", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestQueryHistory(requestBody), new Consumer<HttpRespond<List<HistoryBean>>>() {
            @Override
            public void accept(HttpRespond<List<HistoryBean>> listHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showQueryList(listHttpRespond);
            }
        });
    }
}
