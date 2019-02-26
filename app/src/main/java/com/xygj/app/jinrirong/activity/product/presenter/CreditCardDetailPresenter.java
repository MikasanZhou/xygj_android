package com.xygj.app.jinrirong.activity.product.presenter;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.CreditCardDetailView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.CreditCardDetail;
import com.xygj.app.jinrirong.model.HttpRespond;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuyougen on 2018/4/23.
 */

public class CreditCardDetailPresenter extends BasePresenter<CreditCardDetailView> {
    public void getCreditCardDetail(int id) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getCreditCardDetail(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                id), new Consumer<HttpRespond<CreditCardDetail>>() {
            @Override
            public void accept(HttpRespond<CreditCardDetail> creditCardDetailHttpRespond) throws Exception {
                if (creditCardDetailHttpRespond.result == 1) {
                    getView().onGetCreditCardDetailSucceed(creditCardDetailHttpRespond.data);
                }
                getView().hideLoadingView();
            }
        });
    }

    public void productStatistics(int productId) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("productId", productId);
            requestData.put("token", UserManager.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().productStatistics(
                requestBody), new Consumer<HttpRespond>() {
                    @Override
                    public void accept(HttpRespond httpRespond) throws Exception {

                    }
                }
        );
    }
}
