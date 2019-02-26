package com.xygj.app.jinrirong.fragment.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.product.LoanProductPromoteActivity;
import com.xygj.app.jinrirong.activity.product.ProductListActivity;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.loan.view.ProductView;
import com.xygj.app.jinrirong.fragment.home.loan.presenter.ProductPresenter;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProductBean;

/**
 * 佣金产品
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseMvpFragment<ProductView, ProductPresenter> implements ProductView, OnLoadmoreListener {

    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.rv_product)
    RecyclerView mRvProduct;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private List<ProductBean> list;
    private int page;
    private String token;
    private int type;
    private ProductAdapter adapter;

    public static ProductFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("product_type", type);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, layout);
        mRvRecommend.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mRvProduct.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lazyLoadData();
            }
        });
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(this);
        return layout;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected ProductPresenter createPresenter() {
        return new ProductPresenter();
    }

    @Override
    protected void lazyLoadData() {
        list = new ArrayList<>();
        adapter = new ProductAdapter(list, 1);
        mRvProduct.setAdapter(adapter);
        page = 0;
        if (getArguments() != null) {
            type = getArguments().getInt("product_type", 0);
            token = UserManager.getInstance().isLogin() ?
                    UserManager.getInstance().getToken() : "";
            getPresenter().requestRecProductList(type, token);
            getPresenter().requestProductList(type, token, page);
        }
    }

    @OnClick({R.id.tv_show_more_product, R.id.tv_show_more_product_re})
    public void onShowMoreClick() {
        startActivity(new Intent(getContext(), ProductListActivity.class));
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
    public void showRecProductLists(HttpRespond<List<ProductBean>> respond) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (respond.result == 1) {
            mRvRecommend.setAdapter(new ProductAdapter(respond.data, 2));
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    @Override
    public void showProductLists(HttpRespond<List<ProductBean>> respond) {
        refreshLayout.finishLoadmore();
        mSwipeRefreshLayout.setRefreshing(false);
        if (respond.result == 1) {
            if (page == 0)
                list.clear();
            list.addAll(respond.data);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getPresenter().requestProductList(type, token, ++page);
    }

    class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
        private List<ProductBean> list;
        private int spanCount;

        ProductAdapter(List<ProductBean> list, int spanCount) {
            this.list = list;
            this.spanCount = spanCount;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(spanCount == 2 ?
                    R.layout.item_recomend_product : R.layout.item_recomend_product2, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            final ProductBean bean = list.get(position);
            Glide.with(getContext()).load(bean.picUrl).into(holder.ivProduct);
            holder.tvProduct.setText(bean.name);
            holder.tvDesc.setText(bean.des);
            holder.tvNum.setText(Html.fromHtml(String.format(
                    "<font color=\"#5461eb\">%s</font>人已申请", bean.num)));
            if (bean.type == 1){
                if(bean.smoney==null){
                    holder.tvProductBonus.setText("放款"+bean.bonusRate + "%");
                }
                else{
                    holder.tvProductBonus.setText("申请"+bean.smoney+"元+放款"+bean.bonusRate + "%");
                }
            }
            else{
                if(bean.smoney==null){
                    holder.tvProductBonus.setText("放款"+String.format("%s元", bean.money));
                }
                else{
                    holder.tvProductBonus.setText("申请"+bean.smoney+"元+放款"+String.format("%s元", bean.money));
                }

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!UserManager.getInstance().isLogin()) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        return;
                    }
                    Intent intent = new Intent(getActivity(), LoanProductPromoteActivity.class);
                    intent.putExtra("product_id", bean.id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_product)
            ImageView ivProduct;
            @BindView(R.id.tv_product)
            TextView tvProduct;
            @BindView(R.id.tv_product_bonus)
            TextView tvProductBonus;
            @BindView(R.id.tv_desc)
            TextView tvDesc;
            @BindView(R.id.tv_num)
            TextView tvNum;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
