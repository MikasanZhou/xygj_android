package com.xygj.app.jinrirong.fragment.home;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.xygj.app.R;
import com.xygj.app.common.utils.ScreenUtils;
import com.xygj.app.jinrirong.activity.MainActivity;
import com.xygj.app.jinrirong.adpter.LoanAdapter;
import com.xygj.app.jinrirong.adpter.SingleChooseAdapter;
import com.xygj.app.jinrirong.adpter.SingleChooseAdapter2;
import com.xygj.app.jinrirong.common.base.BaseMvpFragment;
import com.xygj.app.jinrirong.fragment.home.presenter.DiscoverLoanPresenter;
import com.xygj.app.jinrirong.fragment.home.view.DiscoverLoanView;
import com.xygj.app.jinrirong.model.IHaveInfo;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.MoneyInfo;
import com.xygj.app.jinrirong.model.NeedInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage2Fragment extends BaseMvpFragment<DiscoverLoanView, DiscoverLoanPresenter> implements DiscoverLoanView, OnLoadmoreListener {
    private List<MoneyInfo> termList;
    private SingleChooseAdapter2 singleChooseAdapter2;
    LoanAdapter mLoanAdapter;

    public HomePage2Fragment() {
    }

    @BindView(R.id.rv_product)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page2, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoanAdapter = new LoanAdapter(getActivity(), mLoanProductList);
        mRecyclerView.setAdapter(mLoanAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }

    String termId = "";
    int tid = 0;
    int cid = 0;
    boolean isFirst = true;
    String nids = "0";
    int page = 0;
    int rows = 1000;


    private void getDataFromServer() {
        page = 0;
        getPresenter().getTermTypeList();
        getPresenter().getMoneyTypeList();
        getPresenter().getIHaveList();
        // getPresenter().getNeedList();
        researchData();
    }

    @Override
    protected void initData() {
    }

    //private String[] sortNames = {"1月内", "6月内", "1年内", "1-3年", "3年以上"};
    private PopupWindow mPwChooseTerm;
    @BindView(R.id.fl_choose_sort)
    FrameLayout mFlChooseTerm;

    @OnClick(R.id.fl_choose_sort)
    public void chooseSort() {
        if (mPwChooseTerm == null) return;
        if (termList == null || mMoneyInfoList.size() == 0) {
            Toast.makeText(getActivity(), "分类数据获取失败，请下拉刷新页面", Toast.LENGTH_SHORT).show();
            return;
        }
        mPwChooseTerm.showAsDropDown(mFlChooseTerm);
    }

    @BindView(R.id.fl_choose_money)
    FrameLayout mFlChooseMoney;

    private PopupWindow mPwChooseMoney;


    @OnClick(R.id.fl_choose_money)
    public void chooseMoney() {
        if (mPwChooseMoney == null) return;
        if (mMoneyInfoList == null || mMoneyInfoList.size() == 0) {
            Toast.makeText(getActivity(), "分类数据获取失败，请下拉刷新页面", Toast.LENGTH_SHORT).show();
            return;
        }
        mPwChooseMoney.showAsDropDown(mFlChooseMoney);
    }

    /**
     * 生成单选 popupWindow
     *
     * @param context
     * @param strList
     * @param listener
     * @return
     */
    public PopupWindow generateSingleChoosePopupWindow(Context context, List<String> strList, SingleChooseAdapter.OnItemClickListener listener) {
        //获取屏幕宽度和dp值
        int screenWidth = ScreenUtils.getScreenWidth(context);
        float density = context.getResources().getDisplayMetrics().density * 2;

        View layout = getLayoutInflater().inflate(R.layout.layout_popup_window_single_check, null);
        final PopupWindow popupWindow = new PopupWindow(layout, screenWidth, ScreenUtils.getScreenHeight(context) / 2, true);
        RecyclerView recyclerView = layout.findViewById(R.id.rv_single_choose);

        SingleChooseAdapter singleChooseAdapter = new SingleChooseAdapter(
                strList,
                context,
                ContextCompat.getColor(getActivity(), R.color.colorLightGray),
                ContextCompat.getColor(getActivity(), R.color.colorWhite)
        );

        singleChooseAdapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(singleChooseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(density * 2);
        }
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimStyle);
        return popupWindow;
    }


    @BindView(R.id.fl_choose_type)
    FrameLayout mFlChooseType;

    private PopupWindow mPwChooseType;


    @OnClick(R.id.fl_choose_type)
    public void chooseType() {
        if (mPwChooseType == null) {
            if (mIHaveInfoList == null) {
                Toast.makeText(getActivity(), "类型数据加载失败，请下拉刷新", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> strHaveList = new ArrayList<>();

            for (IHaveInfo iHaveInfo : mIHaveInfoList) {
                strHaveList.add(iHaveInfo.getName());
            }
            //获取屏幕宽度和dp值
            int screenWidth = ScreenUtils.getScreenWidth(getActivity());
            float density = getActivity().getResources().getDisplayMetrics().density * 2;

            View layout = getLayoutInflater().inflate(R.layout.layout_popup_window_chose_type, null);
            final PopupWindow popupWindow = new PopupWindow(layout, screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPwChooseType = popupWindow;
            RecyclerView recyclerView = layout.findViewById(R.id.rv_single_choose);

            singleChooseAdapter2 = new SingleChooseAdapter2(mIHaveInfoList, getActivity());
            singleChooseAdapter2.setmCurChosePosition(cid);
            recyclerView.setAdapter(singleChooseAdapter2);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            TextView tvReset = layout.findViewById(R.id.tv_reset);
            TextView tvConfirm = layout.findViewById(R.id.tv_confirm);
            tvReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleChooseAdapter2.resetChoose();
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cid = singleChooseAdapter2.getCurChosePosition();
                    researchData();
                    mPwChooseType.dismiss();
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(density * 2);
            }
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimStyle);
        }
        mPwChooseType.showAsDropDown(mFlChooseType);
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

    List<MoneyInfo> mMoneyInfoList;

    @Override
    public void onGetMoneyTypeListSucceed(List<MoneyInfo> moneyInfoList) {
        mRefreshLayout.setRefreshing(false);
        mMoneyInfoList = moneyInfoList;
        mRefreshLayout.setRefreshing(false);
        if (mPwChooseMoney == null) {
            List<String> stringList = new ArrayList<>();

            for (MoneyInfo moneyInfo : moneyInfoList) {
                stringList.add(moneyInfo.getName());
            }

            mPwChooseMoney = generateSingleChoosePopupWindow(getActivity(), stringList, new SingleChooseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    tid = mMoneyInfoList.get(position).getID();
                    researchData();
                    mPwChooseMoney.dismiss();
                }
            });

        }
    }

    private void researchData() {
        mRefreshLayout.setRefreshing(true);
        getPresenter().getLoanList(termId, tid, cid, nids, page, rows);
    }

    private List<IHaveInfo> mIHaveInfoList;

    @Override
    public void onGetIHaveTypeListSucceed(List<IHaveInfo> iHaveInfoList) {
        mRefreshLayout.setRefreshing(false);
        mIHaveInfoList = iHaveInfoList;
    }

    //private List<NeedInfo> mNeedInfoList;
    private List<LoanProduct> mLoanProductList = new ArrayList<>();

    @Override
    public void onGetNeedListSucceed(List<NeedInfo> needInfoList) {
        mRefreshLayout.setRefreshing(false);
        //mNeedInfoList = needInfoList;
    }

    @Override
    public void onGetLoanListSucceed(List<LoanProduct> loanProductList) {
        mRefreshLayout.setRefreshing(false);
        refreshLayout.finishLoadmore();
        if (page == 0)
            mLoanProductList.clear();
        mLoanProductList.addAll(loanProductList);
        mLoanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetLoanListFailed(String message) {
        refreshLayout.finishLoadmore();
        mRefreshLayout.setRefreshing(false);
        if (page == 0)
            mLoanProductList.clear();
        mLoanAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetTermListSucceed(List<MoneyInfo> data) {
        mRefreshLayout.setRefreshing(false);
        termList = data;
        mRefreshLayout.setRefreshing(false);
        if (mPwChooseTerm == null) {
            List<String> stringList = new ArrayList<>();
            stringList.add("期限不限");
            for (MoneyInfo moneyInfo : termList) {
                stringList.add(moneyInfo.getName());
            }
            mPwChooseTerm = generateSingleChoosePopupWindow(getActivity(), stringList,
                    new SingleChooseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (position == 0) {
                                termId = String.valueOf(0);
                            } else {
                                termId = String.valueOf(termList.get(--position).getID());
                            }
                            researchData();
                            mPwChooseTerm.dismiss();
                        }
                    });
        }
    }

    @Override
    protected DiscoverLoanPresenter createPresenter() {
        return new DiscoverLoanPresenter();
    }

    @Override
    protected void lazyLoadData() {
        page = 0;
        getPresenter().getTermTypeList();
        getPresenter().getMoneyTypeList();
        getPresenter().getIHaveList();
        cid = MainActivity.tempCid == -1 ? 0 : MainActivity.tempCid;
        researchData();
    }

    public void setIHaveType(int id, String name) {
        cid = id;
        tid = 0;
        nids = "0";
        page = 0;
        rows = 100;
        termId = "";
        if (isFirst) {
            isFirst = false;
        } else {
            researchData();
        }
        if (singleChooseAdapter2 != null) singleChooseAdapter2.setmCurChosePosition(cid);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        getPresenter().getLoanList(termId, tid, cid, nids, ++page, rows);
    }
}
