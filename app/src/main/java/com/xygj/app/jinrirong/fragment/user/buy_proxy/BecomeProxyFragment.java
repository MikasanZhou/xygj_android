package com.xygj.app.jinrirong.fragment.user.buy_proxy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.PayModeActivity;
import com.xygj.app.jinrirong.adpter.ChooseProxyLevelAdapter;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.user.presenter.BecomeProxyPresenter;
import com.xygj.app.jinrirong.fragment.user.view.BecomeProxyView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProxyBean;

/**
 * 成为代理
 * A simple {@link Fragment} subclass.
 */
public class BecomeProxyFragment extends BaseMvpFragment<BecomeProxyView, BecomeProxyPresenter> implements BecomeProxyView {
    @BindView(R.id.tv_member_type)
    TextView tvMemberType;
    @BindView(R.id.tv_buy_info)
    TextView tvBuyInfo;
    @BindView(R.id.ll_buy_proxy)
    LinearLayout llBuyProxy;
    @BindView(R.id.rv_proxy_level)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_price_text)
    TextView tvPriceText;
    @BindView(R.id.tv_proxy_price)
    TextView tvProxyPrice;
    @BindView(R.id.sw_protocol)
    Switch swProtocol;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    private List<String> proxyNames;
    private ChooseProxyLevelAdapter chooseProxyLevelAdapter;
    private List<ProxyBean> proxyBeans;
    private int chosenLevel;
    private boolean isFirstLoad = true;
    private int type;

    public BecomeProxyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_become_proxy, container, false);
        ButterKnife.bind(this, layout);
        proxyNames = new ArrayList<>();
        chooseProxyLevelAdapter = new ChooseProxyLevelAdapter(
                proxyNames,
                getActivity(),
                ContextCompat.getColor(getActivity(), R.color.colorLightGray),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary)
        );
        mRecyclerView.setAdapter(chooseProxyLevelAdapter);
        chooseProxyLevelAdapter.setOnItemClickListener(new ChooseProxyLevelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                chosenLevel = proxyBeans.get(position).id;
                tvProxyPrice.setText(getString(R.string.price_value, proxyBeans.get(position).price));
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        return layout;
    }

    @OnClick(R.id.btn_buy)
    public void onBuyClick() {
        if (swProtocol.isChecked()) {
            PayModeActivity.jumpToPay(getActivity(), PayModeActivity.TYPE_DL, "订单金额",
                    tvProxyPrice.getText().toString(), String.valueOf(chosenLevel));
        } else {
            ToastUtils.showShort(getContext(), "须得同意协议");
        }
    }

    @OnClick(R.id.tv_user_protocol)
    public void onUserProtocolClick() {
        getPresenter().getUserProtocol();
    }

    @Override
    protected BecomeProxyPresenter createPresenter() {
        return new BecomeProxyPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoadData() {
        type = UserManager.getInstance().getMemberType();
        if (type == 1) tvMemberType.setVisibility(View.GONE);
//        tvMemberType.setText(String.format("您好，%s",
//                type == 2 ? "渠道代理" : ((type == 3 ? "团队经理" : "城市经理"))));
        tvMemberType.setText(String.format("您好，%s", UserManager.getInstance().getRoleName()));
        switch (type) {
            case 1: // 普通会员
                break;
            case 2: // 渠道代理
                tvPriceText.setText("差价");
                btnBuy.setText("立即升级");
                break;
            case 3: // 团队经理
                tvPriceText.setText("差价");
                btnBuy.setText("立即升级");
                break;
            case 4: // 城市经理
//                tvBuyInfo.setVisibility(View.GONE);
                llBuyProxy.setVisibility(View.GONE);
                tvBuyInfo.setText("你已经是最高级别的会员了");
                break;
        }
        showLoadingDialog();
        getPresenter().requestProxyNames(UserManager.getInstance().getToken());
        isFirstLoad = false;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!isFirstLoad)
//            showLoadingDialog();
//        lazyLoadData();
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
    public void showProxyGrade(HttpRespond<List<ProxyBean>> respond) {
        if (respond.result == 1) {
            proxyBeans = respond.data;
            // 团队和渠道的差价
            BigDecimal sub1 = BigDecimal.valueOf(Double.valueOf(proxyBeans.get(1).price)).subtract(BigDecimal.valueOf(Double.valueOf(proxyBeans.get(2).price)));
            // 城市和团队的差价
            BigDecimal sub2 = BigDecimal.valueOf(Double.valueOf(proxyBeans.get(0).price)).subtract(BigDecimal.valueOf(Double.valueOf(proxyBeans.get(1).price)));
            // 城市和渠道的差价
            BigDecimal sub3 = BigDecimal.valueOf(Double.valueOf(proxyBeans.get(0).price)).subtract(BigDecimal.valueOf(Double.valueOf(proxyBeans.get(2).price)));
            if (type == 2) {
                proxyBeans.remove(2);
                proxyBeans.get(1).price = String.valueOf(sub1);
                proxyBeans.get(0).price = String.valueOf(sub3);
            } else if (type == 3) {
                proxyBeans.remove(2);
                proxyBeans.remove(1);
                proxyBeans.get(0).price = String.valueOf(sub2);
            }
            proxyNames.clear();
            for (ProxyBean bean : proxyBeans) {
                proxyNames.add(bean.name);
            }
            chosenLevel = proxyBeans.get(0).id;
            chooseProxyLevelAdapter.notifyDataSetChanged();
            tvProxyPrice.setText(getString(R.string.price_value, proxyBeans.get(0).price));
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    @Override
    public void jumpToUserProtocolPage(HttpRespond<HtmlData> respond) {
        startActivity(MyWebViewActivity.getIntent(getContext(),
                respond.data.getTitle(), respond.data.getContents()));
    }
}



