package com.xygj.app.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 分享工具类
 * Created by Yangli on 2018/3/26.
 */

public class UMShareUtils {
    private static final String TAG = "ShareUtils";

    public static void shareWithPic(Activity activity, Bitmap bitmap, SHARE_MEDIA platform) {
        UMImage image = new UMImage(activity, bitmap);
        new ShareAction(activity).setPlatform(platform)
                .withMedia(image).setCallback(getShareListener(activity)).share();
    }

    public static void shareWithWeb(Activity activity, UMWeb web, SHARE_MEDIA platform) {
        new ShareAction(activity).setPlatform(platform)
                .withMedia(web).setCallback(getShareListener(activity)).share();
    }

    public static MyShareListener getShareListener(Context context) {
        return new MyShareListener(context);
    }

    private static class MyShareListener implements UMShareListener {
        Context context;

        MyShareListener(Context context) {
            this.context = context;
        }

        // 分享开始的回调
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.e(TAG, "onStart: ");
        }

        // 分享成功的回调
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e(TAG, "onResult: ");
            ToastUtils.showShort(context, "分享成功");
        }

        // 分享失败的回调
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e(TAG, "onError: ");
            t.printStackTrace();
            ToastUtils.showShort(context, "分享失败");
        }

        // 分享取消的回调
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.e(TAG, "onCancel: ");
            ToastUtils.showShort(context, "取消分享");
        }
    }
}
