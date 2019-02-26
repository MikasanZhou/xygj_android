package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.CommonProblemPresenter;
import com.xygj.app.jinrirong.activity.user.view.CommonProblemView;
import com.xygj.app.jinrirong.adpter.ProblemListAdapter;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProblemBean;

public class CommonProblemActivity extends BaseMvpActivity<CommonProblemView, CommonProblemPresenter> implements CommonProblemView {

    @BindView(R.id.rv_help_list)
    RecyclerView mRvHelpList;
    List<ProblemBean> list;
    private ProblemListAdapter adapter;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initView() {
        mRvHelpList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        list = new ArrayList<>();
        adapter = new ProblemListAdapter(list);
        mRvHelpList.setAdapter(adapter);
        mPresenter.getProblemList(0);
    }

    @Override
    protected CommonProblemPresenter createPresenter() {
        return new CommonProblemPresenter();
    }

    @Override
    public void showLoadingView() {
        showLoadingDialog();
    }

    @Override
    public void hideLoadingView() {
        hideLoadingDialog();
    }

    @Override
    public void onTokenInvalidate() {

    }

    @Override
    public void onNetworkConnectFailed() {

    }

    @Override
    public void showProblems(HttpRespond<List<ProblemBean>> respond) {
        if (respond.result == 1) {
            list.clear();
            list.addAll(respond.data);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }
}
