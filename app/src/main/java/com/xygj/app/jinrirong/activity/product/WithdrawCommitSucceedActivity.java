package com.xygj.app.jinrirong.activity.product;

import android.graphics.Color;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseActivity;

public class WithdrawCommitSucceedActivity extends BaseActivity {
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.tv_account_no)
    TextView tvAccountNo;
    @BindView(R.id.tv_withdraw_money)
    TextView tvWithdrawMoney;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_withdraw_commit_succeed;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        tvAccountType.setText(getIntent().getIntExtra("withdraw_type",
                -1) == 1 ? "银行卡" : "支付宝");
        tvAccountNo.setText(getIntent().getStringExtra("withdraw_account"));
        tvWithdrawMoney.setText(getIntent().getStringExtra("withdraw_money"));
    }

    @OnClick(R.id.btn_done)
    public void onDoneClick() {
        finish();
    }
}
