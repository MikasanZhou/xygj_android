package com.xygj.app.jinrirong.fragment.home.income.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.home.income.view.UserIncomeView;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/26.
 */

public class UserIncomePresenter extends BasePresenter<UserIncomeView> {
    public void requestUserIncomeList(String token, int page) {
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
        addTask(RetrofitHelper.getInstance().getService().requestUserIncome(requestBody), new Consumer<HttpRespond<String>>() {
            @Override
            public void accept(HttpRespond<String> respond) throws Exception {
                mView.hideLoadingView();
                mView.showUserIncomeList(respond);
            }
        });
    }
}
