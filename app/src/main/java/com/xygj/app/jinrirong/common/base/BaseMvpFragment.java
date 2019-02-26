package com.xygj.app.jinrirong.common.base;

import android.app.Dialog;
import android.app.ProgressDialog;

import com.xygj.app.common.base.BaseLazyLoadFragment;
import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.common.base.BaseView;

/**
 * Created by xuyougen on 2018/4/17.
 */

public abstract class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseLazyLoadFragment {
    private Dialog mLoadingDialog;
    private P mPresenter;

    @Override
    protected void initView() {
        if (mLoadingDialog == null)
            mLoadingDialog = createProgressDialog();
        mPresenter = createPresenter();
        mPresenter.attach((V) this);
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return mPresenter;
    }

    protected Dialog createProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("请稍等");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        return dialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public void showLoadingDialog() {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
