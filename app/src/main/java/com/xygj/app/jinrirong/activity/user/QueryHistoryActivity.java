package com.xygj.app.jinrirong.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.QueryHistoryPresenter;
import com.xygj.app.jinrirong.activity.user.view.QueryHistoryView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HistoryBean;
import com.xygj.app.jinrirong.model.HttpRespond;

public class QueryHistoryActivity extends BaseMvpActivity<QueryHistoryView, QueryHistoryPresenter> implements QueryHistoryView {
    @BindView(R.id.rv_query)
    RecyclerView rvQuery;
    private List<HistoryBean> list;
    private QueryAdapter adapter;

    @Override
    protected QueryHistoryPresenter createPresenter() {
        return new QueryHistoryPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_query_history;
    }

    @Override
    protected void initView() {
        rvQuery.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        list = new ArrayList<>();
        adapter = new QueryAdapter(list);
        rvQuery.setAdapter(adapter);
    }

    private void getDataFromServer() {
        mPresenter.requestQueryHistory(UserManager.getInstance().getToken(), 0);
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
        hideLoadingView();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromServer();
            }
        }, 300);
    }

    @Override
    public void showQueryList(HttpRespond<List<HistoryBean>> respond) {
        if (respond.result == 1) {
            list.clear();
            list.addAll(respond.data);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {
        private List<HistoryBean> list;

        QueryAdapter(List<HistoryBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_query, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final HistoryBean bean = list.get(position);
            holder.tvName.setText(bean.getTrueName());
            holder.tvPhone.setText(bean.getPhoneNum());
            holder.tvState.setText(bean.getStatus());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QueryHistoryActivity.this,
                            CreditInfoActivity.class);
                    intent.putExtra("id", bean.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_state)
            TextView tvState;
            @BindView(R.id.tv_detail)
            TextView tvDetail;
            @BindView(R.id.tv_phone)
            TextView tvPhone;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
