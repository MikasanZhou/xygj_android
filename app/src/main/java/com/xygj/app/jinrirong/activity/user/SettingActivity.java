package com.xygj.app.jinrirong.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.AppUtils;
import com.xygj.app.common.utils.CacheUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.CustomDialog;
import com.xygj.app.jinrirong.activity.MainActivity;
import com.xygj.app.jinrirong.activity.user.presenter.SettingPresenter;
import com.xygj.app.jinrirong.activity.user.view.SettingView;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * 设置
 */
public class SettingActivity extends BaseMvpActivity<SettingView, SettingPresenter> implements SettingView {

    @BindView(R.id.ll_need_login)
    LinearLayout mLlNeedLogin;

    @BindView(R.id.ll_need_login2)
    LinearLayout mLlNeedLogin2;

    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;

    @BindView(R.id.tv_version)
    TextView mTvVersion;
    private Dialog dialog;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        // 未登录隐藏选项
        if (!UserManager.getInstance().isLogin()) {
            mLlNeedLogin.setVisibility(View.GONE);
            mLlNeedLogin2.setVisibility(View.GONE);
        }
        mTvVersion.setText(AppUtils.getVersionName(this));
        mTvCacheSize.setText(CacheUtils.getTotalCacheSize(this));
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    /**
     * 修改密码
     *
     * @param view
     */
    public void changePwd(View view) {
        startActivity(new Intent(this, UpdatePwdActivity.class));
    }

    /**
     * 清除缓存
     *
     * @param view
     */
    public void clearCache(View view) {
        new CustomDialog.Builder(this)
                .setTitle("清理缓存")
                .setContent("确认清除缓存？")
                .setListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        CacheUtils.clearAllCache(SettingActivity.this);
                        Toast.makeText(SettingActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                        mTvCacheSize.setText(CacheUtils.getTotalCacheSize(SettingActivity.this));
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 关于我们
     *
     * @param view
     */
    public void aboutUs(View view) {
        mPresenter.getAboutUsInfo();
    }

    /**
     * 版本介绍
     *
     * @param view
     */
    public void versionDesc(View view) {
        mPresenter.getVersionDesc();
    }

    /**
     * 登出账号
     *
     * @param view
     */
    public void logOut(View view) {
        //未登录直接 return
        if (!UserManager.getInstance().isLogin())
            return;

        new CustomDialog.Builder(this)
                .setTitle("退出登录")
                .setContent("确认退出当前账号？")
                .setListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        SettingActivity.this.dialog = dialog;
                        mPresenter.signOut(UserManager.getInstance().getToken());
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).build().show();
    }

    private void gotoMain() {
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
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
        hideLoadingDialog();
    }

    @Override
    public void onGetAboutUsInfoSucceed(HtmlData html) {
        startActivity(MyWebViewActivity.getIntent(this, html.getTitle(), html.getContents()));
    }

    @Override
    public void onGetAboutUsInfoFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetVersionDescSucceed(HttpRespond<HtmlData> respond) {
        if (respond.result == 1) {
            startActivity(MyWebViewActivity.getIntent(this, respond.data.getTitle(),
                    getHtmlData(respond.data.getContents())));
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    @Override
    public void onSignOut(HttpRespond respond) {
        dialog.dismiss();
        //ToastUtils.showShort(this, respond.message);
        //if (respond.result == 1) {
            UserManager.getInstance().clearLoginData();
            closeAllActivities();
            gotoMain();
        //}
    }

    /**
     * 让html img 适应屏幕的宽度
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
