package com.xygj.app.jinrirong.fragment.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/23.
 */

public interface ProductDescView extends BaseView {
    void showHtmlContent(HttpRespond<HtmlData> respond);
}
