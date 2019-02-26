package com.xygj.app.jinrirong.fragment.home.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.home.view.LoanView;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;

/**
 * Created by Yangli on 2018/4/25.
 */

public class LoanPresenter extends BasePresenter<LoanView> {
    public void getTopImg() {
        addTask(RetrofitHelper.getInstance().getService().getHomeBanner(
                Constants.CLIENT, Constants.PACKAGE, Constants.VER, 5, 6),
                new Consumer<HttpRespond<List<HomeBanner>>>() {
                    @Override
                    public void accept(HttpRespond<List<HomeBanner>> listHttpRespond) throws Exception {getView().showTopImg(listHttpRespond);
                    }
                }
        );
    }
}
