package com.xygj.app.jinrirong.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.lang.ref.WeakReference;

import com.xygj.app.jinrirong.common.base.BaseActivity;

/**
 * Created by xuyougen on 2018/4/11.
 */

public class LoginStatusChangeReceiver extends BroadcastReceiver {

    public static final String ACTION_LOGIN_STATUS_CHANGE = "login_status_change";

    public static final int TYPE_LOGIN = 0x33;
    public static final int TYPE_LOGOUT = 0x11;
    private static final String EXTRA_TYPE = "extra_type";

    private WeakReference<BaseActivity> mWeakReference;

    public static void sendBroadCast(LocalBroadcastManager broadcastManager, int type) {
        Intent intent = new Intent(ACTION_LOGIN_STATUS_CHANGE);
        intent.putExtra(EXTRA_TYPE, type);
        broadcastManager.sendBroadcast(intent);
    }

    public LoginStatusChangeReceiver() {
    }

    public LoginStatusChangeReceiver(BaseActivity activity) {
        mWeakReference = new WeakReference<BaseActivity>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra(EXTRA_TYPE, -1);
        if (mWeakReference.get() != null && type != -1) {
            switch (type) {
                case TYPE_LOGIN:
                    mWeakReference.get().onLoginSucceedEvent();
                    break;
                case TYPE_LOGOUT:
                    mWeakReference.get().onLogOutEvent();
                    break;
            }
        }
    }
}
