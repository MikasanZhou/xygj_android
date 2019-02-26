package com.xygj.app.jinrirong.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.common.widget.CustomDialog;
import com.xygj.app.jinrirong.activity.user.presenter.MyMessagePresenter;
import com.xygj.app.jinrirong.activity.user.view.MyMessageView;
import com.xygj.app.jinrirong.adpter.MessageListAdapter;
import com.xygj.app.jinrirong.adpter.MessageTypeAdapter;
import com.xygj.app.jinrirong.config.UserManager;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.MessageBean;

/**
 * Created by Yangli on 2018/4/18.
 */

public class MyMessageActivity extends BaseMvpActivity<MyMessageView, MyMessagePresenter> implements MyMessageView {
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private ArrayList<MessageBean> messageBeans;
    private String messageJson;

    @Override
    protected MyMessagePresenter createPresenter() {
        return new MyMessagePresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        messageJson = getIntent().getStringExtra("message_json");
        if (messageJson == null)
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getDataFromServer();
                }
            });
        else
            mRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {
        super.initData();
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        messageBeans = new ArrayList<>();
        if (messageJson != null) {
            List<MessageBean> list = new Gson().fromJson(messageJson, new TypeToken<List<MessageBean>>() {
            }.getType());
            MessageListAdapter messageAdapter = new MessageListAdapter(list);
            rvMessage.setAdapter(messageAdapter);
            return;
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        mPresenter.requestMassages(UserManager.getInstance().getToken());
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
        hideLoadingDialog();
        new CustomDialog.Builder(this)
                .setTitle("哎呀，连接失败")
                .setContent("是否尝试重新连接？")
                .setListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        getDataFromServer();
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                }).build().show();
    }

    @Override
    public void showMessageTypes(HttpRespond<List<List<MessageBean>>> respond) {
        mRefreshLayout.setRefreshing(false);
        messageBeans.clear();
        if (respond.result == 1) {
            // 构造消息类型
            for (List<MessageBean> list : respond.data) {
                MessageBean bean = new MessageBean();
                if (list.size() == 0)
                    continue;
                MessageBean messageBean = list.get(0);
                bean.contents = messageBean.contents;
                bean.type = messageBean.type;
                bean.time = messageBean.time;
                bean.title = messageBean.type == 0 ? "系统消息" : "通知消息";
                bean.id = messageBean.type == 0 ? R.mipmap.ic_system : R.mipmap.ic_bell;
                bean.jsonStr = new Gson().toJson(list);
                messageBeans.add(bean);
            }
            MessageTypeAdapter typeAdapter = new MessageTypeAdapter(messageBeans);
            typeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    MessageBean messageBean = messageBeans.get(position);
                    mPresenter.markMessages(UserManager.getInstance().getToken(), messageBean.type);
                    Intent intent = new Intent(MyMessageActivity.this,
                            MyMessageActivity.class);
                    intent.putExtra("message_json", messageBean.jsonStr);
                    startActivity(intent);
                }
            });
            rvMessage.setAdapter(typeAdapter);
        }
    }

    @Override
    public void onMarkMessages(HttpRespond respond) {
        if (respond.result != 1) {
            ToastUtils.showShort(this, respond.message);
        }
    }
}
