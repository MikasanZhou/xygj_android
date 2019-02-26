package com.xygj.app.common.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * MVP Activity 基类
 * Created by xuyougen on 2018/4/11.
 */

public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {

    protected P mPresenter;
    //加载时需要的dialog
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLoadingDialog == null)
            mLoadingDialog = createProgressDialog();
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.attach((V) this);
        }
    }

    protected Dialog createProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("请稍等");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        return dialog;
    }

    @Override
    protected void initData() {
        mPresenter = createPresenter();
        mPresenter.attach((V) this);
        mLoadingDialog = createProgressDialog();
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
                }
            }
        });
    }

    public void hideLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        });

    }
}
