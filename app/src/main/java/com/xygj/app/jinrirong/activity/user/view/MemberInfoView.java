package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * Created by Yangli on 2018/4/19.
 */

public interface MemberInfoView extends BaseView {
    void onGetMemberInfo(HttpRespond<String> respond);

    void onSubmitMemberInfoDone(HttpRespond respond);
}
