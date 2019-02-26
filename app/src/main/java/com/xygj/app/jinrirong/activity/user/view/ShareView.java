package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ShareBean;

/**
 * Created by Yangli on 2018/4/18.
 */

public interface ShareView extends BaseView {
    void showShareContent(HttpRespond<ShareBean> respond);
}
