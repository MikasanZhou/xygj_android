package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.adapter.UniFragmentPagerAdapter;
import com.xygj.app.jinrirong.common.base.BaseActivity;
import com.xygj.app.jinrirong.fragment.home.loan.CustomerListFragment;
import com.xygj.app.jinrirong.fragment.home.loan.ProductFragment;
import com.xygj.app.jinrirong.fragment.home.loan.WalletFragment;

public class MoneyActivity extends BaseActivity {
    @BindView(R.id.tb_money)
    TabLayout mTabLayout;
    @BindView(R.id.vp_money)
    ViewPager mViewPager;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_money;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        fragmentTitles.add("佣金产品");
        fragmentTitles.add("客户列表");
        fragmentTitles.add("我的钱包");

        fragmentList.add(ProductFragment.newInstance(1));
        fragmentList.add(new CustomerListFragment());
        fragmentList.add(new WalletFragment());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new UniFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, fragmentTitles));
        mTabLayout.setupWithViewPager(mViewPager);

        int index = getIntent().getIntExtra("index", -1);
        if (index < 0) {
            index = 1;
        }
        mViewPager.setCurrentItem(index);
        mTabLayout.getTabAt(index).select();
    }
}
