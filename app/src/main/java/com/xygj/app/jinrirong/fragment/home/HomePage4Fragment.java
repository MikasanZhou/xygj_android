package com.xygj.app.jinrirong.fragment.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ImgUtils;
import com.xygj.app.common.widget.RoundImageView;
import com.xygj.app.jinrirong.activity.user.BuyLoanProxyActivity;
import com.xygj.app.jinrirong.activity.user.HelpActivity;
import com.xygj.app.jinrirong.activity.user.LoginActivity;
import com.xygj.app.jinrirong.activity.user.MemberInfoActivity;
import com.xygj.app.jinrirong.activity.user.MoneyActivity;
import com.xygj.app.jinrirong.activity.user.MyIncomeActivity;
import com.xygj.app.jinrirong.activity.user.MyMessageActivity;
import com.xygj.app.jinrirong.activity.user.PriceTableActivity;
import com.xygj.app.jinrirong.activity.user.QueryCreditActivity;
import com.xygj.app.jinrirong.activity.user.RongKeStoreActivity;
import com.xygj.app.jinrirong.activity.user.SettingActivity;
import com.xygj.app.jinrirong.activity.user.ShareActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.view.MinePresenter;
import com.xygj.app.jinrirong.fragment.home.view.MineView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.MemberInfoBean;
import com.xygj.app.jinrirong.model.ModuleBean;
import com.xygj.app.jinrirong.model.NewMessageBean;
import com.xygj.app.jinrirong.widget.CustomDialog2;
import com.xygj.app.jinrirong.widget.MyWaveView2;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage4Fragment extends BaseMvpFragment<MineView, MinePresenter> implements MineView {
    @BindView(R.id.view_new_msg)
    View newMsg;
    @BindView(R.id.iv_avatar)
    RoundImageView ivAvatar;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_settled)
    TextView tvSettled;
    @BindView(R.id.tv_billable)
    TextView tvBillable;
    @BindView(R.id.tv_rkd_name)
    TextView tvRkdName;
    @BindView(R.id.tv_rkd_des)
    TextView tvRkdDes;
    @BindView(R.id.tv_proxy_name)
    TextView tvProxyName;
    @BindView(R.id.tv_proxy_des)
    TextView tvProxyDes;
    @BindView(R.id.tv_recommend_name)
    TextView tvRecommendName;
    @BindView(R.id.tv_recommend_des)
    TextView tvRecommendDes;
    @BindView(R.id.tv_credit_name)
    TextView tvCreditName;
    @BindView(R.id.tv_credit_des)
    TextView tvCreditDes;
    @BindView(R.id.rl_query_credit)
    RelativeLayout rlQueryCredit;
    @BindView(R.id.rl_rkd)
    RelativeLayout rlRkd;
    @BindView(R.id.rl_proxy)
    RelativeLayout rlProxy;
    @BindView(R.id.rl_recommend)
    RelativeLayout rlRecommend;
    @BindView(R.id.ll_first_two_level)
    LinearLayout llFirstTwo;
    @BindView(R.id.ll_second_two_level)
    LinearLayout llSecondTwo;
    private MemberInfoBean memberInfo;
    @BindView(R.id.wv_wave)
    MyWaveView2 mMyWaveView2;
    public static final int CODE_REFRESH = 101;

    public HomePage4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page4, container, false);
        ButterKnife.bind(this, layout);
        initView();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!UserManager.getInstance().isLogin())
                    mRefreshLayout.setRefreshing(false);
                else
                    lazyLoadData();
            }
        });
        return layout;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @OnClick(R.id.fl_user_info)
    public void gotoLogin() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else {
            gotoMemberInfo();
        }
    }

    @OnClick({R.id.ll_wallet})
    public void gotoMoney() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else if (UserManager.getInstance().isLogin()) {
            startActivity(new Intent(getActivity(), MoneyActivity.class));
//            startActivity(new Intent(getActivity(), MyIncomeActivity.class));
        }
    }

    @OnClick({R.id.ll_wallet2})
    public void gotoMoney2() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else if (UserManager.getInstance().isLogin()) {
            startActivity(new Intent(getActivity(), MoneyActivity.class).putExtra("index", 2));
//            startActivity(new Intent(getActivity(), MyIncomeActivity.class));
        }
    }

    @OnClick(R.id.ll_total_income)
    public void gotoIncome() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else if (UserManager.getInstance().isLogin()) {
            startActivity(new Intent(getActivity(), MyIncomeActivity.class));
        }
    }

    private void jumpToLogin() {
        startActivityForResult(new Intent(getContext(), LoginActivity.class), CODE_REFRESH);
    }

    @OnClick({R.id.rl_rkd, R.id.rl_proxy, R.id.rl_recommend, R.id.rl_query_credit})
    public void onRlClick(View view) {
        if (!UserManager.getInstance().isLogin())
            jumpToLogin();
        else {
            switch (view.getId()) {
                case R.id.rl_rkd:
                    startActivity(new Intent(getActivity(), RongKeStoreActivity.class));
                    break;
                case R.id.rl_proxy:
                    startActivity(new Intent(getActivity(), BuyLoanProxyActivity.class));
                    break;
                case R.id.rl_recommend:
                    startActivity(new Intent(getContext(), ShareActivity.class));
                    break;
                case R.id.rl_query_credit:
                    startActivity(new Intent(getActivity(), QueryCreditActivity.class));
                    break;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            getMemberInfo();
        }
    }

    private void getMemberInfo() {
        if (UserManager.getInstance().isLogin()) {
            getPresenter().requestMemberInfo(UserManager.getInstance().getToken());
        }
    }

    /**
     * 基本信息
     */
    @OnClick(R.id.ll_member_info)
    public void gotoMemberInfo() {
        if (!UserManager.getInstance().isLogin())
            jumpToLogin();
        else
            startActivity(new Intent(getActivity(), MemberInfoActivity.class));
    }


    @Override
    public void onResume() {
        super.onResume();
        getMemberInfo();
        getPresenter().getMessageFlag(UserManager.getInstance().getToken());
    }

    @Override
    public void onNewMessage(HttpRespond<NewMessageBean> respond) {
        if (respond.result == 1) {
            if (respond.data.isNewNoticeMsg() || respond.data.isNewSystemMsg()) {
                newMsg.setVisibility(View.VISIBLE);
            } else {
                newMsg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showModule(HttpRespond<List<ModuleBean>> listHttpRespond) {
        if (listHttpRespond.result == 1 && listHttpRespond.data.size() == 4) {
            llFirstTwo.setVisibility(View.VISIBLE);
            llSecondTwo.setVisibility(View.VISIBLE);
            ModuleBean bean1 = listHttpRespond.data.get(0);
            loadBgWithGlide(bean1.getBgUrl(), rlRkd);
            tvRkdName.setText(bean1.getName());
            tvRkdDes.setText(bean1.getDes());
            ModuleBean bean2 = listHttpRespond.data.get(1);
            loadBgWithGlide(bean2.getBgUrl(), rlProxy);
            tvProxyName.setText(bean2.getName());
            tvProxyDes.setText(bean2.getDes());
            ModuleBean bean3 = listHttpRespond.data.get(2);
            loadBgWithGlide(bean3.getBgUrl(), rlRecommend);
            tvRecommendName.setText(bean3.getName());
            tvRecommendDes.setText(bean3.getDes());
            ModuleBean bean4 = listHttpRespond.data.get(3);
            loadBgWithGlide(bean4.getBgUrl(), rlQueryCredit);
            tvCreditName.setText(bean4.getName());
            tvCreditDes.setText(bean4.getDes());
        }
    }

    private void loadBgWithGlide(String url, final RelativeLayout rl) {
        Glide.with(getContext()).load(url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                rl.setBackground(resource);
            }
        });
    }

    @OnClick(R.id.ll_price_table)
    public void PriceTable() {
        startActivity(new Intent(getContext(), PriceTableActivity.class));
    }

    @OnClick(R.id.iv_msg)
    public void gotoMsg() {
        if (!UserManager.getInstance().isLogin())
            jumpToLogin();
        else
            startActivity(new Intent(getContext(), MyMessageActivity.class));
    }

    @OnClick(R.id.ll_help)
    public void gotoHelp() {
        startActivity(new Intent(getContext(), HelpActivity.class));
    }

    @OnClick(R.id.ll_wechat_kefu)
    public void gotoWechatKeFu() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else
            new CustomDialog2.Builder(getActivity())
                    .setContent("微信号:\n " + memberInfo.wechatKefu)
                    .setConfirmText("复制")
                    .setListener(new CustomDialog2.OnButtonClickListener() {
                        @Override
                        public void onConfirm(Dialog dialog) {
                            try{
                                dialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setComponent(cmp);
                                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData mClipData = ClipData.newPlainText("Label", memberInfo.wechatKefu);
                                cm.setPrimaryClip(mClipData);
                                startActivity(intent);
                            }catch (ActivityNotFoundException e){
                            //getContext().getBaseActivity().showToastLong("检查到您手机没有安装微信，请安装后使用该功能");
                            }

                        }

                        @Override
                        public void onCancel(Dialog dialog) {
                            dialog.dismiss();
                        }
                    }).build().show();
    }
    @OnClick(R.id.ll_wechat_qr)
    @TargetApi(Build.VERSION_CODES.M)
    public void gotoWechatQR() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else
            new CustomDialog2.Builder(getActivity())
                    .setQrPath(memberInfo.wechatQR)
                    .setConfirmText("保存图片")
                    .setListener(new CustomDialog2.OnButtonClickListener() {
                        @Override
                        public void onConfirm(Dialog dialog) {
                            try{
                                FragmentActivity activity = getActivity();

                                // 存储读写权限
                                if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    activity.requestPermissions(new String[]{
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    }, 1);
                                }
                                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    activity.requestPermissions(new String[]{
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                    }, 1);
                                }

                                URL url = new URL(memberInfo.wechatQR);
                                //打开输入流
                                InputStream inputStream = url.openStream();
                                //对网上资源进行下载转换位图图片
                                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/DCIM/IMMQY/";
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                ImgUtils.saveImageToGallery(activity.getApplicationContext(), path, bitmap);
                            }catch (ActivityNotFoundException e){
                                //getContext().getBaseActivity().showToastLong("检查到您手机没有安装微信，请安装后使用该功能");
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                Toast.makeText(getActivity(), "图片已保存", Toast.LENGTH_SHORT).show();
                                //销毁弹层
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onCancel(Dialog dialog) {
                            dialog.dismiss();
                        }
                    }).build().show();
    }
    @OnClick(R.id.ll_contact_us)
    public void gotoContactUs() {
        if (!UserManager.getInstance().isLogin()) {
            jumpToLogin();
        } else
            new CustomDialog2.Builder(getActivity())
                    .setContent("联系我们\n " + memberInfo.severTel)
                    .setConfirmText("拨打")
                    .setListener(new CustomDialog2.OnButtonClickListener() {
                        @Override
                        public void onConfirm(Dialog dialog) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + memberInfo.severTel));
                            startActivity(intent);
                        }

                        @Override
                        public void onCancel(Dialog dialog) {
                            dialog.dismiss();
                        }
                    }).build().show();
    }

    @OnClick(R.id.ll_setting)
    public void gotoSetting() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @Override
    protected void lazyLoadData() {
        if (UserManager.getInstance().isLogin()) {
            tvNickname.setText("获取中");
            tvPhone.setText("获取中");
            getPresenter().requestMemberInfo(UserManager.getInstance().getToken());
            getPresenter().requestModuleInfo(UserManager.getInstance().getToken());
            getPresenter().getMessageFlag(UserManager.getInstance().getToken());
        } else {
            llFirstTwo.setVisibility(View.GONE);
            llSecondTwo.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onTokenInvalidate() {
        llFirstTwo.setVisibility(View.GONE);
        llSecondTwo.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
        UserManager.getInstance().clearLoginData();
        tvNickname.setText("点击登录");
        tvPhone.setText("您好，欢迎使用");
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetworkConnectFailed() {

    }

    @Override
    public void showMineData(HttpRespond<String> respond) {
        mRefreshLayout.setRefreshing(false);
        if (respond.result == 1) {
            memberInfo = new Gson().fromJson(SafeUtils
                    .decrypt(getContext(), respond.data), MemberInfoBean.class);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
            Glide.with(this).load(memberInfo.headImgUrl).apply(options).into(ivAvatar);

            String daiName = "";
            // 1普通会员  2渠道代理  3团队经理  4城市经理
            switch (memberInfo.type) {
                case 1:
                    daiName = "(普通会员)";
                    break;
                case 2:
                    daiName = "(渠道代理)";
                    break;
                case 3:
                    daiName = "(团队经理)";
                    break;
                case 4:
                    daiName = "(城市经理)";
                    break;
            }
            tvNickname.setText(memberInfo.trueName + "(" + memberInfo.rule + ")");
            UserManager.getInstance().saveRoleName(memberInfo.rule);

            tvPhone.setText(memberInfo.mobile);
//            tvPhone.setText(String.format(Locale.CHINA, "%s(工号:%d)",
//                    memberInfo.mobile, memberInfo.id));
            tvTotal.setText(memberInfo.income);
            tvSettled.setText(memberInfo.account);
            tvBillable.setText(memberInfo.balance);
            UserManager.getInstance().saveMemberType(memberInfo.type);
        } else if (respond.result == -1) {
            Toast.makeText(getActivity(), "登录已失效，请重新登录", Toast.LENGTH_SHORT).show();
            UserManager.getInstance().clearLoginData();
            tvNickname.setText("点击登录");
            tvPhone.setText("您好，欢迎使用");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REFRESH) {
            if (resultCode == RESULT_OK)
                lazyLoadData();
        }
    }

    @Override
    protected void onVisibilityChanged(boolean isVisibleToUser) {
        if (mMyWaveView2 != null) {
            if (isVisibleToUser) {
                mMyWaveView2.startAnim();
            } else {
                mMyWaveView2.stopAnim();
            }
        }
    }
}
