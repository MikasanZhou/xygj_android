package com.xygj.app.jinrirong.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.xygj.app.R;
import com.xygj.app.jinrirong.common.base.BaseActivity;

import java.util.List;


public class SplashActivity extends BaseActivity {

    //页面跳转延迟时长
    private static final long VALUE_DELAYED_TIME = 1400;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected void initData() {
        AndPermission.with(this).permission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //拒绝
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startMainActivity();
                            }
                        }, VALUE_DELAYED_TIME);
                    }
                })
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //通过
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startMainActivity();
                            }
                        }, VALUE_DELAYED_TIME);
                    }
                }).start();
    }

    /**
     * 启动主页面
     */
    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
