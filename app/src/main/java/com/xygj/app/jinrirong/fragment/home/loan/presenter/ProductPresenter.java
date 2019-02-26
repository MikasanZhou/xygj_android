package com.xygj.app.jinrirong.fragment.home.loan.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.home.loan.view.ProductView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProductBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * Created by Yangli on 2018/4/23.
 */

public class ProductPresenter extends BasePresenter<ProductView> {
    public void requestRecProductList(int type, String token) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("Itype", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestRecProductList(requestBody), new Consumer<HttpRespond<List<ProductBean>>>() {
            @Override
            public void accept(HttpRespond<List<ProductBean>> respond) throws Exception {
                mView.showRecProductLists(respond);
            }
        });
    }

    public void requestProductList(int type, String token, int page) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("rows", 6);
            requestData.put("page", page);
            requestData.put("Itype", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestProductList(requestBody), new Consumer<HttpRespond<List<ProductBean>>>() {
            @Override
            public void accept(HttpRespond<List<ProductBean>> respond) throws Exception {
                mView.showProductLists(respond);
            }
        });
    }
}
