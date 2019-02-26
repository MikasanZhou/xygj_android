package com.xygj.app.jinrirong.activity.product.presenter;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.ProductDetailView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoanProduct;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuyougen on 2018/4/17.
 */

public class ProductDetailPresenter extends BasePresenter<ProductDetailView> {
    public void getProductDetail(int productId) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getLoanProductDetail(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER,
                productId),
                new Consumer<HttpRespond<LoanProduct>>() {
                    @Override
                    public void accept(HttpRespond<LoanProduct> loanProductHttpRespond) throws Exception {
                        if (loanProductHttpRespond.result == 1) {
                            getView().onGetProductDetailSucceed(loanProductHttpRespond.data);
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
