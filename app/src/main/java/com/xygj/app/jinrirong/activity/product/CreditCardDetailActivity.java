package com.xygj.app.jinrirong.activity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.jinrirong.activity.product.presenter.CreditCardDetailPresenter;
import com.xygj.app.jinrirong.activity.product.view.CreditCardDetailView;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.CreditCardDetail;

public class CreditCardDetailActivity extends BaseMvpActivity<CreditCardDetailView, CreditCardDetailPresenter> implements CreditCardDetailView {

    private static final String EXTRA_CREDIT_ID = "id";

    @BindView(R.id.iv_cover)
    ImageView mIvCover;

    public static Intent getIntent(Context context, int id) {
        return new Intent(context, CreditCardDetailActivity.class).putExtra(EXTRA_CREDIT_ID, id);
    }

    @BindView(R.id.tv_year_fee)
    TextView mTvYearFee;

    @BindView(R.id.tv_year_fee_desc)
    TextView mTvYearFeeDesc;

    @BindView(R.id.tv_jifen)
    TextView mTvJiFen;

    @BindView(R.id.tv_jifen_desc)
    TextView mTvJiFenDesc;

    @BindView(R.id.tv_mianxi)
    TextView mTvMianXi;

    @BindView(R.id.tv_mianxi_desc)
    TextView mTvMianXiDesc;

    @BindView(R.id.tv_level)
    TextView mTvLevel;

    @BindView(R.id.iv_organization)
    ImageView mIvOrganization;

    @BindView(R.id.rv_rights)
    RecyclerView mRvRights;


    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getIntent().getIntExtra(EXTRA_CREDIT_ID, -1);
        if (mId != -1) {
            mPresenter.getCreditCardDetail(mId);
        }
    }

    @Override
    protected CreditCardDetailPresenter createPresenter() {
        return new CreditCardDetailPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_credit_card_detail;
    }

    @Override
    protected void initView() {

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

    }

    private CreditCardDetail mCardDetail;

    @Override
    public void onGetCreditCardDetailSucceed(CreditCardDetail cardDetail) {
        mCardDetail = cardDetail;
        Glide.with(this).load(cardDetail.getMainPic()).into(mIvCover);
        Glide.with(this).load(cardDetail.getFakaurl()).into(mIvOrganization);
        mTvYearFee.setText(cardDetail.getYarfeename());
        mTvYearFeeDesc.setText(cardDetail.getYeardesc());
        mTvJiFen.setText(cardDetail.getJifen1());
        mTvJiFenDesc.setText(cardDetail.getJifen2());
        mTvMianXi.setText(cardDetail.getFreetime());
        mTvMianXiDesc.setText(cardDetail.getFreedesc());
        mTvLevel.setText(cardDetail.getLevelname());
        mRvRights.setAdapter(new SAdapter(this, cardDetail.getQuanyconts()));
        mRvRights.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        if (mCardDetail == null) {
            return;
        }
        if (!UserManager.getInstance().isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.productStatistics(mId);
            }
        }, 300);

        startActivity(MyWebViewActivity.getIntent(this, "申请信用卡", mCardDetail.getOpenurl()));
    }

    private class SAdapter extends RecyclerView.Adapter<SAdapter.Holder> {

        private Context mContext;
        private List<String> mStringList;

        public SAdapter(Context context, List<String> stringList) {
            mContext = context;
            mStringList = stringList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_rights, parent, false);
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
