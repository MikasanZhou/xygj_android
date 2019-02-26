package com.xygj.app.jinrirong.fragment.home.income.promote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.fragment.home.income.presenter.PromoteIncomePresenter;
import com.xygj.app.jinrirong.fragment.home.income.view.PromoteIncomeView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.IncomeBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoteIncomeDetailFragment extends BaseMvpFragment<PromoteIncomeView, PromoteIncomePresenter> implements PromoteIncomeView {
    private static final String ARG_TYPE = "param";
    private static final String TYPE_INCOME = "income_type";

    @BindView(R.id.rv_record)
    RecyclerView mRecyclerView;
    private int type;
    private List<IncomeBean> list;
    private MAdapter adapter;


    public PromoteIncomeDetailFragment() {
    }

    public static PromoteIncomeDetailFragment newInstance(String param1) {
        PromoteIncomeDetailFragment fragment = new PromoteIncomeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static PromoteIncomeDetailFragment newInstance(int typeValue) {
        PromoteIncomeDetailFragment fragment = new PromoteIncomeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_INCOME, typeValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE_INCOME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_promote_income_detail, container, false);
        ButterKnife.bind(this, layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new MAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected PromoteIncomePresenter createPresenter() {
        return new PromoteIncomePresenter();
    }

    @Override
    protected void lazyLoadData() {
        getPresenter().requestPromoteIncomeList(UserManager.getInstance().getToken(), type, 0);
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
    public void showPromoteList(HttpRespond<String> respond) {
        if (respond.result == 1) {
            List<IncomeBean> beanList = new Gson().fromJson(SafeUtils.decrypt(getContext(),
                    respond.data), new TypeToken<List<IncomeBean>>() {
            }.getType());
            list.clear();
            list.addAll(beanList);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> {
        private List<IncomeBean> beans;

        MAdapter(List<IncomeBean> beans) {
            this.beans = beans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_promote_income, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            IncomeBean bean = beans.get(position);
            holder.tvName.setText(bean.getName());
            holder.tvIncome.setText(getString(R.string.plus_mark, bean.getIncome()));
            holder.tvAccountNo.setText(getString(R.string.account_no_text, bean.getAccountNo()));
            holder.tvTime.setText(bean.getTime());
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_income)
            TextView tvIncome;
            @BindView(R.id.tv_account_no)
            TextView tvAccountNo;
            @BindView(R.id.tv_time)
            TextView tvTime;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
