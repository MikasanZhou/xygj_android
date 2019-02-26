package com.xygj.app.jinrirong.activity.user.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.CommonProblemView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProblemBean;
import io.reactivex.functions.Consumer;

/**
 * Created by Yangli on 2018/4/23.
 */

public class CommonProblemPresenter extends BasePresenter<CommonProblemView> {
    public void getProblemList(int page) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getProblemList(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, 1, page, 10
        ), new Consumer<HttpRespond<List<ProblemBean>>>() {
            @Override
            public void accept(HttpRespond<List<ProblemBean>> listHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.showProblems(listHttpRespond);
            }
        });
    }
}
