package com.xygj.app.jinrirong.activity.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.xygj.app.jinrirong.activity.user.presenter.SharePresenter;
import com.xygj.app.jinrirong.activity.user.view.ShareView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ShareBean;

public class ShareActivity extends BaseMvpActivity<ShareView, SharePresenter> implements ShareView {
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.iv_poster_bg)
    ImageView ivPosterBg;
    @BindView(R.id.rl_share_wx)
    RelativeLayout rlShareWx;
    @BindView(R.id.rl_share_qq)
    RelativeLayout rlShareQQ;
    @BindView(R.id.rl_share_pyq)
    RelativeLayout rlSharePyq;
    @BindView(R.id.rl_share_qz)
    RelativeLayout rlShareQz;
    @BindView(R.id.rl_share_wb)
    RelativeLayout rlShareWb;
    private ShareBean shareBean;
    private SHARE_MEDIA platform;

    @BindView(R.id.rl_poster)
    RelativeLayout mRlPoster;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_share;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        mPresenter.requestShareContent(UserManager.getInstance().getToken());
    }

    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter();
    }

//    @OnClick({R.id.rl_share_wx, R.id.rl_share_qq, R.id.rl_share_qz,
//            R.id.rl_share_pyq, R.id.rl_share_wb})
//    public void onShareClick(final View view) {
//        final ShareActivity context = ShareActivity.this;
//        platform = SHARE_MEDIA.QQ;
//        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
//                .onGranted(new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                        switch (view.getId()) {
//                            case R.id.rl_share_wx:
//                                platform = SHARE_MEDIA.WEIXIN;
//                                break;
//                            case R.id.rl_share_qq:
//                                platform = SHARE_MEDIA.QQ;
//                                break;
//                            case R.id.rl_share_qz:
//                                platform = SHARE_MEDIA.QZONE;
//                                break;
//                            case R.id.rl_share_pyq:
//                                platform = SHARE_MEDIA.WEIXIN_CIRCLE;
//                                break;
//                            case R.id.rl_share_wb:
//                                platform = SHARE_MEDIA.SINA;
//                                break;
//                        }
//                        UMWeb web = new UMWeb(shareBean.getShareUrl());
//                        web.setTitle(shareBean.getTitle());
//                        web.setDescription(" ");
//                        web.setThumb(new UMImage(context, R.mipmap.ic_launcher));
//                        UMShareUtils.shareWithWeb(context, web, platform);
//                    }
//                }).onDenied(new Action() {
//            @Override
//            public void onAction(List<String> permissions) {
//                ToastUtils.showShort(context, "权限拒绝");
//            }
//        }).start();
//    }

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
    public void showShareContent(HttpRespond<ShareBean> respond) {
        if (respond.result == 1) {
            shareBean = respond.data;
            Glide.with(this).load(shareBean.url2).into(ivPosterBg);
//        ivQrCode.setImageBitmap(CodeUtils.createImage(shareBean.getShareUrl(),
//                400, 400, null));
        } else {
            Toast.makeText(this, respond.message, Toast.LENGTH_SHORT).show();
            finish();
        }
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
                    Toast.makeText(ShareActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ShareActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    @NonNull
    private Bitmap getPosterBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mRlPoster.getWidth(), mRlPoster.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mRlPoster.draw(canvas);
        return bitmap;
    }

    @OnClick(R.id.ll_copy_link)
    public void copyLink() {
        if (shareBean == null) {
            Toast.makeText(this, "尚未获取到信息，请刷新重试", Toast.LENGTH_SHORT).show();
            return;
        }
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(ClipData.newPlainText("", shareBean.getShareUrl()));
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ll_share_poster)
    public void sharePoster() {
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        UMImage image = new UMImage(ShareActivity.this, getPosterBitmap());
                        new ShareAction(ShareActivity.this).withMedia(image).setDisplayList(
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                .setCallback(UMShareUtils.getShareListener(ShareActivity.this)).open();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.showShort(ShareActivity.this, "权限拒绝");
            }
        }).start();
    }

    @OnClick(R.id.ll_share_link)
    public void shareLink() {
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        UMWeb web = new UMWeb(shareBean.getShareUrl());
                        web.setTitle("极速贷分享推广");
                        web.setDescription(" ");
                        web.setThumb(new UMImage(ShareActivity.this, getPosterBitmap()));
                        new ShareAction(ShareActivity.this).withMedia(web).setDisplayList(
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                .setCallback(UMShareUtils.getShareListener(ShareActivity.this)).open();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtils.showShort(ShareActivity.this, "权限拒绝");
            }
        }).start();
    }
}
