package com.xygj.app.jinrirong;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.xygj.app.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by xuyougen on 2018/4/11.
 */

public class MyApplication extends Application {
    private static Context mApplicationContext;

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
        UMConfigure.init(this,"5c3f18e1b465f532c2000a87","",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.setLogEnabled(true);
        debug();

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