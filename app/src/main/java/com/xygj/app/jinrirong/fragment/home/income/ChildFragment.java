package com.xygj.app.jinrirong.fragment.home.income;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.RecIncomeModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends BaseMvpFragment<ChildView, ChildPresenter> implements ChildView {
    private static final String ARG_INCOME_TYPE = "income_type";
    private static final String ARG_STATUS = "status";

    @BindView(R.id.rv_record)
    RecyclerView mRecyclerView;
    private int type;
    int status;
    private List<RecIncomeModel> list;
    private MAdapter adapter;


    public ChildFragment() {
    }

    public static ChildFragment newInstance(int type, int status) {
        ChildFragment fragment = new ChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INCOME_TYPE, type);
        args.putInt(ARG_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_INCOME_TYPE);
            status = getArguments().getInt(ARG_STATUS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_child, container, false);
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
    protected ChildPresenter createPresenter() {
        return new ChildPresenter();
    }

    @Override
    protected void lazyLoadData() {
        getPresenter().requestPromoteIncomeList(UserManager.getInstance().getToken(), type, status, 0);
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
    public void showPromoteList(List<RecIncomeModel> data) {
        list.clear();
        if (data.size() == 0) return;
        Log.i("---------", "type:" + type + ",status:" + status);
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPromoteListFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> {
        private List<RecIncomeModel> beans;

        MAdapter(List<RecIncomeModel> beans) {
            this.beans = beans;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_promote_income2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecIncomeModel bean = beans.get(position);
            Glide.with(getActivity()).load(bean.getLogurl()).into(holder.ivIcon);
            holder.tvNo.setText("订单编号：" + bean.getOrderSn());
            holder.tvJsTime.setText(bean.getSettletime());
            holder.tvName.setText(bean.getGoodname());
            holder.tvPhone.setText("手机号码：" + bean.getMobile());
            holder.tvTime.setText("提交时间：" + bean.getAddtime());
            if (bean.getStatus() == 0) {
                //佣金模式:1按比例 2按金额
                if (bean.getYjtype() == 1) {
                    if(bean.getApplyBonus()!=null){
                        holder.tvTips.setText("申请"+bean.getApplyBonus() +"+放款"+ bean.getBonusRate() + "%");
                    }else{
                        holder.tvTips.setText("放款"+ bean.getBonusRate() + "%");
                    }
                    holder.tvTips.setBackgroundResource(R.drawable.bg_btn_gray_corner);
                    holder.tvTips.setTextColor(Color.GRAY);
                } else {
                    if(bean.getApplyBonus()!=null){
                        holder.tvTips.setText("申请"+bean.getApplyBonus() +"+放款"+ bean.getYmoney() + "元");
                    }else{
                        holder.tvTips.setText("放款" + bean.getYmoney() + "元");
                    }
                    if (type==0){
                        holder.tvTips.setBackgroundResource(R.drawable.bg_btn_login);
                        holder.tvTips.setTextColor(Color.WHITE);
                    }else{
                        holder.tvTips.setBackgroundResource(R.drawable.bg_btn_gray_corner);
                        holder.tvTips.setTextColor(Color.GRAY);
                    }
                }
            }else {
                if(bean.getApplyBonus()!=null){
                    holder.tvTips.setText("申请"+bean.getApplyBonus() +"+放款"+ bean.getBonus() + "元");
                }else{
                    holder.tvTips.setText("放款" + bean.getBonus() + "元");
                }
                holder.tvTips.setBackgroundResource(R.drawable.bg_btn_login);
            }
        }
        @Override
        public int getItemCount() {
            return beans.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_no)
            TextView tvNo;
            @BindView(R.id.tv_js_time)
            TextView tvJsTime;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_phone)
            TextView tvPhone;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_tips)
            TextView tvTips;
            @BindView(R.id.iv_icon)
            ImageView ivIcon;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
