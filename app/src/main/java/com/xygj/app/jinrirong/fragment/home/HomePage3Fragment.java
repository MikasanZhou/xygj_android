package com.xygj.app.jinrirong.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.adapter.UniFragmentPagerAdapter;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.activity.user.RongKeStoreActivity;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.loan.ProductFragment;
import com.xygj.app.jinrirong.fragment.home.presenter.LoanPresenter;
import com.xygj.app.jinrirong.fragment.home.view.LoanView;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;

public class HomePage3Fragment extends BaseMvpFragment<LoanView, LoanPresenter> implements LoanView {

    @BindView(R.id.tb_sale)
    TabLayout mTabLayout;
    @BindView(R.id.cb_banner)
    ConvenientBanner<String> mCbBanner;
    @BindView(R.id.vp_sale)
    ViewPager mViewPager;
    private HomeBanner banner;

    public HomePage3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page3, container, false);
        ButterKnife.bind(this, layout);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        fragmentTitles.add("贷款产品");
        fragmentTitles.add("信用卡产品");
//        fragmentTitles.add("一键代理");

        fragmentList.add(ProductFragment.newInstance(1));
        fragmentList.add(ProductFragment.newInstance(2));
//        fragmentList.add(new Fragment());
//        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new UniFragmentPagerAdapter(getChildFragmentManager(), fragmentList, fragmentTitles));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && mViewPager.getCurrentItem() == 2) {
                    if (UserManager.getInstance().isLogin()) {
                        startActivity(new Intent(getActivity(), RongKeStoreActivity.class));
                        mViewPager.setCurrentItem(0, false);
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        mViewPager.setCurrentItem(0, false);
                    }
                }
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        return layout;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_one_key_proxy)
    public void tv_one_key_proxy() {
        if (UserManager.getInstance().isLogin()) {
            startActivity(new Intent(getActivity(), RongKeStoreActivity.class));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    protected LoanPresenter createPresenter() {
        return new LoanPresenter();
    }

    @Override
    protected void lazyLoadData() {
        getPresenter().getTopImg();
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
    public void showTopImg(HttpRespond<List<HomeBanner>> respond) {
        if (respond.result == 1) {
            mBannerList.clear();
            mBannerList.addAll(respond.data);
            mCbBanner.setCanLoop(true);
            mCbBanner.setScrollDuration(800);
            mCbBanner.startTurning(3000);
            mCbBanner.setPageIndicator(new int[]{R.drawable.shape_dot_gray, R.drawable.shape_dot_black})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            List<String> datas = new ArrayList<>();
            for (HomeBanner homeBanner : mBannerList) {
                datas.add(homeBanner.getPic());
            }
            mCbBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new HomePage1Fragment.NetImageLoadHolder();
                }
            }, datas);

            mCbBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeBanner banner = mBannerList.get(position);
                    String title = banner.getName();
                    String url = banner.getUrl();
                    if (url == null || url.length() == 0 || url.trim().equals("#"))
                        return;
                    startActivity(MyWebViewActivity.getIntent(getContext(), title, url));
                }
            });
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }
}
