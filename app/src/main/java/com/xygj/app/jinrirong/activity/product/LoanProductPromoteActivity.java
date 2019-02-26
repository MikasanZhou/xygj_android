package com.xygj.app.jinrirong.activity.product;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.CustomDialog;
import com.xygj.app.jinrirong.activity.product.presenter.LoanProductPromotePresenter;
import com.xygj.app.jinrirong.activity.product.view.LoanProductPromoteView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.RankBean;
import com.xygj.app.jinrirong.model.RankListBean;

/**
 * 贷款产品推广详情
 */
public class LoanProductPromoteActivity extends BaseMvpActivity<LoanProductPromoteView, LoanProductPromotePresenter> implements LoanProductPromoteView {

    @BindView(R.id.iv_top_img)
    ImageView ivTopImg;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.ll_rank)
    LinearLayout llRank;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rv_rank)
    RecyclerView mRecyclerView;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.rv_jiesuan)
    RecyclerView mRvJieSuan;
    @BindView(R.id.rv_canyufangshi)
    RecyclerView mRvCanYuFangShi;
    @BindView(R.id.rv_gongzijieshao)
    RecyclerView mRvGongZiJieShao;

    @BindView(R.id.tv_one_label)
    TextView mTvOneLabel;
    @BindView(R.id.tv_one_base)
    TextView mTvOneBase;
    @BindView(R.id.tv_one_total)
    TextView mTvOneTotal;

    @BindView(R.id.tv_two_label)
    TextView mTvTwoLabel;
    @BindView(R.id.tv_two_base)
    TextView mTvTwoBase;
    @BindView(R.id.tv_two_extra)
    TextView mTvTwoExtra;
    @BindView(R.id.tv_two_total)
    TextView mTvTwoTotal;

    @BindView(R.id.tv_three_label)
    TextView mTvThreeLabel;
    @BindView(R.id.tv_three_base)
    TextView mTvThreeBase;
    @BindView(R.id.tv_three_extra)
    TextView mTvThreeExtra;
    @BindView(R.id.tv_three_total)
    TextView mTvThreeTotal;

    @BindView(R.id.tv_base_salary)
    TextView mTvBaseSalary;
    @BindView(R.id.tv_up_salary)
    TextView mTvUpSalary;

    private int productId;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_loan_product_promote;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }

    private void initFakeData(RankListBean.ProductInfo productInfo) {
        if (productInfo.getAccountType() != null) {
            mRvJieSuan.setAdapter(new TextAdapter(productInfo.getAccountType()));
            mRvJieSuan.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }
        if (productInfo.getPartType() != null) {
            mRvCanYuFangShi.setAdapter(new TextAdapter(productInfo.getPartType()));
            mRvCanYuFangShi.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }

        if (productInfo.getFeeIntro() != null) {
            mRvGongZiJieShao.setAdapter(new TextAdapter(productInfo.getFeeIntro()));
            mRvGongZiJieShao.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }

        if (productInfo.getStepIntro() != null) {
            try {
                mTvOneLabel.setText(productInfo.getStepIntro().get(0));
                mTvTwoLabel.setText(productInfo.getStepIntro().get(1));
                mTvThreeLabel.setText(productInfo.getStepIntro().get(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mTvOneBase.setText(productInfo.getStepBase() + "" + productInfo.getStepUnit());
        mTvOneTotal.setText(productInfo.getStepBase() + "" + productInfo.getStepUnit());

        mTvTwoBase.setText(productInfo.getStepBase() + "" + productInfo.getStepUnit());
        mTvTwoExtra.setText(productInfo.getStepInc1() + "" + productInfo.getStepUnit());
        mTvTwoTotal.setText(productInfo.getAccountFee1() + "" + productInfo.getStepUnit());


        mTvThreeBase.setText(productInfo.getStepBase() + "" + productInfo.getStepUnit());
        mTvThreeExtra.setText(productInfo.getStepInc2() + "" + productInfo.getStepUnit());
        mTvThreeTotal.setText(productInfo.getAccountFee2() + "" + productInfo.getStepUnit());

        mTvBaseSalary.setText(productInfo.getBaseFee());
        mTvUpSalary.setText(productInfo.getStepFee());
    }

    @OnClick(R.id.btn_generate_qr_code)
    public void generateQrCode() {
        Intent intent = new Intent(this, GenerateProductPromotePosterActivity.class);
        intent.putExtra("product_id", productId);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        productId = getIntent().getIntExtra("product_id", -1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromServer();
            }
        }, 300);
    }

    private void getDataFromServer() {
        mPresenter.getTopImg();
        mPresenter.requestPromoteInfo(UserManager.getInstance().getToken(), productId);
    }

    @Override
    protected LoanProductPromotePresenter createPresenter() {
        return new LoanProductPromotePresenter();
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
    public void showTopImg(HttpRespond<List<HomeBanner>> respond) {
        mRefreshLayout.setRefreshing(false);
        if (respond.result == 1) {
            Glide.with(this).load(respond.data.get(0).getPic()).into(ivTopImg);
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    @Override
    public void showPromoteInfo(HttpRespond<RankListBean> respond) {
        mRefreshLayout.setRefreshing(false);
        RankListBean.ProductInfo productInfo = respond.data.productInfo;
        if (respond.result == 1) {
            Glide.with(this).load(productInfo.getPicUrl()).into(ivProduct);
            tvProduct.setText(productInfo.getName());
            initFakeData(productInfo);
            if (respond.data.list.size() > 0) {
                tvDate.setText(getString(R.string.rank_info_text, productInfo.getDate()));
                mRecyclerView.setAdapter(new MyAdapter(respond.data.list));
            } else {
                llRank.setVisibility(View.GONE);
            }
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    class TextAdapter extends RecyclerView.Adapter<TextAdapter.Holder> {
        List<String> mStrings;

        public TextAdapter(List<String> strings) {
            mStrings = strings;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_string, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.tvTitle.setText(mStrings.get(position));
        }

        @Override
        public int getItemCount() {
            return mStrings.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_title)
            TextView tvTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
        private List<RankBean> beans;
        private int mDefaultColor;

        MyAdapter(List<RankBean> beans) {
            this.beans = beans;
            mDefaultColor = ContextCompat.getColor(LoanProductPromoteActivity.this,
                    R.color.colorLightGray);
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_rank, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.rankNo.setText(String.valueOf(position + 1));
            if (position == 0) {
                holder.rankNo.setBackgroundResource(R.drawable.bg_rank_no_1);
                holder.rankNo.setTextColor(Color.WHITE);
            } else if (position == 1) {
                holder.rankNo.setBackgroundResource(R.drawable.bg_rank_no_2);
                holder.rankNo.setTextColor(Color.WHITE);
            } else if (position == 2) {
                holder.rankNo.setBackgroundResource(R.drawable.bg_rank_no_3);
                holder.rankNo.setTextColor(Color.WHITE);
            } else {
                holder.rankNo.setBackgroundColor(Color.TRANSPARENT);
                holder.rankNo.setTextColor(mDefaultColor);
            }
            RankBean bean = beans.get(position);
            holder.rankPhoneNum.setText(bean.phoneNum);
            holder.rankMoney.setText(getString(R.string.money_mark, bean.money));
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_rank_no)
            TextView rankNo;
            @BindView(R.id.tv_phone_num)
            TextView rankPhoneNum;
            @BindView(R.id.tv_money)
            TextView rankMoney;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
