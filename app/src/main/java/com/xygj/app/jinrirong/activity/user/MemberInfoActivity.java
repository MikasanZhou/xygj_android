package com.xygj.app.jinrirong.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.yalantis.ucrop.util.FileUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.BitmapHelper;
import com.xygj.app.common.utils.LogUtil;
import com.xygj.app.common.utils.RxGalleryMyApi;
import com.xygj.app.common.utils.SimpleRxGalleryFinal;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.RoundImageView;
import com.xygj.app.jinrirong.activity.user.presenter.MemberInfoPresenter;
import com.xygj.app.jinrirong.activity.user.view.MemberInfoView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.dialog.ChooseDialog;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.MemberInfoBean;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.Logger;

public class MemberInfoActivity extends BaseMvpActivity<MemberInfoView, MemberInfoPresenter> implements MemberInfoView {
    @BindView(R.id.fl_identity)
    FrameLayout flIdentity;
    @BindView(R.id.fl_has_credit_card)
    FrameLayout flHasCreditCard;
    @BindView(R.id.fl_phone_use_time)
    FrameLayout flPhoneUseTime;
    @BindView(R.id.iv_head_img)
    RoundImageView ivHeadImg;
    @BindView(R.id.et_true_name)
    EditText etTrueName;
    @BindView(R.id.et_id_card_num)
    EditText etIdCardNum;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_recommend_code)
    TextView tvRecommendCode;
    @BindView(R.id.tv_recommend_name)
    TextView tvRecommendName;
    @BindView(R.id.tv_identity)
    TextView tvIdentity;
    @BindView(R.id.tv_has_credit_card)
    TextView tvHasCreditCard;
    @BindView(R.id.tv_phone_use_time)
    TextView tvPhoneUseTime;
    @BindView(R.id.btn_save)
    Button btnSave;
    private final String[] identityText = {"学生族", "上班族", "自主创业", "无业"};// 1、2、3、4
    private final String[] hasCreditCardText = {"无", "有"};// 0、1 默认0
    private final String[] phoneUseTimeText = {"一年以下", "一年以上", "两年以上", "三年以上"};
    private int currentIdentityId, hasCreditCard, currentPhoneUseTimeId;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_member_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.requestMemberInfo(UserManager.getInstance().getToken());
            }
        }, 300);
    }

    @Override
    protected MemberInfoPresenter createPresenter() {
        return new MemberInfoPresenter();
    }

    /**
     * 更改头像
     */
    @OnClick(R.id.fl_change_avatar)
    public void changeAvatar() {
        ChooseDialog chooseDialog = ChooseDialog.newInstance("更换头像", "程序相机拍照",
                "相册",
                new ChooseDialog.OnChoiceClickListener() {
                    @Override
                    public void onChooseFirst() {
                        // 拍照
                        AndPermission.with(MemberInfoActivity.this)
                                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                                .onGranted(new Action() {
                                    @Override
                                    public void onAction(List<String> permissions) {
                                        takePhoto();
                                    }
                                }).onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                ToastUtils.showShort(MemberInfoActivity.this, "权限拒绝");
                            }
                        }).start();
                    }

                    @Override
                    public void onChooseSecond() {
                        // 相册
                        chooseFromAlbum();
                    }
                });
        chooseDialog.show(getSupportFragmentManager(), "choose_dia");
    }

    @OnClick({R.id.fl_identity, R.id.fl_has_credit_card, R.id.fl_phone_use_time})
    public void showPickView(View view) {
        switch (view.getId()) {
            case R.id.fl_identity:
                getSinglePicker("目前身份", identityText,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                tvIdentity.setText(identityText[options1]);
                                currentIdentityId = options1 + 1;
                            }
                        }).show();
                break;
            case R.id.fl_has_credit_card:
                getSinglePicker("有无信用卡", hasCreditCardText,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                tvHasCreditCard.setText(hasCreditCardText[options1]);
                                hasCreditCard = options1;
                            }
                        }).show();
                break;
            case R.id.fl_phone_use_time:
                getSinglePicker("手机使用时长", phoneUseTimeText,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                tvPhoneUseTime.setText(phoneUseTimeText[options1]);
                                currentPhoneUseTimeId = options1 + 1;
                            }
                        }).show();
                break;
            default:
        }
    }

    public OptionsPickerView getSinglePicker(
            String title, String[] pickerText, OnOptionsSelectListener listener) {
        final List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(pickerText));
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, listener)
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText(title).build();
        pvOptions.setPicker(list, null, null);
        return pvOptions;
    }

    @OnClick(R.id.btn_save)
    public void onSaveClick() {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("TrueName", etTrueName.getText().toString());
            requestData.put("HeadImg", mPresenter.getHeadImgUrl());
            requestData.put("CardNo", etIdCardNum.getText().toString());
            requestData.put("Position", currentIdentityId);
            requestData.put("IsCredit", hasCreditCard);
            requestData.put("UseTime", currentPhoneUseTimeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.submitUserInfo(UserManager.getInstance().getToken(),
                SafeUtils.encrypt(this, requestData.toString()));
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
    public void onGetMemberInfo(HttpRespond<String> respond) {
        if (respond.result == 1) {
            String data = SafeUtils
                    .decrypt(this, respond.data);
            Log.e("eeeeeeeee", data);
            MemberInfoBean bean = new Gson().fromJson(data, MemberInfoBean.class);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
            try {
                String submitImgUrl = bean.headImgUrl.contains("Public/images") ? "" :
                        bean.headImgUrl.substring(bean.headImgUrl.indexOf("/Upload"));
                mPresenter.setHeadImgUrl(submitImgUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
            Glide.with(this).load(bean.headImgUrl).apply(options).into(ivHeadImg);
            etTrueName.setText(bean.trueName);
            etIdCardNum.setText(!TextUtils.isEmpty(bean.cardNo) ? bean.cardNo : "");
            tvPhoneNum.setText(bean.mobile);
            tvRecommendCode.setText(bean.recommendCode);
            tvRecommendName.setText(bean.referee);
            tvIdentity.setText(!TextUtils.isEmpty(bean.position) ? bean.position : "未设置");
            tvHasCreditCard.setText(bean.isCredit);
            tvPhoneUseTime.setText(bean.useTime);
            currentIdentityId = getCurrentInfoId(bean.position, identityText) + 1;
            hasCreditCard = getCurrentInfoId(bean.isCredit, hasCreditCardText);
            currentPhoneUseTimeId = getCurrentInfoId(bean.useTime, phoneUseTimeText) + 1;
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    private int getCurrentInfoId(String s, String[] strings) {
        int index = 0;
        for (String str : strings) {
            if (s.equals(str)) {
                return ++index;
            }
        }
        return index;
    }

    @Override
    public void onSubmitMemberInfoDone(HttpRespond respond) {
        ToastUtils.showShort(this, respond.message);
    }

    private void chooseFromAlbum() {
        RxGalleryMyApi instance =
                RxGalleryMyApi.getInstance(this);
        instance.openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                Logger.i("只要选择图片就会触发");
            }
        }).onCropImageResult(
                new IRadioImageCheckedListener() {
                    @Override
                    public void cropAfter(Object t) {
                        File file = (File) t;
                        RequestOptions options = new RequestOptions();
                        options.centerCrop();
                        Glide.with(MemberInfoActivity.this).load(file)
                                .apply(options).into(ivHeadImg);
                        compressAndUploadHeadImg(file.getAbsolutePath());
                        Logger.i("裁剪完成");
                    }

                    @Override
                    public boolean isActivityFinish() {
                        Logger.i("返回false不关闭，返回true则为关闭");
                        return true;
                    }
                });
    }

    private void takePhoto() {
        SimpleRxGalleryFinal.get().init(
                new SimpleRxGalleryFinal.RxGalleryFinalCropListener() {
                    @NonNull
                    @Override
                    public Activity getSimpleActivity() {
                        return MemberInfoActivity.this;
                    }

                    @Override
                    public void onCropCancel() {
                        ToastUtils.showShort(getSimpleActivity(), "操作被取消");
                    }

                    @Override
                    public void onCropSuccess(@Nullable Uri uri) {
                        if (uri != null) {
                            String path = FileUtils.getPath(getSimpleActivity(), uri);
                            compressAndUploadHeadImg(path);
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            Glide.with(MemberInfoActivity.this).load(path)
                                    .apply(options).into(ivHeadImg);
                        } else {
                            ToastUtils.showShort(getSimpleActivity(), "图片不存在");
                        }
                    }

                    @Override
                    public void onCropError(@NonNull String errorMessage) {
                        ToastUtils.showShort(getSimpleActivity(), errorMessage);
                    }
                }
        ).openCamera();
    }

    private void compressAndUploadHeadImg(String path) {
        File file = new File(path);
        // 压缩头像并保存
        Bitmap bitmap = BitmapHelper
                .compressImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
        LogUtil.e(file.getParent());
        File PHOTO_DIR = new File(file.getParent());
        File headFile = new File(PHOTO_DIR, "head.jpg");//设置文件名称
        if (headFile.exists()) {
            headFile.delete();
        }
        try {
            headFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(headFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 上传用户头像
        LogUtil.e(headFile.getAbsolutePath());
        mPresenter.uploadImage(headFile.getAbsolutePath(), UserManager.getInstance().getToken());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
    }
}
