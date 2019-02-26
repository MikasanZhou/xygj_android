package com.xygj.app.jinrirong.activity.product;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.product.presenter.ProductListPresenter;
import com.xygj.app.jinrirong.activity.product.view.ProductListView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProductBean;

public class ProductListActivity extends BaseMvpActivity<ProductListView, ProductListPresenter> implements ProductListView {

    @BindView(R.id.rv_product_list)
    RecyclerView rvProductList;
    private ProductAdapter adapter;
    private List<ProductBean> list;

    @Override
    protected ProductListPresenter createPresenter() {
        return new ProductListPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        adapter = new ProductAdapter(list);
        rvProductList.setAdapter(adapter);
        mPresenter.requestProductList(UserManager.getInstance().isLogin() ?
                UserManager.getInstance().getToken() : "");
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
    public void showProductList(HttpRespond<List<ProductBean>> respond) {
        if (respond.result == 1) {
            list.clear();
            list.addAll(respond.data);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    class ProductAdapter extends RecyclerView.Adapter<ProductListActivity.ProductAdapter.Holder> {
        private List<ProductBean> list;

        ProductAdapter(List<ProductBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ProductListActivity.ProductAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_recomend_product2, parent, false);
            return new ProductListActivity.ProductAdapter.Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductListActivity.ProductAdapter.Holder holder, int position) {
            final ProductBean bean = list.get(position);
            holder.tvShare.setVisibility(View.VISIBLE);
            Glide.with(ProductListActivity.this).load(bean.picUrl).into(holder.ivProduct);
            holder.tvProduct.setText(bean.name);
            if (bean.type == 1)
                holder.tvProductBonus.setText(bean.bonusRate + "个点");
            else
                holder.tvProductBonus.setText(bean.money + "元");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductListActivity.this, LoanProductPromoteActivity.class);
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
            @BindView(R.id.tv_share)
            TextView tvShare;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
