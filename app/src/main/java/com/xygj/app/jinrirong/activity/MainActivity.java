package com.xygj.app.jinrirong.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.xygj.app.R;
import com.xygj.app.common.adapter.UniFragmentPagerAdapter;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.IpGetUtil;
import com.xygj.app.jinrirong.activity.presenter.MainPresenter;
import com.xygj.app.jinrirong.activity.view.MainView;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.dialog.PicDialog;
import com.xygj.app.jinrirong.fragment.home.HomeFragment;
import com.xygj.app.jinrirong.fragment.home.HomePage1Fragment;
import com.xygj.app.jinrirong.fragment.home.HomePage2Fragment;
import com.xygj.app.jinrirong.fragment.home.HomePage3Fragment;
import com.xygj.app.jinrirong.fragment.home.HomePage4Fragment;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PopBean;
import com.xygj.app.jinrirong.model.UpdateBean;
import com.xygj.app.jinrirong.widget.UpdateDialog;

/**
 * 主页面
 */
public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView {
    @BindView(R.id.vp_fragment)
    ViewPager mVpFragment;
    @BindView(R.id.rg_tab)
    RadioGroup mRgTab;
    @BindView(R.id.iv_get_money)
    ImageView mIvGetMoney;
    private List<Fragment> mFragments = new ArrayList<>();

    private String address = "用户未授权", ipStr = "用户未授权";

    public static int tempCid = -1;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        initTabBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        startLoaction();
    }

    private void startLoaction() {
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setMockEnable(false);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    Log.e("data---定位", aMapLocation.toString() );
                    address = aMapLocation.getAddress();
                    if(mPresenter!=null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ipStr = IpGetUtil.GetNetIp();
                                mPresenter.sendData(address, ipStr);
                            }
                        }).start();

                    }
                } else {
                    Log.e("data---定位失败", aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo());
                }
            }
        });
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    /**
     * 初始化tab 栏
     */
    private void initTabBar() {
        mFragments.add(new HomeFragment());
        mFragments.add(new HomePage2Fragment());
        mFragments.add(new HomePage3Fragment());
        mFragments.add(new HomePage4Fragment());
        mVpFragment.setAdapter(new UniFragmentPagerAdapter(getSupportFragmentManager(), mFragments));
        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_0:
                        mVpFragment.setCurrentItem(0, false);
                        break;
                    case R.id.rb_1:
                        mVpFragment.setCurrentItem(1, false);
                        break;
                    case R.id.rb_2:
                        mVpFragment.setCurrentItem(2, false);
                        break;
                    case R.id.rb_3:
                        mVpFragment.setCurrentItem(3, false);
                        break;
                }
            }
        });
        mVpFragment.setOffscreenPageLimit(4);
        //默认选中第一个页面
        mRgTab.check(R.id.rb_0);
    }

    @OnClick(R.id.iv_get_money)
    void iv_get_money() {
        if (mPopBean != null) {
            String url = mPopBean.getYouTan().getYtanUrl();
            if (url != null && url.length() != 0 && !url.trim().equals("#")) {
                startActivity(MyWebViewActivity.getIntent(MainActivity.this, "", mPopBean.getYouTan().getYtanUrl()));
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getPopWindowData();
        mPresenter.checkUpdate();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    private static final long DELAY_TIME = 1500;
    long lastPressBackKeyTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastPressBackKeyTime < DELAY_TIME) {
            super.onBackPressed();
        } else {
            lastPressBackKeyTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkMoudle(int position) {
        switch (position) {
            case 0:
                mRgTab.check(R.id.rb_0);
                break;
            case 1:
                mRgTab.check(R.id.rb_1);
                break;
            case 2:
                mRgTab.check(R.id.rb_2);
                break;
            case 3:
                mRgTab.check(R.id.rb_3);
                break;
        }
    }

    public void jumpToDiscoverLoan(int id, String name) {
        tempCid = id;
        checkMoudle(1);
        ((HomePage2Fragment) mFragments.get(1)).setIHaveType(id, name);
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

    PopBean mPopBean;

    @Override
    public void onGetPopInfo(HttpRespond<PopBean> respond) {
        if (respond.result == 1) {
            mPopBean = respond.data;
            if(mPopBean.getBigTan().getTcstatus()==2||mPopBean.getBigTan().getTcstatus()==3){

                PicDialog.newInstance(respond.data.getBigTan().getTanImg(), respond.data.getBigTan().getTanUrl())
                        .show(getSupportFragmentManager(), "pic");
                String img = mPopBean.getYouTan().getYtanImg();
                if (img != null && img.length() != 0) {
                    Glide.with(this).load(img).into(mIvGetMoney);
                    mIvGetMoney.setVisibility(View.VISIBLE);
                } else {
                    mIvGetMoney.setVisibility(View.GONE);
                }
            }

        }
    }


    @Override
    public void onGetUpdate(final UpdateBean updateBean) {
        Dialog dialog = new UpdateDialog.Builder(this)
                .setTitle("发现新版本")
                .setContent(updateBean.getUpdates())
                .setSingleBtn(updateBean.isForceUpdate())
                .setListener(new UpdateDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        if (!updateBean.isForceUpdate()) dialog.dismiss();
                        openSystemBrowser(updateBean.getUrl());
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).build();
        dialog.setCancelable(!updateBean.isForceUpdate());
        dialog.show();
    }

    /**
     * 打开系统浏览器
     *
     * @param url
     */
    private void openSystemBrowser(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "创建下载任务失败，请检查更新地址", Toast.LENGTH_SHORT).show();
        }
    }
}
