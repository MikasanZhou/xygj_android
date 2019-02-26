package com.xygj.app.jinrirong.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.transform.GlideRoundTransform;
import com.xygj.app.jinrirong.common.MyWebViewActivity;

/**
 * 首页图片广告对话框
 * Created by Yangli on 2018/5/3.
 */

public class PicDialog extends DialogFragment {
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private String clickUrl;

    public static PicDialog newInstance(String picUrl, String clickUrl) {
        Bundle args = new Bundle();
        args.putString("pic_url", picUrl);
        args.putString("click_url", clickUrl);
        PicDialog fragment = new PicDialog();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_pic, container, false);
        ButterKnife.bind(this, view);
        initData(getArguments());
        return view;
    }

    private void initData(Bundle args) {
        String picUrl = args.getString("pic_url");
        clickUrl = args.getString("click_url");
        RequestOptions options = new RequestOptions();
        options.transform(new GlideRoundTransform(getContext(), 5));
        Glide.with(getContext()).load(picUrl).apply(options).into(ivPic);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @OnClick(R.id.iv_close)
    public void onCloseClick() {
        dismiss();
    }

    @OnClick(R.id.iv_pic)
    public void iv_pic() {
        startActivity(MyWebViewActivity.getIntent(getContext(), "", clickUrl));
        dismiss();
    }

    @OnClick(R.id.btn_view)
    public void onViewClick() {
        startActivity(MyWebViewActivity.getIntent(getContext(), "", clickUrl));
        dismiss();
    }
}
