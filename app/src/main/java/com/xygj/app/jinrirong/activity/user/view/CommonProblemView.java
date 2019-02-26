package com.xygj.app.jinrirong.activity.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProblemBean;

/**
 * Created by Yangli on 2018/4/23.
 */

public interface CommonProblemView extends BaseView {
    void showProblems(HttpRespond<List<ProblemBean>> respond);
}
