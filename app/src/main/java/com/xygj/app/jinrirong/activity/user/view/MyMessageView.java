package com.xygj.app.jinrirong.activity.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.MessageBean;

/**
 * Created by Yangli on 2018/4/18.
 */

public interface MyMessageView extends BaseView {
    void showMessageTypes(HttpRespond<List<List<MessageBean>>> respond);
    void onMarkMessages(HttpRespond respond);
}
