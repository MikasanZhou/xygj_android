package com.xygj.app.jinrirong.fragment.home.income;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.xygj.app.common.base.BaseLazyLoadFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FatherFragment extends BaseLazyLoadFragment {

    private static final String ARG_INCOME_TYPE = "income_type";
    int type;

    @BindView(R.id.tb_bar)
    TabLayout mTabLayout;

    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;

    public FatherFragment() {
    }

    public static FatherFragment newInstance(int type) {
        FatherFragment fragment = new FatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INCOME_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_father, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments() != null ? getArguments().getInt(ARG_INCOME_TYPE) : 0;
    }

    @Override
    protected void lazyLoadData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ChildFragment.newInstance(type, 0));
        fragmentList.add(ChildFragment.newInstance(type, 1));

        List<String> strings = new ArrayList<>();
        strings.add("待达标");
        strings.add("已达标");

        mViewPager.setAdapter(new UniFragmentPagerAdapter(getChildFragmentManager(), fragmentList, strings));
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
