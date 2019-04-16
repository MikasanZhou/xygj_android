package com.xygj.app.jinrirong.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.utils.LogUtil;
import com.xygj.app.jinrirong.activity.MainActivity;
import com.xygj.app.jinrirong.activity.product.CreditCardCenterActivity;
import com.xygj.app.jinrirong.activity.product.ProductDetailActivity;
import com.xygj.app.jinrirong.activity.user.CommonNewsActivity;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.activity.user.MyMessageActivity;
import com.xygj.app.jinrirong.activity.user.QueryCreditActivity;
import com.xygj.app.jinrirong.activity.user.RongKeStoreActivity;
import com.xygj.app.jinrirong.adpter.LoanAdapter;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.presenter.HomePresenter;
import com.xygj.app.jinrirong.fragment.home.view.HomeView;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoanCateAndLocation;
import com.xygj.app.jinrirong.model.LoanCategory;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.NewMessageBean;
import com.xygj.app.jinrirong.widget.CustomScrollView;
import com.xygj.app.jinrirong.widget.LooperTextView;

public class HomePage1Fragment extends BaseMvpFragment<HomeView, HomePresenter> implements HomeView {
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.view_new_msg)
    View newMsg;
    @BindView(R.id.rv_loan_category)
    RecyclerView mRvLoanCategory;
    @BindView(R.id.cb_banner)
    ConvenientBanner<String> mCbBanner;
    @BindView(R.id.rv_credit_card_category)
    RecyclerView mRvCreditCardCategory;
    @BindView(R.id.rv_loan_hot)
    RecyclerView mRvLoanHot;
    @BindView(R.id.rv_loan_platform)
    RecyclerView mRvLoanPlatform;
    @BindView(R.id.sv_custom)
    CustomScrollView mScrollView;
    @BindView(R.id.fl_title_bg)
    FrameLayout mFlTitleBg;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSrRefresh;

    @BindView(R.id.ltv_common_news)
    LooperTextView mLooperTextView;

    private int mTitleBarHeight;
    private boolean mIsTitleBgTranslucent;

    public HomePage1Fragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page1, container, false);
        ButterKnife.bind(this, layout);

        mSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData();
            }
        });

        mFlTitleBg.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                mTitleBarHeight = mFlTitleBg.getHeight();
            }
        });

        //设置滚动监听
        mScrollView.setScrollOffsetChangedListener(new CustomScrollView.OnScrollOffsetChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                LogUtil.d(t + "");
                if (t < mTitleBarHeight) {
                    mIsTitleBgTranslucent = true;
                    if (t <= 0) {
                        mFlTitleBg.setAlpha(0);
                        return;
                    }
                    float alpha = (t * 1f) / (mTitleBarHeight * 1f);
                    mFlTitleBg.setAlpha(alpha);
                } else {
                    //根据标记判断是否设置透明度，重复设置很影响性能
                    if (!mIsTitleBgTranslucent) {
                        return;
                    }
                    mFlTitleBg.setAlpha(1f);
                    mIsTitleBgTranslucent = false;
                }
            }
        });

        mRvCreditCardCategory.setAdapter(new CreditCardCategoryAdapter());
        mRvCreditCardCategory.setLayoutManager(new GridLayoutManager(getActivity(), 1) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        return layout;
    }

    @OnClick(R.id.ll_common_news)
    public void gotoCommonNewsPage() {
        startActivity(new Intent(getActivity(), CommonNewsActivity.class));
    }

    private void getHomeData() {
        getPresenter().getBanner();
        getPresenter().getLoanCategory();
        getPresenter().getHotLoanLoanList();
        getPresenter().getRecommendLoanList();
        getPresenter().getCommonNews();
        getPresenter().getMessageFlag(UserManager.getInstance().getToken());
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoadData() {
        getHomeData();
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

    private List<HomeBanner> mBannerList = new ArrayList<>();

    @Override
    public void onGetBannerSucceed(List<HomeBanner> bannerList) {
        mSrRefresh.setRefreshing(false);

        mBannerList.clear();
        mBannerList.addAll(bannerList);

        mCbBanner.setCanLoop(true);
        mCbBanner.setPageIndicator(new int[]{R.drawable.shape_dot_gray, R.drawable.shape_dot_black})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        mCbBanner.setScrollDuration(800);
        mCbBanner.startTurning(3000);
        List<String> datas = new ArrayList<>();
        for (HomeBanner homeBanner : mBannerList) {
            datas.add(homeBanner.getPic());
        }
        mCbBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetImageLoadHolder();
            }
        }, datas);

        mCbBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomeBanner banner = mBannerList.get(position);
                String title = banner.getName();
                String url = banner.getUrl();
                if (!url.startsWith("#") && !TextUtils.isEmpty(url))
                    startActivity(MyWebViewActivity.getIntent(getActivity(), title, url));
            }
        });
    }

    @Override
    public void onGetLoanCategorySucceed(LoanCateAndLocation data) {
        mSrRefresh.setRefreshing(false);
        //不显示贷款类型
        data.list.clear();
        mRvLoanCategory.setAdapter(new LoanCategoryAdapter(getActivity(), data.list));
        mRvLoanCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        tvCity.setText(data.cityName);
    }

    @Override
    public void onGetRecommendLoanListSucceed(List<LoanProduct> data) {
        mSrRefresh.setRefreshing(false);

        mRvLoanPlatform.setAdapter(new LoanPlatformAdapter(getActivity(), data));
        mRvLoanPlatform.setLayoutManager(new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    public void onGetHotLoanListSucceed(List<LoanProduct> data) {
        mSrRefresh.setRefreshing(false);

        mRvLoanHot.setAdapter(new LoanAdapter(getActivity(), data));
        mRvLoanHot.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    public void onGetCommonNewsSucceed(List<CommonNews> commonNewsList) {
        List<String> strings = new ArrayList<>();
        for (CommonNews commonNews : commonNewsList) {
            strings.add(commonNews.getTitle());
        }
        mLooperTextView.setTipList(strings);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getMessageFlag(UserManager.getInstance().getToken());
    }

    @Override
    public void onNewMessage(HttpRespond<NewMessageBean> respond) {
        if (respond.result == 1) {
            if (respond.data.isNewNoticeMsg() || respond.data.isNewSystemMsg()) {
                newMsg.setVisibility(View.VISIBLE);
            } else {
                newMsg.setVisibility(View.GONE);
            }
        }
    }

    public static class NetImageLoadHolder implements Holder<String> {
        private ImageView image_lv;

        //可以是一个布局也可以是一个Imageview
        @Override
        public ImageView createView(Context context) {
            image_lv = new ImageView(context);
            image_lv.setScaleType(ImageView.ScaleType.FIT_XY);
            return image_lv;

        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(image_lv);
        }
    }

    @OnClick(R.id.iv_msg)
    void gotoMessage() {
        if (!UserManager.getInstance().isLogin()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getActivity(), MyMessageActivity.class));
    }

    private class LoanCategoryAdapter extends RecyclerView.Adapter<LoanCategoryAdapter.Holder> {

        private Context mContext;
        private List<LoanCategory> mCategoryList;

        LoanCategoryAdapter(Context context, List<LoanCategory> categoryList) {
            mContext = context;
            mCategoryList = categoryList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.item_loan_category, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            if (position > 3) {
                holder.lineHorizontal.setVisibility(View.GONE);
            } else {
                holder.lineHorizontal.setVisibility(View.VISIBLE);
            }

            final LoanCategory loanCategory = mCategoryList.get(position);
            Glide.with(mContext).load(loanCategory.getImageurl())
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                    .into(holder.img);
            holder.title.setText(loanCategory.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).jumpToDiscoverLoan(loanCategory.getID(), loanCategory.getName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title;
            View lineHorizontal;
            View lineVertical;

            public Holder(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.iv_img);
                title = itemView.findViewById(R.id.tv_title);
                lineHorizontal = itemView.findViewById(R.id.line_horizontal);
                lineVertical = itemView.findViewById(R.id.line_vertical);
            }
        }
    }


    private class LoanPlatformAdapter extends RecyclerView.Adapter<LoanPlatformAdapter.Holder> {
        private Context mContext;
        private List<LoanProduct> mProductList;

        LoanPlatformAdapter(Context context, List<LoanProduct> productList) {
            mContext = context;
            mProductList = productList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_loan_platform, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            final LoanProduct product = mProductList.get(position);
            holder.title.setText(product.getName());
            String wan = product.getAppNumbs();
            if (!wan.contains("万")) {
                try {
                    int applyNum = Integer.valueOf(wan);
                    if (applyNum > 10000)
                        wan = String.format("%s万", new DecimalFormat("0.0").format(applyNum * 1.0f / 10000f));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            holder.desc.setText(String.format("%s人申请", wan));
            Glide.with(mContext).load(product.getLogurl())
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                    .into(holder.img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(ProductDetailActivity.getIntent(mContext, product.getID()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title;
            TextView desc;

            public Holder(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.iv_img);
                title = itemView.findViewById(R.id.tv_title);
                desc = itemView.findViewById(R.id.tv_desc);
            }
        }
    }

    @Override
    protected void onVisibilityChanged(boolean isVisibleToUser) {
        //根据当前fragment的可视状态来改变banner控件的动画状态
        if (mCbBanner != null) {
            if (isVisibleToUser) {
                mCbBanner.startTurning(3000);
            } else {
                mCbBanner.stopTurning();
            }
        }
    }

    private class CreditCardCategoryAdapter extends RecyclerView.Adapter<CreditCardCategoryAdapter.Holder> {

//        String titles[] = new String[]{"贷款大全", "办信用卡", "我要赚钱", "黑名单查询"};
//        String subTitles[] = new String[]{"汇集各类网贷", "下卡快额度高", "邀请朋友赚钱", "老被拒?看是否黑了"};
//        private int[] icons = new int[]{R.mipmap.home_loan, R.mipmap.home_card, R.mipmap.home_community, R.mipmap.home_credit};
        String titles[] = new String[]{ };
        String subTitles[] = new String[]{ };
        private int[] icons = new int[]{ };

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_credit_card_category, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, int position) {
            if (position % 2 == 0) {
                holder.lineVertical.setVisibility(View.VISIBLE);
            } else {
                holder.lineVertical.setVisibility(View.GONE);
            }

            if (position == 2 | position == 3) {
                holder.lineHorizontal.setVisibility(View.GONE);
            } else {
                holder.lineHorizontal.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (holder.getAdapterPosition()) {
                        //贷款大全
                        case 0:
//                            if (getActivity() != null) {
//                                ((MainActivity) getActivity()).checkMoudle(1);
//                            }
                            if (!UserManager.getInstance().isLogin()) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                return;
                            }
                            startActivity(new Intent(getActivity(), QueryCreditActivity.class));
                            break;
                        //办信用卡
                        case 1:
                            startActivity(new Intent(getActivity(), CreditCardCenterActivity.class));
                            break;
                        //融客店
                        case 2:
                            if (!UserManager.getInstance().isLogin()) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                return;
                            }
                            startActivity(new Intent(getActivity(), RongKeStoreActivity.class));
                            break;
                        //贷款征信
                        case 3:
                            if (!UserManager.getInstance().isLogin()) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                return;
                            }
                            startActivity(new Intent(getActivity(), QueryCreditActivity.class));
                            break;
                    }
                }
            });

            holder.title.setText(titles[position]);
            holder.desc.setText(subTitles[position]);
            holder.image.setImageResource(icons[position]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        public class Holder extends RecyclerView.ViewHolder {
            View lineHorizontal;
            View lineVertical;
            TextView title;
            TextView desc;
            ImageView image;

            public Holder(View itemView) {
                super(itemView);
                lineHorizontal = itemView.findViewById(R.id.line_horizontal);
                lineVertical = itemView.findViewById(R.id.line_vertical);
                title = itemView.findViewById(R.id.tv_title);
                desc = itemView.findViewById(R.id.tv_desc);
                image = itemView.findViewById(R.id.iv_img);
            }
        }
    }

}
