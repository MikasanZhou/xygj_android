package com.xygj.app.jinrirong.fragment.home.income;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.RecIncomeModel;

public interface ChildView extends BaseView {
    void showPromoteList(List<RecIncomeModel> data);

    void onGetPromoteListFailed(String message);
}
