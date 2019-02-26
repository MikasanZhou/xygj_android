package com.xygj.app.jinrirong.fragment.user.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.user.view.BecomeProxyView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProxyBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/23.
 */

public class BecomeProxyPresenter extends BasePresenter<BecomeProxyView> {
    public void requestProxyNames(String token) {
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
        addTask(RetrofitHelper.getInstance().getService().requestProxy(requestBody), new Consumer<HttpRespond<List<ProxyBean>>>() {
            @Override
            public void accept(HttpRespond<List<ProxyBean>> listHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showProxyGrade(listHttpRespond);
            }
        });
    }

    public void getUserProtocol() {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getHtmlContent(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, Constants.USER_PROTOCOL), new Consumer<HttpRespond<HtmlData>>() {
            @Override
            public void accept(HttpRespond<HtmlData> respond) throws Exception {
                getView().hideLoadingView();
                getView().jumpToUserProtocolPage(respond);
            }
        });
    }
}
