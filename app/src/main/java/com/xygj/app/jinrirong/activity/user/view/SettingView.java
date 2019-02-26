package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by xuyougen on 2018/3/26.
 */

public interface SettingView extends BaseView {
    void onGetAboutUsInfoSucceed(HtmlData html);

    void onGetAboutUsInfoFailed(String msg);

    void onGetVersionDescSucceed(HttpRespond<HtmlData> respond);

    void onSignOut(HttpRespond respond);
}
