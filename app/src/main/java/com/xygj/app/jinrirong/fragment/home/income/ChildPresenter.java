package com.xygj.app.jinrirong.fragment.home.income;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.RecIncomeModel;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChildPresenter extends BasePresenter<ChildView> {
    public void requestPromoteIncomeList(String token, int type, int status, int page) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("Itype", type);
            requestData.put("isdab", status);
            requestData.put("page", page);
            requestData.put("rows", 200);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestPromoteIncome2(requestBody), new Consumer<HttpRespond<List<RecIncomeModel>>>() {
            @Override
            public void accept(HttpRespond<List<RecIncomeModel>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().showPromoteList(listHttpRespond.data);
                }else{
                    getView().onGetPromoteListFailed(listHttpRespond.message);
                }
                mView.hideLoadingView();
            }
        });
    }
}
