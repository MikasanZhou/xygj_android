package com.xygj.app.jinrirong.fragment.home.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.home.view.HomeView;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoanCateAndLocation;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.NewMessageBean;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuyougen on 2018/4/17.
 */

public class HomePresenter extends BasePresenter<HomeView> {
    public void getBanner() {
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getHomeBanner(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                1,
                                20),
                new Consumer<HttpRespond<List<HomeBanner>>>() {
                    @Override
                    public void accept(HttpRespond<List<HomeBanner>> listHttpRespond) throws Exception {
                        if (listHttpRespond.result == 1) {
                            getView().onGetBannerSucceed(listHttpRespond.data);
                        }
                    }
                }
        );
    }

    public void getLoanCategory() {
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getHomeLoanCategory(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                8),
                new Consumer<HttpRespond<LoanCateAndLocation>>() {
                    @Override
                    public void accept(HttpRespond<LoanCateAndLocation> respond) throws Exception {
                        if (respond.result == 1) {
                            getView().onGetLoanCategorySucceed(respond.data);
                        }
                    }
                }
        );
    }

    public void getRecommendLoanList() {
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getHomeItems(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                1, 4),
                new Consumer<HttpRespond<List<LoanProduct>>>() {
                    @Override
                    public void accept(HttpRespond<List<LoanProduct>> listHttpRespond) throws Exception {
                        if (listHttpRespond.result==1) {
                            getView().onGetRecommendLoanListSucceed(listHttpRespond.data);
                        }else {
                            getView().onGetRecommendLoanListFiled();

                        }
                    }
                }
        );
    }

    public void getHotLoanLoanList() {
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getHomeItems(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                0, 20),
                new Consumer<HttpRespond<List<LoanProduct>>>() {
                    @Override
                    public void accept(HttpRespond<List<LoanProduct>> listHttpRespond) throws Exception {
                        if (listHttpRespond.result==1) {
                            getView().onGetHotLoanListSucceed(listHttpRespond.data);
                        }
                    }
                }
        );
    }

    public void getCommonNews() {
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getCommonNews(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                2, 0, 4), new Consumer<HttpRespond<List<CommonNews>>>() {
                    @Override
                    public void accept(HttpRespond<List<CommonNews>> listHttpRespond) throws Exception {
                        if (listHttpRespond.result == 1) {
                            getView().onGetCommonNewsSucceed(listHttpRespond.data);
                        }
                    }
                }
        );
    }

    public void getMessageFlag(String token) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().newMessage(requestBody), new Consumer<HttpRespond<NewMessageBean>>() {
            @Override
            public void accept(HttpRespond<NewMessageBean> respond) throws Exception {
                getView().onNewMessage(respond);
            }
        });
    }
}
