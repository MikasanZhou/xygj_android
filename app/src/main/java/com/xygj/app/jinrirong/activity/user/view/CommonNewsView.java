package com.xygj.app.jinrirong.activity.user.view;

import java.util.List;

import com.xygj.app.common.base.BaseView;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.CommonNewsDetail;

/**
 * Created by xuyougen on 2018/4/25.
 */

public interface CommonNewsView extends BaseView{
    void onGetCommonNewsSucceed(List<CommonNews> commonNewsList);
    void onGetCommonNewsFailed(String msg);
    void onGetCommonNewsDetailSucceed(CommonNewsDetail commonNewsDetail);
    void onGetCommonNewsDetailFailed(String message);
}
