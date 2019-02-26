package com.xygj.app.jinrirong.activity.product.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.product.view.ProductListView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProductBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/23.
 */

public class ProductListPresenter extends BasePresenter<ProductListView> {
    public void requestProductList(String token) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestProductList(requestBody), new Consumer<HttpRespond<List<ProductBean>>>() {
            @Override
            public void accept(HttpRespond<List<ProductBean>> listHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showProductList(listHttpRespond);
            }
        });
    }
}
