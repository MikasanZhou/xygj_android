package com.xygj.app.jinrirong.config;

import java.util.List;

import com.xygj.app.jinrirong.model.CaptchaBean;
import com.xygj.app.jinrirong.model.CommonNews;
import com.xygj.app.jinrirong.model.CommonNewsDetail;
import com.xygj.app.jinrirong.model.CreditCard;
import com.xygj.app.jinrirong.model.CreditCardDetail;
import com.xygj.app.jinrirong.model.CreditInfoBean;
import com.xygj.app.jinrirong.model.HistoryBean;
import com.xygj.app.jinrirong.model.HomeBanner;
import com.xygj.app.jinrirong.model.HttpRespond;
import com.xygj.app.jinrirong.model.IHaveInfo;
import com.xygj.app.jinrirong.model.LoanCateAndLocation;
import com.xygj.app.jinrirong.model.LoanProduct;
import com.xygj.app.jinrirong.model.LoginData;
import com.xygj.app.jinrirong.model.MessageBean;
import com.xygj.app.jinrirong.model.ModuleBean;
import com.xygj.app.jinrirong.model.MoneyInfo;
import com.xygj.app.jinrirong.model.HtmlData;
import com.xygj.app.jinrirong.model.NewMessageBean;
import com.xygj.app.jinrirong.model.PayInfoBean;
import com.xygj.app.jinrirong.model.PopBean;
import com.xygj.app.jinrirong.model.PosterBean;
import com.xygj.app.jinrirong.model.PriceBean;
import com.xygj.app.jinrirong.model.ProblemBean;
import com.xygj.app.jinrirong.model.ProductBean;
import com.xygj.app.jinrirong.model.ProxyBean;
import com.xygj.app.jinrirong.model.NeedInfo;
import com.xygj.app.jinrirong.model.QueryResultBean;
import com.xygj.app.jinrirong.model.RecIncomeModel;
import com.xygj.app.jinrirong.model.RongKeBean;
import com.xygj.app.jinrirong.model.RankListBean;
import com.xygj.app.jinrirong.model.ShareBean;
import com.xygj.app.jinrirong.model.TimeStampBean;
import com.xygj.app.jinrirong.model.UpdateBean;
import com.xygj.app.jinrirong.model.credit_card.Bank;
import com.xygj.app.jinrirong.model.credit_card.BankType;
import com.xygj.app.jinrirong.model.credit_card.CardType;
import com.xygj.app.jinrirong.model.credit_card.MoneyType;
import com.xygj.app.jinrirong.model.credit_card.YearFeeType;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xuyougen on 2018/4/11.
 */

public interface ApiService {
    @GET(ApiFactory.GET_CAPTCHA)
    Observable<HttpRespond<CaptchaBean>> getCaptcha(
            @Query("client") String client,
            @Query("ver") String ver,
            @Query("package") String packageName);

    @GET(ApiFactory.GET_SMS)
    Observable<HttpRespond> getSmsCode(
            @Query("client") String client,
            @Query("ver") String ver,
            @Query("package") String packageName,
            @Query("Mobile") String phoneNum,
            @Query("type") int type,
            @Query("code") String code);

    @POST(ApiFactory.REGISTER)
    Observable<HttpRespond> register(@Body RequestBody requestBody);

    @POST(ApiFactory.SIGN_OUT)
    Observable<HttpRespond> signOut(@Body RequestBody requestBody);

    @POST(ApiFactory.MY_MESSAGE)
    Observable<HttpRespond<List<List<MessageBean>>>> requestMessages(@Body RequestBody requestBody);

    @POST(ApiFactory.MEMBER_INFO)
    Observable<HttpRespond<String>> requestMemberInfo(@Body RequestBody requestBody);

    @POST(ApiFactory.MEMBER_INFO)
    Observable<HttpRespond> requestMemberInfo2(@Body RequestBody requestBody);

    @POST(ApiFactory.MEMBER_MODULE)
    Observable<HttpRespond<List<ModuleBean>>> requestMemberModule(@Body RequestBody requestBody);

    @POST(ApiFactory.MODIFY_MEMBER_INFO)
    Observable<HttpRespond> submitMemberInfo(@Body RequestBody requestBody);

    @POST(ApiFactory.PROMOTE_INFO)
    Observable<HttpRespond<RankListBean>> requestPromoteInfo(@Body RequestBody requestBody);

    @POST(ApiFactory.PROMOTE_POSTER)
    Observable<HttpRespond<PosterBean>> requestPromotePoster(@Body RequestBody requestBody);

    @POST(ApiFactory.PROMOTE_INCOME)
    Observable<HttpRespond<String>> requestPromoteIncome(@Body RequestBody requestBody);

    @POST("center/member/getpromotdata")
    Observable<HttpRespond<List<RecIncomeModel>>> requestPromoteIncome2(@Body RequestBody requestBody);

    @POST(ApiFactory.USER_INCOME)
    Observable<HttpRespond<String>> requestUserIncome(@Body RequestBody requestBody);

    @POST(ApiFactory.CREDIT_INFO)
    Observable<HttpRespond<CreditInfoBean>> requestCreditInfo(@Body RequestBody requestBody);

