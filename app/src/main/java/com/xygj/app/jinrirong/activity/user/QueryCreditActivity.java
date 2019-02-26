package com.xygj.app.jinrirong.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.md5.SafeUtils;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.QueryCreditPresenter;
import com.xygj.app.jinrirong.activity.user.view.QueryCreditView;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.QueryResultBean;

/**
 * 查征信
 */
public class QueryCreditActivity extends BaseMvpActivity<QueryCreditView, QueryCreditPresenter> implements QueryCreditView {
    @BindView(R.id.et_true_name)
    EditText etTrueName;
    @BindView(R.id.et_id_card_num)
    EditText etIdCardNum;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_query_history)
    TextView tvQueryHistory;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_query_credit;
    }

    @Override
    protected void initView() {
        tvQueryHistory.setText(Html.fromHtml("<u>" + "查询历史" + "</u>"));
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
    }

    @Override
    protected QueryCreditPresenter createPresenter() {
        return new QueryCreditPresenter();
    }

    @OnClick(R.id.btn_query)
    public void onQueryClick() {
        if (TextUtils.isEmpty(etTrueName.getText()) || TextUtils.isEmpty(etIdCardNum.getText()) ||
                TextUtils.isEmpty(etPhone.getText())) {
            ToastUtils.showShort(this, "相关查询条件不能为空");
        } else {
            JSONObject json = new JSONObject();
            try {
                json.put("truename", etTrueName.getText().toString());
                json.put("cardID", etIdCardNum.getText().toString());
                json.put("mobile", etPhone.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPresenter.submitQuery(UserManager.getInstance().getToken(),
                    SafeUtils.encrypt(this, json.toString()));
        }
    }

    @OnClick(R.id.tv_query_history)
    public void onQueryHistoryClick() {
        startActivity(new Intent(this, QueryHistoryActivity.class));
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
    public void onQuerySubmitted(HttpRespond<QueryResultBean> respond) {
        if (respond.result == 1) {
            PayModeActivity.jumpToPay(this, PayModeActivity.TYPE_ZX,
                    "征信查询余额", "￥" + respond.data.getPayment(),
                    respond.data.getID());
        } else {
            ToastUtils.showShort(this, respond.message);
        }
    }
}
