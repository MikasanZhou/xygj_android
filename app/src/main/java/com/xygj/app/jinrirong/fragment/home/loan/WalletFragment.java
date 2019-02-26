package com.xygj.app.jinrirong.fragment.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.product.WithdrawActivity;
import com.xygj.app.jinrirong.activity.user.MyIncomeActivity;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.loan.presenter.WalletPresenter;
import com.xygj.app.jinrirong.fragment.home.loan.view.WalletView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.MoneyInfoList;
import com.xygj.app.jinrirong.model.WithdrawBean;

/**
 * 我的钱包
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends BaseMvpFragment<WalletView, WalletPresenter> implements WalletView {
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_withdraw_money)
    TextView tvWithdrawMoney;
    @BindView(R.id.rv_cash_record)
    RecyclerView mRecyclerView;
    private List<WithdrawBean> beans;
    private RecordAdapter adapter;
    private String cost;

    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
        return layout;
    }

    @OnClick({R.id.btn_my_income,R.id.tv_browse})
    public void gotoMyIncomePage() {
        startActivity(new Intent(getActivity(), MyIncomeActivity.class));
    }

    //跳转提现页面
    @OnClick(R.id.tv_withdraw)
    public void gotoWithdrawPage() {
        Intent intent = new Intent(getActivity(), WithdrawActivity.class);
        intent.putExtra("withdraw_money", tvWithdrawMoney.getText().toString());
        intent.putExtra("withdraw_cost", cost);
        startActivity(intent);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected WalletPresenter createPresenter() {
        return new WalletPresenter();
    }

    @Override
    protected void lazyLoadData() {
        if (UserManager.getInstance().isLogin()) {
            beans = new ArrayList<>();
            adapter = new RecordAdapter(beans);
            mRecyclerView.setAdapter(adapter);
            getDataFromServer();
        } else {
            ToastUtils.showShort(getContext(), "请先登录");
        }
    }

    private void getDataFromServer() {
        getPresenter().requestMoneyData(UserManager.getInstance().getToken(), 0);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onTokenInvalidate() {

    }

    @Override
    public void onNetworkConnectFailed() {

    }

    @Override
    public void showMoneyData(HttpRespond<String> respond) {
        if (respond.result == 1) {
            MoneyInfoList moneyInfo = new Gson().fromJson(SafeUtils.decrypt(getContext(),
                    respond.data), MoneyInfoList.class);
            tvTotalMoney.setText(getString(R.string.price_value, moneyInfo.totalIncome));
            tvWithdrawMoney.setText(getString(R.string.price_value, moneyInfo.balance));
            cost = moneyInfo.cost + "%";
            beans.clear();
            beans.addAll(moneyInfo.list);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.Holder> {
        private List<WithdrawBean> list;

        RecordAdapter(List<WithdrawBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_cash_record, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            WithdrawBean bean = list.get(position);
            holder.tvWithdrawMoney.setText(getString(R.string.withdraw_mark, bean.money));
            holder.tvCurrentBalance.setText(bean.currentMoney);
            holder.tvTime.setText(bean.time);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_withdraw_money)
            TextView tvWithdrawMoney;
            @BindView(R.id.tv_current_balance)
            TextView tvCurrentBalance;
            @BindView(R.id.tv_time)
            TextView tvTime;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
