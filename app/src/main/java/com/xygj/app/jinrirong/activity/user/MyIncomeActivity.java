package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.adapter.UniFragmentPagerAdapter;
import com.xygj.app.common.base.BaseActivity;
import com.xygj.app.jinrirong.fragment.home.income.promote.PromoteIncomeFragment;
import com.xygj.app.jinrirong.fragment.home.income.user.UserIncomeFragment;

public class MyIncomeActivity extends BaseActivity {

    @BindView(R.id.rg_top)
    RadioGroup mRadioGroup;

    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_my_income;
    }

    @Override
    protected void initView() {
        mRadioGroup.check(R.id.rb_top1);
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new PromoteIncomeFragment());
        fragments.add(new UserIncomeFragment());
        mViewPager.setAdapter(new UniFragmentPagerAdapter(getSupportFragmentManager(),fragments));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_top1:
                        mViewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_top2:
                        mViewPager.setCurrentItem(1,false);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT,false);
    }
}
