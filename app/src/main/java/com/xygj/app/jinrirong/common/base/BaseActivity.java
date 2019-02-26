package com.xygj.app.jinrirong.common.base;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.xygj.app.jinrirong.receiver.LoginStatusChangeReceiver;

/**
 * Created by xuyougen on 2018/4/11.
 */

public abstract class BaseActivity extends com.xygj.app.common.base.BaseActivity {


    private LocalBroadcastManager mLocalBroadcastManager;
    private LoginStatusChangeReceiver mLoginStatusChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLoginStatusChangeReceiver = new LoginStatusChangeReceiver(this);
        mLocalBroadcastManager.registerReceiver(
                mLoginStatusChangeReceiver, new IntentFilter(LoginStatusChangeReceiver.ACTION_LOGIN_STATUS_CHANGE)
        );
    }

    public void onLogOutEvent() {
    }

    public void onLoginSucceedEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mLoginStatusChangeReceiver);
    }


    protected void notifiLoginStatusChanged(int type) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
