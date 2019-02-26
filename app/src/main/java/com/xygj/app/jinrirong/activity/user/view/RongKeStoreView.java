package com.xygj.app.jinrirong.activity.user.view;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.RongKeBean;

/**
 * Created by xuyougen on 2018/4/25.
 */

public interface RongKeStoreView extends BaseView{
    void onGetRongKeInfoSucceed(RongKeBean rongKeBean);

    void onGetRongKeInfoFailed(String message);
}
