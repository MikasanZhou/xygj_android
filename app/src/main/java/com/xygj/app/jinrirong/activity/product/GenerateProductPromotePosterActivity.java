package com.xygj.app.jinrirong.activity.product;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.utils.UMShareUtils;
import com.xygj.app.jinrirong.activity.product.presenter.PromotePosterPresenter;
import com.xygj.app.jinrirong.activity.product.view.PromotePosterView;
import com.xygj.app.jinrirong.adpter.ChoosePosterImageAdapter;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.PosterBean;

/**
 * 生成我的专属海报
 */
public class GenerateProductPromotePosterActivity extends BaseMvpActivity<PromotePosterView, PromotePosterPresenter> implements PromotePosterView {
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.rv_poster)
    RecyclerView mRvPoster;
    private PosterBean posterBean;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_generate_product_promote_poster;
    }

    @Override
    protected void initView() {
        mRvPoster.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        mPresenter.requestPromotePoster(UserManager.getInstance().getToken(),
                getIntent().getIntExtra("product_id", -1));
    }

    @Override
    protected PromotePosterPresenter createPresenter() {
        return new PromotePosterPresenter();
    }

    @OnClick(R.id.ll_share_poster)
    public void sharePoster() {
        final GenerateProductPromotePosterActivity context =
                GenerateProductPromotePosterActivity.this;
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        UMImage image = new UMImage(context, getSharePoster());
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
        final GenerateProductPromotePosterActivity context =
                GenerateProductPromotePosterActivity.this;
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        UMWeb web = new UMWeb(posterBean.getShareUrl());
                        web.setTitle(posterBean.getName());
                        web.setDescription(" ");
                        web.setThumb(new UMImage(context, getSharePoster()));
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

    @OnClick(R.id.ll_save_poster)
    public void savePosterToLocal() {
        saveImageToGallery(this, getSharePoster());
    }

    @OnClick(R.id.ll_copy_link)
    public void copyLink() {
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(ClipData.newPlainText("", posterBean.getShareUrl()));
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
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
    public void showPromotePoster(HttpRespond<PosterBean> respond) {
        if (respond.result == 1) {
            posterBean = respond.data;
            final String[] picUrls = {respond.data.getPosterUrl1(), respond.data.getPosterUrl2(),
                    respond.data.getPosterUrl3()};
            Glide.with(GenerateProductPromotePosterActivity.this).load(picUrls[0]).into(ivPoster);
            ChoosePosterImageAdapter posterImageAdapter = new ChoosePosterImageAdapter(this,
                    picUrls);
            posterImageAdapter.setOnItemClickListener(new ChoosePosterImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Glide.with(GenerateProductPromotePosterActivity.this)
                            .load(picUrls[position]).into(ivPoster);
                }
            });
            mRvPoster.setAdapter(posterImageAdapter);
        } else {
            ToastUtils.showShort(this, respond.message);
        }

    }

    // 获取相对布局的bitmap
    private Bitmap getSharePoster() {
        Bitmap bitmap = Bitmap.createBitmap(ivPoster.getWidth(), ivPoster.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        ivPoster.draw(canvas);
        return bitmap;
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "jrr");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        ToastUtils.showShort(context, "保存成功");
    }
}
