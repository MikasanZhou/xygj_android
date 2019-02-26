package com.xygj.app.jinrirong.activity.product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.widget.CustomDialog;
import com.xygj.app.jinrirong.activity.product.presenter.CreditCardCenterPresenter;
import com.xygj.app.jinrirong.activity.product.view.CreditCardCenterView;
import com.xygj.app.jinrirong.adpter.CreditCardListAdapter;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.credit_card.Bank;

public class CreditCardCenterActivity extends BaseMvpActivity<CreditCardCenterView, CreditCardCenterPresenter> implements CreditCardCenterView {

    @BindView(R.id.rv_card)
    RecyclerView mRecyclerView1;
    @BindView(R.id.rv_card2)
    RecyclerView mRecyclerView2;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.iv_pointer)
    ImageView mIvPointer;
    @BindView(R.id.iv_3)
    ImageView mIv3;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindViews({R.id.tv_title1, R.id.tv_title2, R.id.tv_title3})
    List<TextView> mTvTitleList;
    @BindViews({R.id.tv_sub1, R.id.tv_sub2, R.id.tv_sub3})
    List<TextView> mTvSubList;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_credit_card_center;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }

    @OnClick({R.id.tv_more_1, R.id.tv_more_2})
    public void gotoMorePage() {
        startActivity(new Intent(this, CreditCardCenterMoreActivity.class));
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
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
        mPresenter.getCreditCardRecommendList(6);
        mPresenter.getQuickOpenCardList(20);
        mPresenter.getCreditCardBankList();
    }

    @Override
    protected CreditCardCenterPresenter createPresenter() {
        return new CreditCardCenterPresenter();
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

    private boolean mIsShownDialog = false;

    @Override
    public void onNetworkConnectFailed() {
        hideLoadingDialog();
        if (mIsShownDialog) return;
        mIsShownDialog = true;
        new CustomDialog.Builder(this)
                .setTitle("哎呀，连接失败")
                .setContent("是否尝试重新连接？")
                .setListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        mIsShownDialog = false;
                        getDataFromServer();
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                }).build().show();
    }

    private CreditCard card1, card2, card3;

    @Override
    public void OnGetCreditCardRecommendListSucceed(List<CreditCard> recommendCardList) {
        mRefreshLayout.setRefreshing(false);

        card1 = recommendCardList.get(0);
        Glide.with(this).load(card1.getLogurl()).into(mIv1);
        mTvTitleList.get(0).setText(card1.getName());
        mTvSubList.get(0).setText(card1.getIntro());

        card2 = recommendCardList.get(1);
        Glide.with(this).load(card2.getLogurl()).into(mIv2);
        mTvTitleList.get(1).setText(card2.getName());
        mTvSubList.get(1).setText(card2.getIntro());

        card3 = recommendCardList.get(2);
        Glide.with(this).load(card3.getLogurl()).into(mIv3);
        mTvTitleList.get(2).setText(card3.getName());
        mTvSubList.get(2).setText(card3.getIntro());
    }

    MyAdapter mAdapter;

    @Override
    public void onGetCreditCardBankListSucceed(List<Bank> bankList) {
        mRefreshLayout.setRefreshing(false);

        mAdapter = new MyAdapter(this, bankList);

        mRecyclerView1.setAdapter(mAdapter);
        mRecyclerView1.setLayoutManager(new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    public void OnGetQuickOpenCardListSucceed(List<CreditCard> creditCardList) {
        mRefreshLayout.setRefreshing(false);

        mRecyclerView2.setAdapter(new CreditCardListAdapter(this, creditCardList));
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @OnClick(R.id.ll_card1)
    public void card1() {
        if (card1 != null) {
            startActivity(CreditCardDetailActivity.getIntent(this, card1.getID()));
        }
    }

    @OnClick(R.id.ll_toggle_list)
    public void toggleList() {
        if (mAdapter != null) {
            if (mAdapter.toggleList()) {
                mIvPointer.setImageResource(R.mipmap.ic_down);
            } else {
                mIvPointer.setImageResource(R.mipmap.ic_up);
            }
        }
    }

    @OnClick(R.id.ll_card2)
    public void card2() {
        if (card2 != null) {
            startActivity(CreditCardDetailActivity.getIntent(this, card2.getID()));
        }
    }

    @OnClick(R.id.ll_card3)
    public void card3() {
        if (card3 != null) {
            startActivity(CreditCardDetailActivity.getIntent(this, card3.getID()));
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        private boolean isNormal = true;

        private Context mContext;
        List<Bank> mBanks;

        MyAdapter(Context context, List<Bank> banks) {
            mContext = context;
            mBanks = banks;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_bank_card, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            final Bank bank = mBanks.get(position);
            Glide.with(mContext).load(bank.getLogurl()).into(holder.cover);
            holder.title.setText(bank.getBankName());
            holder.desc.setText(bank.getIntro());
            holder.tip.setText(bank.getDesc());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(CreditCardCenterMoreActivity.getIntent(mContext, bank.getID()));
                }
            });
        }

        public boolean toggleList() {
            isNormal = !isNormal;
            notifyDataSetChanged();
            return isNormal;
        }

        @Override
        public int getItemCount() {
            return isNormal ? (mBanks.size() > 6 ? 6 : mBanks.size()) : mBanks.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            ImageView cover;
            TextView title;
            TextView desc;
            TextView tip;

            public Holder(View itemView) {
                super(itemView);
                cover = itemView.findViewById(R.id.iv_cover);
                title = itemView.findViewById(R.id.tv_title);
                desc = itemView.findViewById(R.id.tv_desc);
                tip = itemView.findViewById(R.id.tv_tip);
            }
        }
    }

}
