package com.xygj.app.jinrirong;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.tencent.bugly.Bugly;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.xygj.app.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by xuyougen on 2018/4/11.
 */

public class MyApplication extends Application {
    private static Context mApplicationContext;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx88888888";

    static {
        // todo 分享配置
        // 配置第三方分享的appKey
        PlatformConfig.setWeixin("wx8d8d230a1d13f3b0", "3e4a0cb3cd41833611efee12c52bd9b9");//ab249c8e3e9fabe60b788ef5d306bd20
        //PlatformConfig.setSinaWeibo("1996373625", "86e6913558c7716676726495311c0f02", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1108079678", "rMUXoannDAVOcpLa");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationContext = this;
        UMConfigure.init(this, "5c3f18e1b465f532c2000a87", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "d6836733a9959f2f289ecfa2a2afd080");
        UMConfigure.setLogEnabled(true);
        debug();

        registerUMPush();
        Bugly.init(getApplicationContext(), "81461d0b1e", false);
    }

    private void registerUMPush() {
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("onSuccess", "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("onFailure", "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

    }

    private void debug() {

        /*Chrome进行网络调试chrome://inspect/#devices*/
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
        }
    }

    public static Context getContext() {
        return mApplicationContext;
    }
}
