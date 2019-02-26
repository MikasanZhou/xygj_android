package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.activity.user.presenter.HelpPresenter;
import com.xygj.app.jinrirong.activity.user.view.HelpView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.ProblemBean;

public class HelpActivity extends BaseMvpActivity<HelpView, HelpPresenter> implements HelpView, View.OnKeyListener {
    @BindView(R.id.et_key_word)
    EditText etKeyword;
    @BindView(R.id.rv_help)
    RecyclerView rvHelpList;
    private List<ProblemBean> list;
    private HelpAdapter adapter;

    @Override
    protected HelpPresenter createPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        rvHelpList.setLayoutManager(new LinearLayoutManager(this));
        etKeyword.setOnKeyListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        adapter = new HelpAdapter(list);
        rvHelpList.setAdapter(adapter);
        mPresenter.getHelpList(0, 0, "");
    }

    @OnClick({R.id.tv_rkd, R.id.tv_register_login, R.id.tv_offer, R.id.tv_recommend})
    public void onHelpTypeClick(View view) {
        switch (view.getId()) {
            case R.id.tv_rkd:
                mPresenter.getHelpList(0, 4, "");
                break;
            case R.id.tv_register_login:
                mPresenter.getHelpList(0, 5, "");
                break;
            case R.id.tv_offer:
                mPresenter.getHelpList(0, 6, "");
                break;
            case R.id.tv_recommend:
                mPresenter.getHelpList(0, 7, "");
                break;
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
    public void showHelpList(HttpRespond<List<ProblemBean>> respond) {
        if (respond.result == 1) {
            list.clear();
            list.addAll(respond.data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        String keyword = etKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            ToastUtils.showShort(this, "关键字不能为空");
        } else {
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                mPresenter.getHelpList(0, 0, keyword);
            }
        }
        return false;
    }

    class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

        private List<ProblemBean> beans;

        HelpAdapter(List<ProblemBean> beans) {
            this.beans = beans;
        }

        @NonNull
        @Override
        public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_help, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HelpAdapter.ViewHolder holder, int position) {
            ProblemBean bean = beans.get(position);
            holder.tvTitle.setText(bean.title);
            holder.wvContent.loadDataWithBaseURL(null, bean.content, "text/html", "UTF-8", "");
            holder.tvTitle.setSelected(false);
            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean selected = holder.tvTitle.isSelected();
                    holder.tvTitle.setSelected(!selected);
                    holder.rlContent.setVisibility(!selected ? View.VISIBLE : View.GONE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.rl_content)
            RelativeLayout rlContent;
            @BindView(R.id.wv_content)
            WebView wvContent;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
