package com.xygj.app.jinrirong.config;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.xygj.app.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xuyougen on 2018/4/11.
 */

public class RetrofitHelper {

    private Retrofit mRetrofit;
    private static RetrofitHelper INSTANCE;

    private RetrofitHelper() {
        init();
    }

    private void init() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        //设置chrome调试
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(new StethoInterceptor());
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiFactory.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitHelper();
        }
        return INSTANCE;
    }

    public ApiService getService() {
        return mRetrofit.create(ApiService.class);
    }

}
