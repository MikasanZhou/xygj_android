package com.xygj.app.jinrirong.activity.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProblemBean;

/**
 *
 * Created by Yangli on 2018/5/30.
 */

public interface HelpView extends BaseView {
    void showHelpList(HttpRespond<List<ProblemBean>> respond);
}
