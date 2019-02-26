package com.xygj.app.jinrirong.activity.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.utils.UMShareUtils;
import com.xygj.app.jinrirong.activity.user.presenter.RongKeStorePresenter;
import com.xygj.app.jinrirong.activity.user.view.RongKeStoreView;
import com.xygj.app.jinrirong.model.RongKeBean;

/**
 * 融客店
 */
public class RongKeStoreActivity extends BaseMvpActivity<RongKeStoreView, RongKeStorePresenter> implements RongKeStoreView {

    @BindView(R.id.iv_poster)
    ImageView mIvPoster;

    RongKeBean mRongKeBean;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_rong_ke_store;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getRongKeInfo();
    }

    @Override
    protected RongKeStorePresenter createPresenter() {
        return new RongKeStorePresenter();
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

    @Override
    public void onGetRongKeInfoSucceed(RongKeBean rongKeBean) {
        mRongKeBean = rongKeBean;
        Glide.with(this).load(rongKeBean.getUrl2()).into(mIvPoster);
    }

    @OnClick(R.id.ll_save_poster)
    public void savePoster() {
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".png");
                try {
                    Bitmap bitmap = getPosterBitmap();
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    Toast.makeText(RongKeStoreActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RongKeStoreActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    @NonNull
    private Bitmap getPosterBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mIvPoster.getWidth(), mIvPoster.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mIvPoster.draw(canvas);
        return bitmap;
    }

    @OnClick(R.id.ll_copy_link)
    public void copyLink() {
        if (mRongKeBean == null) {
            Toast.makeText(this, "尚未获取到信息，请刷新重试", Toast.LENGTH_SHORT).show();
            return;
        }
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(ClipData.newPlainText("", mRongKeBean.getShareurl()));
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ll_share_poster)
    public void sharePoster() {
        final RongKeStoreActivity context =
                RongKeStoreActivity.this;
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        UMImage image = new UMImage(context, getPosterBitmap());
                        new ShareAction(context).withMedia(image).setDisplayList(
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                .setCallback(UMShareUtils.getShareListener(context)).open();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.showShort(context, "权限拒绝");
            }
        }).start();
    }

    @OnClick(R.id.ll_share_link)
    public void shareLink() {
        final RongKeStoreActivity context =
                RongKeStoreActivity.this;
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        UMWeb web = new UMWeb(mRongKeBean.getShareurl());
                        web.setTitle("融客店");
                        web.setDescription(" ");
                        web.setThumb(new UMImage(context, getPosterBitmap()));
                        new ShareAction(context).withMedia(web).setDisplayList(
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                .setCallback(UMShareUtils.getShareListener(context)).open();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.showShort(context, "权限拒绝");
            }
        }).start();
    }


    @Override
    public void onGetRongKeInfoFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