    @POST("center/member/againcheck")
    Observable<String> againcheck(@Body RequestBody requestBody);

    @POST(ApiFactory.CREDIT_INFO)
    Observable<String> requestCreditInfo2(@Body RequestBody requestBody);

    @POST(ApiFactory.CREDIT_INFO_PAY)
    Observable<HttpRespond<PayInfoBean>> payQueryCreditInfo(@Body RequestBody requestBody);

    @POST(ApiFactory.PROXY_PAY)
    Observable<String> payProxy(@Body RequestBody requestBody);

    @POST(ApiFactory.SHARE_RECOMMEND)
    Observable<HttpRespond<ShareBean>> requestShareContent(@Body RequestBody requestBody);

    @POST(ApiFactory.QUERY_CREDIT)
    Observable<HttpRespond<QueryResultBean>> queryCredit(@Body RequestBody requestBody);

    @POST(ApiFactory.QUERY_HISTORY)
    Observable<HttpRespond<List<HistoryBean>>> requestQueryHistory(@Body RequestBody requestBody);

    @POST(ApiFactory.MODIFY_PWD)
    Observable<HttpRespond> modifyPwd(@Body RequestBody requestBody);

    @POST(ApiFactory.PROXY_GRADE)
    Observable<HttpRespond<List<ProxyBean>>> requestProxy(@Body RequestBody requestBody);

    @POST(ApiFactory.REC_PRODUCT_LISTS)
    Observable<HttpRespond<List<ProductBean>>> requestRecProductList(@Body RequestBody requestBody);

    @POST(ApiFactory.PRODUCT_LIST)
    Observable<HttpRespond<List<ProductBean>>> requestProductList(@Body RequestBody requestBody);

//    @POST(ApiFactory.PRODUCT_LIST)
//    Observable<HttpRespond<List<ProductBean>>> requestProductList(@Body RequestBody requestBody);

    @POST(ApiFactory.MONEY_DATA)
    Observable<HttpRespond<String>> requestMoneyData(@Body RequestBody requestBody);

    @POST(ApiFactory.SUBMIT_WITHDRAW)
    Observable<HttpRespond> submitWithdraw(@Body RequestBody requestBody);

    @POST(ApiFactory.MARK_MESSAGE)
    Observable<HttpRespond> markMessages(@Body RequestBody requestBody);

    @POST(ApiFactory.NEW_MESSAGE)
    Observable<HttpRespond<NewMessageBean>> newMessage(@Body RequestBody requestBody);

    @POST(ApiFactory.CLIENT_LIST)
    Observable<String> getClientList(@Body RequestBody requestBody);

