package com.xygj.app.jinrirong.activity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.product.presenter.WithdrawPresenter;
import com.xygj.app.jinrirong.activity.product.view.WithdrawView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * 提现页面
 */
public class WithdrawActivity extends BaseMvpActivity<WithdrawView, WithdrawPresenter> implements WithdrawView {

    @BindView(R.id.rv_pay_type)
    RecyclerView mRvPayType;
    @BindView(R.id.tv_withdraw_money)
    TextView tvWithdrawMoney;
    @BindView(R.id.tv_withdraw_cost)
    TextView tvWithdrawCost;
    @BindView(R.id.et_account_no)
    EditText etAccountNo;
    @BindView(R.id.et_withdraw_money)
    EditText etWithdrawMoney;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    private int currentWithdrawType = 2; // 2 支付宝 1 银行卡

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initView() {
        List<String> strings = new ArrayList<>();
        strings.add("提现到支付宝");
        strings.add("提现到银行卡");
        ChoosePayTypeAdapter adapter = new ChoosePayTypeAdapter(strings, this);
        mRvPayType.setAdapter(adapter);
        mRvPayType.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new ChoosePayTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        currentWithdrawType = 2;
                        etAccountNo.setHint("请填写支付宝帐号");
                        etBankName.setVisibility(View.GONE);
                        etUserName.setVisibility(View.GONE);
                        break;
                    case 1:
                        currentWithdrawType = 1;
                        etAccountNo.setHint("请填写银行卡号");
                        etBankName.setVisibility(View.VISIBLE);
                        etUserName.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        String withdrawMoney = getIntent().getStringExtra("withdraw_money");
        String withdrawCost = getIntent().getStringExtra("withdraw_cost");
        tvWithdrawMoney.setText(withdrawMoney);
        tvWithdrawCost.setText(getString(R.string.withdraw_cost_text, withdrawCost));
    }

    @Override
    protected WithdrawPresenter createPresenter() {
        return new WithdrawPresenter();
    }

    //提交申请
    @OnClick(R.id.btn_commit_apply)
    public void commitApply() {
        if (TextUtils.isEmpty(etAccountNo.getText())) {
            ToastUtils.showShort(this, "提现账户不能为空");
        } else if (TextUtils.isEmpty(etWithdrawMoney.getText())) {
            ToastUtils.showShort(this, "提现金额不能为空");
        } else if (etUserName.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etUserName.getText())) {
            ToastUtils.showShort(this, "持卡人姓名不能为空");
        } else if (etBankName.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etBankName.getText())) {
            ToastUtils.showShort(this, "开户行名为空");
        } else {
            JSONObject json = new JSONObject();
            if (currentWithdrawType==1) {
                try {
                    json.put("type", currentWithdrawType);
                    json.put("money", etWithdrawMoney.getText().toString());
                    json.put("CardNo2", etAccountNo.getText().toString());
                    json.put("HolderName", etUserName.getText().toString());
                    json.put("BankName", etBankName.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    json.put("type", currentWithdrawType);
                    json.put("money", etWithdrawMoney.getText().toString());
                    json.put("card", etAccountNo.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mPresenter.submitWithdraw(UserManager.getInstance().getToken(),
                    SafeUtils.encrypt(this, json.toString()));
        }
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
    public void onWithdrawSubmitted(HttpRespond respond) {
        if (respond.result == 1) {
            Intent intent = new Intent(this, WithdrawCommitSucceedActivity.class);
            intent.putExtra("withdraw_type", currentWithdrawType);
            intent.putExtra("withdraw_account", etAccountNo.getText().toString());
            intent.putExtra("withdraw_money", etWithdrawMoney.getText().toString());
            startActivity(intent);
            finish();
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }

    static class ChoosePayTypeAdapter extends RecyclerView.Adapter<ChoosePayTypeAdapter.Holder> {

        private List<String> mStringList;
        private Context mContext;
        private int mCurChosePosition = 0;

        private OnItemClickListener mOnItemClickListener;

        ChoosePayTypeAdapter(List<String> stringList, Context context) {
            mStringList = stringList;
            mContext = context;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.item_choose_pay_type, parent, false);
            return new Holder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, int position) {
            if (position == mCurChosePosition) {
                holder.status.setBackgroundResource(R.drawable.bg_check_item_checked);
            } else {
                holder.status.setBackgroundResource(R.drawable.bg_check_item_normal);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurChosePosition = holder.getAdapterPosition();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }
            });
            holder.mTvTitle.setText(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView mTvTitle;
            View status;

            Holder(View itemView) {
                super(itemView);
                mTvTitle = itemView.findViewById(R.id.tv_title);
                status = itemView.findViewById(R.id.v_status);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }
}
