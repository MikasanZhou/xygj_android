package com.xygj.app.jinrirong.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.adapter.UniFragmentPagerAdapter;
import com.xygj.app.jinrirong.common.base.BaseActivity;
import com.xygj.app.jinrirong.fragment.user.buy_proxy.BecomeProxyFragment;
import com.xygj.app.jinrirong.fragment.user.buy_proxy.ProductDescFragment;
import com.xygj.app.jinrirong.fragment.user.buy_proxy.ShouldKnownFragment;

/**
 * 购买代理
 */
public class BuyLoanProxyActivity extends BaseActivity {


    @BindView(R.id.tb_bar)
    TabLayout mTabLayout;

    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;

    List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_buy_loan_proxy;
    }

    @Override
    protected void initView() {
        mFragmentList.add(new ProductDescFragment());
        mFragmentList.add(new ShouldKnownFragment());
        mFragmentList.add(new BecomeProxyFragment());
        List<String> titles = new ArrayList<>();
        titles.add("产品描述");
        titles.add("购买须知");
        titles.add("成为代理");
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new UniFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList, titles));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }


    @OnClick(R.id.iv_common_question)
    public void gotoCommonQuestion() {
        startActivity(new Intent(this, CommonProblemActivity.class));
    }
}