    @GET(ApiFactory.PRICE_LIST)
    Observable<HttpRespond<List<PriceBean>>> getPriceList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver
    );

    @GET(ApiFactory.VERIFY_SMS_CODE)
    Observable<HttpRespond> verifySmsCode(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("Mobile") String phoneNum,
            @Query("Code") String smsCode
    );

    @GET(ApiFactory.PROBLEM_LIST)
    Observable<HttpRespond<List<ProblemBean>>> getProblemList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("cid") int id,
            @Query("page") int page,
            @Query("rows") int rows
    );

    @GET(ApiFactory.HELP_LIST)
    Observable<HttpRespond<List<ProblemBean>>> getHelpList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("cateid") int id,
            @Query("words") String key,
            @Query("page") int page,
            @Query("rows") int rows
    );

    @GET(ApiFactory.RESET_PWD)
    Observable<HttpRespond> resetPwd(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("Mobile") String phoneNum,
            @Query("pwd") String pwd
    );

    @GET(ApiFactory.GET_POP)
    Observable<HttpRespond<PopBean>> getPopInfo(
            @Query("client") String client,
            @Query("ver") String ver,
            @Query("package") String packageName);

    @GET("Home/home/version")
    Observable<HttpRespond<UpdateBean>> checkUpdate(
            @Query("client") String client,
            @Query("ver") String ver,
            @Query("package") String packageName);

    @GET(ApiFactory.LOGIN)
    Observable<HttpRespond<LoginData>> login(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("mobile") String phoneNum,
            @Query("token") String token,
            @Query("ticksid") String tickId,
            @Query("ticks") String ticks,
            @Query("DeviceToken") String deviceToken);

    @GET(ApiFactory.GET_TIMESTAMP)
    Observable<HttpRespond<TimeStampBean>> getTime(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver);

    //    client=android&package=android.ceshi&ver=v1.1&aid=1&num=2
    @GET(ApiFactory.GET_HOME_BANNER)
    Observable<HttpRespond<List<HomeBanner>>> getHomeBanner(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("aid") int aid,
            @Query("num") int num);

    @GET(ApiFactory.GET_HOME_LOAN_CATEGORY)
    Observable<HttpRespond<LoanCateAndLocation>> getHomeLoanCategory(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("aid") int rows);

    @GET(ApiFactory.GET_HTML_TEXT)
    Observable<HttpRespond<HtmlData>> getHtmlContent(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("id") int id);

    //获取注册协议 //isrec=1 是否是推荐列表
    @GET(ApiFactory.GET_HOME_ITEMS)
    Observable<HttpRespond<List<LoanProduct>>> getHomeItems(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("isrec") int isrec,
            @Query("rows") int rows);

    //获取 网贷平台 详情
    @GET(ApiFactory.GET_LOAN_PRODUCT_DETAIL)
    Observable<HttpRespond<LoanProduct>> getLoanProductDetail(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("id") int id);

    @POST(ApiFactory.GET_LOAN_PRODUCT_STATISTICS)
    Observable<HttpRespond> productStatistics(
            @Body RequestBody requestBody);

    //获取 金额类型信息  列表
    @GET(ApiFactory.GET_MONEY_TYPE)
    Observable<HttpRespond<List<MoneyInfo>>> getMoneyTypeList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver);

    @GET(ApiFactory.GET_TERM_TYPE)
    Observable<HttpRespond<List<MoneyInfo>>> getTermTypeList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver);

    //获取 我有 类型列表
    @GET(ApiFactory.GET_I_HAVE_TYPE)
    Observable<HttpRespond<List<IHaveInfo>>> getIHaveList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver);

    //获取 所需材料 类型列表
    @GET(ApiFactory.GET_NEED_TYPE)
    Observable<HttpRespond<List<NeedInfo>>> getNeedList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver);

    //获取 信用卡推荐 列表
    @GET(ApiFactory.GET_CREDIT_CARD_RECOMMEND_LIST)
    Observable<HttpRespond<List<CreditCard>>> getCreditCardRecommendList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("isrec") int isrec,
            @Query("rows") int rows);

    //获取 信用卡银行 列表
    @GET(ApiFactory.GET_CREDIT_CARD_BANK_LIST)
    Observable<HttpRespond<List<Bank>>> getCreditCardBankList(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver);

    //获取 信用卡详情信息
    @GET(ApiFactory.GET_CREDIT_CARD_DETAIL)
    Observable<HttpRespond<CreditCardDetail>> getCreditCardDetail(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("id") int id);

    //获取 信用卡 银行 分类信息
    @GET(ApiFactory.GET_CREDIT_CARD_TYPE)
    Observable<HttpRespond<List<BankType>>> getCreditCardBankType(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("type") int type);

    //获取 信用卡 卡种 分类信息
    @GET(ApiFactory.GET_CREDIT_CARD_TYPE)
    Observable<HttpRespond<List<CardType>>> getCreditCardType(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("type") int type);

    //获取 信用卡 币种 分类信息
    @GET(ApiFactory.GET_CREDIT_CARD_TYPE)
    Observable<HttpRespond<List<MoneyType>>> getCreditCardCoinType(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("type") int type);

    //获取 信用卡 年费 分类信息
    @GET(ApiFactory.GET_CREDIT_CARD_TYPE)
    Observable<HttpRespond<List<YearFeeType>>> getCreditCardYearFeeType(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("type") int type);

    //获取 根据信用卡分类筛选获取列表
    @GET(ApiFactory.GET_CREDIT_CARD_BY)
    Observable<HttpRespond<List<CreditCard>>> getCreditCardBy(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("bankid") int bankid,
            @Query("cardid") int cardid,
            @Query("bitid") int bitid,
            @Query("feeid") int feeid,
            @Query("page") int page,
            @Query("rows") int rows);

    //获取 根据贷款产品分类筛选获取列表
    @GET(ApiFactory.GET_LOAN_PRODUCT_LIST_BY)
    Observable<HttpRespond<List<LoanProduct>>> getLoanListBy(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("jkday") String termId,
            @Query("tid") int tid,
            @Query("cid") int cid,
            @Query("nids") String nids,
            @Query("page") int page,
            @Query("rows") int rows);

    //获取 融客店信息
    @POST(ApiFactory.GET_RONG_KE_INFO)
    Observable<HttpRespond<RongKeBean>> getRongKeInfo(@Body RequestBody body);

    //获取公告信息
    @GET(ApiFactory.PROBLEM_LIST)
    Observable<HttpRespond<List<CommonNews>>> getCommonNews(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("cid") int cid,
            @Query("page") int page,
            @Query("rows") int rows);

    @GET(ApiFactory.COMMON_NEW_DETAIL)
    Observable<HttpRespond<CommonNewsDetail>> getCommonNewsDetail(
            @Query("client") String client,
            @Query("package") String packageName,
            @Query("ver") String ver,
            @Query("id") int id);

    @POST("center/member/modifyAdd")
    Observable<String> sendAddressAndIP(@Body RequestBody body);

    @POST("Center/Payment/NowPay")
    Observable<String> changJie_sendPaySmsCode(@Body RequestBody body);

    @POST("Center/Payment/NowPayConfirm")
    Observable<String> changJie_commitPay(@Body RequestBody requestBody);

    @POST("Center/Payzenxin/NowPay")
    Observable<String> changJie_sendPaySmsCodeZX(@Body RequestBody body);

    @POST("Center/Payzenxin/NowPayConfirm")
    Observable<String> changJie_commitPayZX(@Body RequestBody requestBody);
}
