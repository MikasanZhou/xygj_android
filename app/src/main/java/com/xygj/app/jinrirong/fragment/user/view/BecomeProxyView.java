package com.xygj.app.jinrirong.fragment.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProxyBean;

/**
 * Created by Yangli on 2018/4/23.
 */

public interface BecomeProxyView extends BaseView {
    void showProxyGrade(HttpRespond<List<ProxyBean>> respond);

    void jumpToUserProtocolPage(HttpRespond<HtmlData> respond);
}
