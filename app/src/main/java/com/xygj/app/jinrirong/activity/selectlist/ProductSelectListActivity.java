package com.xygj.app.jinrirong.activity.selectlist;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.jinrirong.activity.selectlist.presenter.ProductListPresenter;
import com.xygj.app.jinrirong.activity.selectlist.view.ProductListView;
import com.xygj.app.jinrirong.adpter.LoanAdapter;
import com.xygj.app.jinrirong.adpter.home.ProductListAdapter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.model.LoanProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

public class ProductSelectListActivity extends BaseMvpActivity<ProductListView, ProductListPresenter> implements ProductListView, OnLoadmoreListener {
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

    String termId = "";
    int tid = 0;
    int cid = 0;
    boolean isFirst = true;
    String nids = "0";
    int page = 0;
    int rows = 1000;
    @BindView(R.id.rv_product)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.card_view)
    ConstraintLayout cardView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private LoanAdapter mLoanAdapter;
    private List<LoanProduct> mLoanProductList = new ArrayList<>();
    private ProductListAdapter productListAdapter;

    @Override
    protected ProductListPresenter createPresenter() {
        return new ProductListPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_product_list2;
    }

    @Override
    protected void initView() {
        productListAdapter = new ProductListAdapter(R.layout.item_home_product, mLoanProductList);
        AlphaAnimatorAdapter selectAnimation = new AlphaAnimatorAdapter(productListAdapter, mRecyclerView);
        mRecyclerView.setAdapter(selectAnimation);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.bg_product_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setIHaveType(cid, "");
            }
        });

        String url = getIntent().getStringExtra(Constants.BANNER_URL);
        String name = getIntent().getStringExtra(Constants.BANNER_NAME);
        tvTitle.setText(name);
        cid = Integer.valueOf(url);
    }

    @Override
    protected void initData() {
        super.initData();
        setIHaveType(cid, "");
    }

    public void setIHaveType(int id, String name) {
        cid = id;
        tid = 0;
        nids = "0";
        page = 0;
        rows = 100;
        termId = "";

        researchData();
//        if (singleChooseAdapter2 != null) singleChooseAdapter2.setmCurChosePosition(cid);
    }


    private void researchData() {
        refreshLayout.autoRefresh();
        mPresenter.getLoanList(termId, tid, cid, nids, page, rows);
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
        cardView.setVisibility(View.VISIBLE);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void onGetLoanListSucceed(List<LoanProduct> loanProductList) {
        cardView.setVisibility(View.VISIBLE);
        ivEmpty.setVisibility(View.GONE);
        refreshLayout.finishRefresh();
        refreshLayout.setEnableRefresh(true);
        mLoanProductList.clear();
        mLoanProductList.addAll(loanProductList);
        productListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetLoanListFailed(String message) {
        ivEmpty.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        mLoanProductList.clear();
        productListAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
    }

}
