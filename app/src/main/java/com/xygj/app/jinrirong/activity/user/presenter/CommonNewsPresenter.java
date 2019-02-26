package com.xygj.app.jinrirong.activity.user.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.CommonNewsView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.CommonNewsDetail;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;

/**
 * Created by xuyougen on 2018/4/25.
 */

public class CommonNewsPresenter extends BasePresenter<CommonNewsView> {
    public void getCommonNews() {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getCommonNews(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                2, 0, 100), new Consumer<HttpRespond<List<CommonNews>>>() {
                    @Override
                    public void accept(HttpRespond<List<CommonNews>> listHttpRespond) throws Exception {
                        if (listHttpRespond.result == 1) {
                            getView().onGetCommonNewsSucceed(listHttpRespond.data);
                        }else{
                            getView().onGetCommonNewsFailed(listHttpRespond.message);
                        }
                        getView().hideLoadingView();
                    }
                }
        );
    }

    public void getCommonNewsDetail(int id) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance()
                        .getService()
                        .getCommonNewsDetail(
                                Constants.CLIENT,
                                Constants.PACKAGE,
                                Constants.VER,
                                id), new Consumer<HttpRespond<CommonNewsDetail>>() {
                    @Override
                    public void accept(HttpRespond<CommonNewsDetail> commonNewsDetailHttpRespond) throws Exception {
                        if (commonNewsDetailHttpRespond.result==1) {
                            getView().onGetCommonNewsDetailSucceed(commonNewsDetailHttpRespond.data);
                        }else{
                            getView().onGetCommonNewsDetailFailed(commonNewsDetailHttpRespond.message);
                        }
                        getView().hideLoadingView();
                    }
                }
        );
    }
}
