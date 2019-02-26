package com.xygj.app.jinrirong.fragment.home.income.user;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class UserIncomeFragment extends Fragment {

    @BindView(R.id.tb_bar)
    TabLayout mTabLayout;

    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;

    public UserIncomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_user_income, container, false);
        ButterKnife.bind(this, layout);

        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(UserIncomeDetailFragment.newInstance(""));

        List<String> strings =new ArrayList<>();
        strings.add("推荐会员");

        mViewPager.setAdapter(new UniFragmentPagerAdapter(getChildFragmentManager(),fragmentList,strings));
        mTabLayout.setupWithViewPager(mViewPager);

        return layout;
    }

}
