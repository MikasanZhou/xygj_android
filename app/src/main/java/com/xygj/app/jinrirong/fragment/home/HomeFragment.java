package com.xygj.app.jinrirong.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xygj.app.R;
import com.xygj.app.common.utils.DensityUtils;
import com.xygj.app.jinrirong.activity.user.CreditDetail.CreditListActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.adapter.SelectedExplosionAdapter;
import com.xygj.app.jinrirong.fragment.adapter.SpreadAdapter;
import com.xygj.app.jinrirong.fragment.home.presenter.HomePresenter;
import com.xygj.app.jinrirong.fragment.home.view.HomeView;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoanCateAndLocation;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.NewMessageBean;
import com.xygj.app.jinrirong.widget.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;


/**
 * Created by Administrator on 2019/3/30 0030.
 */

public class HomeFragment extends BaseMvpFragment<HomeView, HomePresenter> implements HomeView {
    static {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = "下拉可以刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
        ClassicsHeader.REFRESH_HEADER_LASTTIME = "上次更新 M-d HH:mm";
        ClassicsHeader.REFRESH_HEADER_LASTTIME = "'Last update' M-d HH:mm";
    }
    @BindView(R.id.sr_refresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.home_recycler_view)
    RecyclerView homeRecyclerView;
    @BindView(R.id.recycler_selected_explosions)
    RecyclerView recyclerSelectedExplosions;
    private SpreadAdapter spreadAdapter;
    private List<HomeBanner> bannersList;
    private List<LoanProduct> selectedList;
    private Activity activity;
    private AlphaAnimatorAdapter animatorAdapter;
    private SelectedExplosionAdapter selectedExplosionAdapter;
    private AlphaAnimatorAdapter selectAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_fragemnt_layout, container, false);
        ButterKnife.bind(this, layout);


        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();

        srRefresh.setNestedScrollingEnabled(false
        );
        initSpreadAdapter();

        initSelectedExplosion();
    }

    private void initSelectedExplosion() {
        selectedList = new ArrayList<>();
        selectedExplosionAdapter = new SelectedExplosionAdapter(activity,R.layout.item_selected_applicaion_layout, selectedList);
        recyclerSelectedExplosions.setLayoutManager(new LinearLayoutManager(activity));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        recyclerSelectedExplosions.addItemDecoration(dividerItemDecoration);
        selectAnimation = new AlphaAnimatorAdapter(selectedExplosionAdapter, recyclerSelectedExplosions);
        recyclerSelectedExplosions.setAdapter(selectAnimation);
    }

    /**
     * 初始化顶部推荐recycelrview
     */
    private void initSpreadAdapter() {
        bannersList = new ArrayList<>();
        spreadAdapter = new SpreadAdapter(activity,R.layout.item_spread_layout, bannersList);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        animatorAdapter = new AlphaAnimatorAdapter(spreadAdapter, homeRecyclerView);

        homeRecyclerView.addItemDecoration(new GridDividerItemDecoration(DensityUtils.dp2px(activity, 20), Color.TRANSPARENT));
        homeRecyclerView.setAdapter(animatorAdapter);
        spreadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(activity, CreditListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onTokenInvalidate() {

    }

    @Override
    public void onNetworkConnectFailed() {

    }

    @Override
    public void onGetBannerSucceed(List<HomeBanner> list) {
        srRefresh.finishRefresh();

        bannersList.clear();
        bannersList.addAll(list);
        animatorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetLoanCategorySucceed(LoanCateAndLocation data) {

    }

    @Override
    public void onGetRecommendLoanListSucceed(List<LoanProduct> data) {

    }

    @Override
    public void onGetHotLoanListSucceed(List<LoanProduct> data) {
        selectedList.clear();
        selectedList.addAll(data);
        selectedExplosionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCommonNewsSucceed(List<CommonNews> commonNewsList) {

    }

    @Override
    public void onNewMessage(HttpRespond<NewMessageBean> respond) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void lazyLoadData() {
        getHomeData();
    }

    private void getHomeData() {
        getPresenter().getBanner();
        getPresenter().getLoanCategory();
        getPresenter().getHotLoanLoanList();
        getPresenter().getRecommendLoanList();
        getPresenter().getCommonNews();
        getPresenter().getMessageFlag(UserManager.getInstance().getToken());
    }

}
