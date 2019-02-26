package com.xygj.app.jinrirong.activity.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.QueryCreditView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.QueryResultBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/25.
 */

public class QueryCreditPresenter extends BasePresenter<QueryCreditView> {
    public void submitQuery(String token, String data) {
        mView.hideLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("dynamic", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().queryCredit(requestBody), new Consumer<HttpRespond<QueryResultBean>>() {
            @Override
            public void accept(HttpRespond<QueryResultBean> queryResultBeanHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onQuerySubmitted(queryResultBeanHttpRespond);
            }
        });
    }
}
