package com.xygj.app.jinrirong.fragment.user.buy_proxy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xygj.app.R;
import com.xygj.app.common.utils.ToastUtils;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.fragment.user.presenter.ProductDescPresenter;
import com.xygj.app.jinrirong.fragment.user.view.ProductDescView;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.HttpRespond;

/**
 * 购买须知
 * A simple {@link Fragment} subclass.
 */
public class ShouldKnownFragment extends BaseMvpFragment<ProductDescView, ProductDescPresenter> implements ProductDescView {

    @BindView(R.id.wv_should_know)
    WebView wvShouldKnow;

    public ShouldKnownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_should_known, container, false);
        ButterKnife.bind(this, layout);
        initView();
        //mRecyclerView.setAdapter(new DescAdapter());
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onTokenInvalidate() {

    }

    @Override
    public void onNetworkConnectFailed() {

    }

    @Override
    public void showHtmlContent(HttpRespond<HtmlData> respond) {
        if (respond.result == 1) {
            String url = respond.data.getContents();
            if (url.startsWith("http")) {
                wvShouldKnow.loadUrl(url);
            } else {
                wvShouldKnow.loadDataWithBaseURL(null, url, "text/html;charset=UTF-8", null, null);
            }
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    @Override
    protected ProductDescPresenter createPresenter() {
        return new ProductDescPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoadData() {
        getPresenter().getHtmlData(Constants.BUY_KNOW);
    }

    /*
     * private class DescAdapter extends RecyclerView.Adapter<DescAdapter.Holder> {
    @NonNull
    @Override
    public DescAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View layout = getLayoutInflater().inflate(R.layout.item_desc, parent, false);
    return new DescAdapter.Holder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull DescAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
    return 4;
    }

    public class Holder extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
    super(itemView);

    }
    }
    }
     */
}
