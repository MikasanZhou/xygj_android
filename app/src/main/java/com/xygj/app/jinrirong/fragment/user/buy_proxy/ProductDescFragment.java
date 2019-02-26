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
 * 产品介绍
 * A simple {@link Fragment} subclass.
 */
public class ProductDescFragment extends BaseMvpFragment<ProductDescView, ProductDescPresenter> implements ProductDescView {
    @BindView(R.id.wv_product_des)
    WebView wvProductDes;

    public ProductDescFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_desc, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
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
        getPresenter().getHtmlData(Constants.PRODUCT_DES);
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
                wvProductDes.loadUrl(url);
            } else {
                wvProductDes.loadDataWithBaseURL(null, getHtmlData(url), "text/html;charset=UTF-8", null, null);
            }
        } else {
            ToastUtils.showShort(getContext(), respond.message);
        }
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body><html>";
    }

}
