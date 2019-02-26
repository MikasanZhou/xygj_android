package com.xygj.app.common.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * 通用的 FragmentPagerAdapter
 * Created by xuyougen on 2018/3/1.
 */

public class UniFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mBaseFragmentList;

    public UniFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
        super(fm);
        mBaseFragmentList = fragments;
    }

    public UniFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments, @NonNull List<String> titles) {
        super(fm);
        mBaseFragmentList = fragments;
        mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null) return "";
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mBaseFragmentList.size();
    }
}
