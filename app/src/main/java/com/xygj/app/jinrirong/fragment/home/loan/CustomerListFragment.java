package com.xygj.app.jinrirong.fragment.home.loan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.fragment.home.loan.presenter.CustomerListPresenter;
import com.xygj.app.jinrirong.fragment.home.loan.view.CustomerListView;
import com.xygj.app.jinrirong.model.ClientBean;
import com.xygj.app.jinrirong.model.ClientListBean;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * 客户列表
 * A simple {@link Fragment} subclass.
 */

public class CustomerListFragment extends BaseMvpFragment<CustomerListView, CustomerListPresenter> implements CustomerListView, OnLoadmoreListener {
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_apply_total)
    TextView mTvApplyTotal;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_customer)
    RecyclerView mRecyclerView;
    private List<ClientBean> mClientList;
    private CustomerAdapter mCustomerAdapter;
    private int mCurrentYear;
    private int mPage;

    public CustomerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_customer_list, container, false);
        ButterKnife.bind(this, layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefreshLayout.setOnLoadmoreListener(this);
        mRefreshLayout.setEnableRefresh(false);
        return layout;
    }

    @BindView(R.id.tv_choose_year)
    TextView mTvChooseYear;

    /**
     * 选择年份
     */
    @OnClick(R.id.tv_choose_year)
    public void chooseYear() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), mTvChooseYear);
        for (int i = 2000; i < mCurrentYear + 1; i++) {
            popupMenu.getMenu().add(String.valueOf(i));
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mTvChooseYear.setText(item.getTitle());
                mTvDate.setText(String.format("%s年%s月", mTvChooseYear.getText(), mTvChooseMonth.getText()));
                return true;
            }
        });
    }

    @BindView(R.id.tv_choose_month)
    TextView mTvChooseMonth;

    /**
     * 选择月份
     */
    @OnClick(R.id.tv_choose_month)
    public void chooseMonth() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), mTvChooseMonth);
        for (int i = 1; i <= 12; i++) {
            popupMenu.getMenu().add(i < 10 ? "0" + i : String.valueOf(i));
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mTvChooseMonth.setText(item.getTitle());
                mTvDate.setText(String.format("%s年%s月", mTvChooseYear.getText(), mTvChooseMonth.getText()));
                return true;
            }
        });
    }

    /**
     * 查询数据
     */
    @OnClick(R.id.tv_query)
    public void queryData() {
        showLoadingView();
        mPage = 0;
        getPresenter().getClientList(mTvChooseYear.getText().toString() + "-"
                + mTvChooseMonth.getText().toString(), 0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected CustomerListPresenter createPresenter() {
        return new CustomerListPresenter();
    }

    @Override
    protected void lazyLoadData() {
        Calendar calendar = Calendar.getInstance();
        mCurrentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;
        mTvChooseYear.setText(String.valueOf(mCurrentYear));
        String month = currentMonth < 10 ? "0" + currentMonth : String.valueOf(currentMonth);
        mTvChooseMonth.setText(month);
        mTvDate.setText(mCurrentYear + "年" + month + "月");
        mClientList = new ArrayList<>();
        mCustomerAdapter = new CustomerAdapter(mClientList);
        mRecyclerView.setAdapter(mCustomerAdapter);
        mPage = 0;
        getPresenter().getClientList("", 0);
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
    public void showClientList(HttpRespond<ClientListBean> respond) {
        hideLoadingView();
        resetRefreshLayout();
        if (respond.result == 1) {
            mTvApplyTotal.setText(respond.data.applyTotal);
            if (mPage == 0)
                mClientList.clear();
            mClientList.addAll(respond.data.list);
            mCustomerAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    @Override
    public void onRequestFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        resetRefreshLayout();
    }

    private void resetRefreshLayout() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getPresenter().getClientList(mTvChooseYear.getText().toString() + "-"
                + mTvChooseMonth.getText().toString(), ++mPage);
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.Holder> {
        private List<ClientBean> list;

        CustomerAdapter(List<ClientBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = getLayoutInflater().inflate(R.layout.item_customer, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            ClientBean bean = list.get(position);
            holder.tvName.setText(bean.name);
            holder.tvApplyNum.setText(bean.applyNum);
            holder.tvSuccessNum.setText(bean.successNum);
            holder.tvBonus.setText(getString(R.string.price_value, bean.bonus));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_apply_num)
            TextView tvApplyNum;
            @BindView(R.id.tv_success_num)
            TextView tvSuccessNum;
            @BindView(R.id.tv_bonus)
            TextView tvBonus;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
