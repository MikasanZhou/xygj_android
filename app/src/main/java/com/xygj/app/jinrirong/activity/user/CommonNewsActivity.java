package com.xygj.app.jinrirong.activity.user;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.xygj.app.R;
import com.xygj.app.common.base.BaseMvpActivity;
import com.xygj.app.jinrirong.activity.user.presenter.CommonNewsPresenter;
import com.xygj.app.jinrirong.activity.user.view.CommonNewsView;
import com.xygj.app.jinrirong.common.MyWebViewActivity;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.CommonNewsDetail;

public class CommonNewsActivity extends BaseMvpActivity<CommonNewsView, CommonNewsPresenter> implements CommonNewsView {

    @BindView(R.id.lv_news)
    ListView mListView;

    List<CommonNews> mCommonNewsList = new ArrayList<>();
    ArrayAdapter mArrayAdapter;

    @Override
    protected CommonNewsPresenter createPresenter() {
        return new CommonNewsPresenter();
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_common_news;
    }

    @Override
    protected void initView() {
        requestTranslucentStatusBar(Color.TRANSPARENT, false);
        mArrayAdapter = new ArrayAdapter<CommonNews>(this, R.layout.simple_list, mCommonNewsList);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.getCommonNewsDetail(mCommonNewsList.get(position).getID());
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getCommonNews();
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
    public void onGetCommonNewsSucceed(List<CommonNews> commonNewsList) {
        mCommonNewsList.clear();
        mCommonNewsList.addAll(commonNewsList);
        mArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCommonNewsFailed(String msg) {

    }

    @Override
    public void onGetCommonNewsDetailSucceed(CommonNewsDetail commonNewsDetail) {
        startActivity(MyWebViewActivity.getIntent(this, commonNewsDetail.getTitle(), commonNewsDetail.getContents()));
    }

    @Override
    public void onGetCommonNewsDetailFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
