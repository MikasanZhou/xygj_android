package com.xygj.app.jinrirong.activity.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PopBean;
import com.xygj.app.jinrirong.model.UpdateBean;

/**
 * Created by Yangli on 2018/5/3.
 */

public interface MainView extends BaseView {
    void onGetPopInfo(HttpRespond<PopBean> respond);

    void onGetUpdate(UpdateBean updateBean);
}
