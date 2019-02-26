package com.xygj.app.jinrirong.fragment.home.income.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.xygj.app.jinrirong.fragment.home.income.presenter.UserIncomePresenter;
import com.xygj.app.jinrirong.fragment.home.income.view.UserIncomeView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.IncomeBean;

public class UserIncomeDetailFragment extends BaseMvpFragment<UserIncomeView, UserIncomePresenter> implements UserIncomeView {

    private static final String ARG_PARAM1 = "param1";
    private List<IncomeBean> list;
    private MAdapter adapter;
    //private String mParam1;

    public UserIncomeDetailFragment() {
    }

    @BindView(R.id.rv_record)
    RecyclerView mRecyclerView;


    public static UserIncomeDetailFragment newInstance(String param1) {
        UserIncomeDetailFragment fragment = new UserIncomeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_user_income_detail, container, false);
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
    protected UserIncomePresenter createPresenter() {
        return new UserIncomePresenter();
    }

    @Override
    protected void lazyLoadData() {
        getPresenter().requestUserIncomeList(UserManager.getInstance().getToken(), 0);
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
    public void showUserIncomeList(HttpRespond<String> respond) {
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
        public MAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_user_income, parent, false);
            return new MAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MAdapter.ViewHolder holder, int position) {
            IncomeBean bean = beans.get(position);
            holder.tvTime.setText(bean.getTime());
            holder.commission.setText(getString(R.string.plus_mark, bean.getIncome()));
            holder.tvAccountNo.setText(getString(R.string.name_text, bean.getAccountNo()));
            Glide.with(getContext()).load(bean.getHeadImgUrl()).into(holder.ivHeadImg);
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_head_img)
            ImageView ivHeadImg;
            @BindView(R.id.tv_account_no)
            TextView tvAccountNo;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_commission)
            TextView commission;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
