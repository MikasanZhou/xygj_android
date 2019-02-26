package com.xygj.app.jinrirong.fragment.home.loan.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.home.loan.view.WalletView;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/24.
 */

public class WalletPresenter extends BasePresenter<WalletView> {
    public void requestMoneyData(String token, int page) {
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
        addTask(RetrofitHelper.getInstance().getService().requestMoneyData(requestBody), new Consumer<HttpRespond<String>>() {
            @Override
            public void accept(HttpRespond<String> respond) throws Exception {
                mView.showMoneyData(respond);
            }
        });
    }
}
