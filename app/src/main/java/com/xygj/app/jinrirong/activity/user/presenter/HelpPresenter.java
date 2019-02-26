package com.xygj.app.jinrirong.activity.user.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.HelpView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProblemBean;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by Yangli on 2018/5/30.
 */

public class HelpPresenter extends BasePresenter<HelpView> {
    public void getHelpList(int page, int id, String keyword) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getHelpList(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, id, keyword, page, 10), new Consumer<HttpRespond<List<ProblemBean>>>() {
            @Override
            public void accept(HttpRespond<List<ProblemBean>> respond) throws Exception {
                getView().hideLoadingView();
                getView().showHelpList(respond);
            }
        });
    }
}
