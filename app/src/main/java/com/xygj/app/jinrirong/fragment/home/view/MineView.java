package com.xygj.app.jinrirong.fragment.home.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ModuleBean;
import com.xygj.app.jinrirong.model.NewMessageBean;

/**
 *
 * Created by Yangli on 2018/4/19.
 */

public interface MineView extends BaseView {
    void showMineData(HttpRespond<String> respond);

    void onNewMessage(HttpRespond<NewMessageBean> respond);

    void showModule(HttpRespond<List<ModuleBean>> listHttpRespond);
}
