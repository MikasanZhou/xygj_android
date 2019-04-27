package com.xygj.app.jinrirong.activity.selectlist.presenter;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.activity.selectlist.view.ProductListView;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.LoanProduct;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/4/20 0020.
 */

public class ProductListPresenter extends BasePresenter<ProductListView> {

    //    page      当前页码，默认值为0
    //    rows      每页数据量大小，默认值未6
    //    tid       额度条件查找（贷款金额类型ID）
    //    cid       我有的条件（贷款分类ID）
    //    nids      贷款所需材料的条件（所需材料ID的集合）
    //    jkday     借款期限id
    public void getLoanList(String termId, int tid, int cid, String nids, int page, int rows) {
        getView().showLoadingView();
        addTask(RetrofitHelper.getInstance().getService().getLoanListBy(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER, termId, tid, cid, nids, page, rows), new Consumer<HttpRespond<List<LoanProduct>>>() {
                    @Override
                    public void accept(HttpRespond<List<LoanProduct>> listHttpRespond) throws Exception {
                        getView().hideLoadingView();
                        if (listHttpRespond.result == 1) {
                            getView().onGetLoanListSucceed(listHttpRespond.data);
                        } else {
                            getView().onGetLoanListFailed(listHttpRespond.message);
                        }
                    }
                }
        );
    }
}
