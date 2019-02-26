package com.xygj.app.jinrirong.activity.user.presenter;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.user.view.ResetPwdView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;

/**
 * Created by Yangli on 2018/4/21.
 */

public class ResetPwdPresenter extends BasePresenter<ResetPwdView> {
    public void submitNewPwd(String phoneNum, String pwd) {
        mView.showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().resetPwd(
                Constants.CLIENT, Constants.VER, Constants.PACKAGE, phoneNum, pwd
        ), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                mView.hideLoadingView();
                mView.onResetPwdDone(respond);
            }
        });
    }
}
