package com.xygj.app.jinrirong.activity.product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.widget.CustomDialog;
import com.xygj.app.jinrirong.activity.product.presenter.ProductDetailPresenter;
import com.xygj.app.jinrirong.activity.product.view.ProductDetailView;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.LoanProduct;

public class ProductDetailActivity extends BaseMvpActivity<ProductDetailView, ProductDetailPresenter> implements ProductDetailView {


    private static String EXTRA_PRODUCT_ID = "extra_id";
    @BindView(R.id.rv_condition)
    RecyclerView mRvCondition;
    @BindView(R.id.rv_strategy)
    RecyclerView mRvStrategy;
    @BindView(R.id.rv_require)
    RecyclerView mRvRequire;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_days)
    TextView mTvDays;
    @BindView(R.id.tv_apply_num)
    TextView mTvApplyNum;
    @BindView(R.id.tv_fee)
    TextView mTvFee;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.iv_img)
    ImageView mIvIcon;
    @BindView(R.id.tv_pass_rate)
    TextView mTvPassRate;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private LoanProduct mLoanProduct;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_product_detail;
    }

    public static Intent getIntent(Context context, int loanProductId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, loanProductId);
        return intent;
    }

    private int mProductId = -1;

    @Override
    protected void initView() {
        mProductId = getIntent().getIntExtra(EXTRA_PRODUCT_ID, -1);
        if (mProductId == -1) {
            Toast.makeText(this, "非法进入", Toast.LENGTH_SHORT).show();
            finish();
        }
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromServer();
            }
        }, 300);
    }

    private void getDataFromServer() {
        mPresenter.getProductDetail(mProductId);
    }
    private void getFromServer() {
        mPresenter.productStatistics(mProductId);
    }

    /**
     * 立即申请
     */
    @OnClick(R.id.btn_apply)
    public void applyNow() {
        if (mLoanProduct == null) {
            Toast.makeText(this, "未获取到信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (UserManager.getInstance().isLogin()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getFromServer();
                }
            }, 300);

            startActivity(new Intent(MyWebViewActivity.getIntent(this, mLoanProduct.getName(), mLoanProduct.getOpenurl())));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
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
        hideLoadingDialog();
        new CustomDialog.Builder(this)
                .setTitle("哎呀，连接失败")
                .setContent("是否尝试重新连接？")
                .setListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        getDataFromServer();
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                }).build().show();
    }

    @Override
    public void onGetProductDetailSucceed(LoanProduct loanProduct) {
        mRefreshLayout.setRefreshing(false);
        mLoanProduct = loanProduct;
        mTvTitle.setText(loanProduct.getName());
        mTvDesc.setText(loanProduct.getIntro());
        mTvPassRate.setText(loanProduct.getPassRate());
        mTvMoney.setText(loanProduct.getTypeName());
        mTvDays.setText(loanProduct.getJkdays());
        mTvApplyNum.setText(loanProduct.getAppNumbs());
        mTvFee.setText(loanProduct.getDayfeeRate());
        Glide.with(this).load(loanProduct.getLogurl())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(mIvIcon);


        mRvCondition.setAdapter(new MyAdapter(loanProduct.getConditIDs()));
        mRvCondition.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mRvRequire.setAdapter(new MyAdapter(loanProduct.getNeedIDs()));
        mRvRequire.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mRvStrategy.setAdapter(new SAdapter(loanProduct.getDownconts()));
        mRvStrategy.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

    }

    @Override
    protected ProductDetailPresenter createPresenter() {
        return new ProductDetailPresenter();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        private List<String> mStringList;

        MyAdapter(List<String> stringList) {
            mStringList = stringList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_condition, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.title.setText(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView title;

            public Holder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
            }
        }
    }


    private class SAdapter extends RecyclerView.Adapter<SAdapter.Holder> {

        private List<String> mStringList;

        SAdapter(List<String> stringList) {
            mStringList = stringList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_loan_strategy, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.title.setText(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView title;

            public Holder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
            }
        }
    }
}
