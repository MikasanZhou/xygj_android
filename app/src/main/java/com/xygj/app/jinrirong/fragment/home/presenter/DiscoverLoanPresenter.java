package com.xygj.app.jinrirong.fragment.home.presenter;

import java.util.List;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.fragment.home.view.DiscoverLoanView;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.IHaveInfo;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.MoneyInfo;
import com.xygj.app.jinrirong.model.NeedInfo;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by xuyougen on 2018/4/17.
 */

public class DiscoverLoanPresenter extends BasePresenter<DiscoverLoanView> {
    public void getMoneyTypeList() {
        addTask(RetrofitHelper.getInstance().getService().getMoneyTypeList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER), new Consumer<HttpRespond<List<MoneyInfo>>>() {
            @Override
            public void accept(HttpRespond<List<MoneyInfo>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetMoneyTypeListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getTermTypeList() {
        addTask(RetrofitHelper.getInstance().getService().getTermTypeList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER), new Consumer<HttpRespond<List<MoneyInfo>>>() {
            @Override
            public void accept(HttpRespond<List<MoneyInfo>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetTermListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getIHaveList() {
        addTask(RetrofitHelper.getInstance().getService().getIHaveList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER), new Consumer<HttpRespond<List<IHaveInfo>>>() {
            @Override
            public void accept(HttpRespond<List<IHaveInfo>> listHttpRespond) throws Exception {
                if (listHttpRespond.result == 1) {
                    getView().onGetIHaveTypeListSucceed(listHttpRespond.data);
                }
            }
        });
    }

    public void getNeedList() {
        addTask(RetrofitHelper.getInstance().getService().getNeedList(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER), new Consumer<HttpRespond<List<NeedInfo>>>() {
                    @Override
                    public void accept(HttpRespond<List<NeedInfo>> listHttpRespond) throws Exception {
                        if (listHttpRespond.result == 1) {
                            getView().onGetNeedListSucceed(listHttpRespond.data);
                        }
                    }
                }
        );
    }

    //    page      当前页码，默认值为0
    //    rows      每页数据量大小，默认值未6
    //    tid       额度条件查找（贷款金额类型ID）
    //    cid       我有的条件（贷款分类ID）
    //    nids      贷款所需材料的条件（所需材料ID的集合）
    //    jkday     借款期限id
    public void getLoanList(String termId, int tid, int cid, String nids, int page, int rows) {
        addTask(RetrofitHelper.getInstance().getService().getLoanListBy(
                Constants.CLIENT,
                Constants.PACKAGE,
                Constants.VER, termId, tid, cid, nids, page, rows), new Consumer<HttpRespond<List<LoanProduct>>>() {
                    @Override
                    public void accept(HttpRespond<List<LoanProduct>> listHttpRespond) throws Exception {
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
