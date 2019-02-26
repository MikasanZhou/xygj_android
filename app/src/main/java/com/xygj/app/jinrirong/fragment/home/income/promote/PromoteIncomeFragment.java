package com.xygj.app.jinrirong.fragment.home.income.promote;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.common.adapter.UniFragmentPagerAdapter;
import com.xygj.app.jinrirong.fragment.home.income.FatherFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoteIncomeFragment extends Fragment {


    @BindView(R.id.tb_bar)
    TabLayout mTabLayout;

    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;

    public PromoteIncomeFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_promote_income, container, false);
        ButterKnife.bind(this, layout);

        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(PromoteIncomeDetailFragment.newInstance(2));
//        fragmentList.add(PromoteIncomeDetailFragment.newInstance(3));
        //1贷款商品 2信用卡商品
        fragmentList.add(FatherFragment.newInstance(1));
        fragmentList.add(FatherFragment.newInstance(2));
        fragmentList.add(PromoteIncomeDetailFragment.newInstance(4));

        List<String> strings = new ArrayList<>();
        strings.add("网贷");
        strings.add("信用卡");
        strings.add("征信");

        mViewPager.setAdapter(new UniFragmentPagerAdapter(getChildFragmentManager(), fragmentList, strings));
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        return layout;
    }

}
