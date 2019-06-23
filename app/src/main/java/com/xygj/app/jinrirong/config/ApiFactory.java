package com.xygj.app.jinrirong.config;

/**
 * Created by Yangli on 2018/4/16.
 */

public class ApiFactory {
    public static final String BASE_URL = "http://rongyijie.xyz/api.php/";
    public static final String PAY_URL = "http://rongyijie.xyz/";

    public static final String GET_CAPTCHA = "core/public/checkimgcode";

    public static final String GET_SMS = "core/tool/getcode";

    public static final String GET_SMS_NO_CODE = "core/tool/getcodeWithNoCode";

    public static final String REGISTER = "center/Register/reg";

    public static final String LOGIN = "center/member/login";

    public static final String GET_TIMESTAMP = "core/tool/timestamp";

    public static final String GET_HOME_BANNER = "home/home/ads";
    public static final String GET_HOME_LOAN_CATEGORY = "home/home/getcate";

    public static final String GET_POP = "home/home/tanimg";

    //获取  单页信息（关于我们，注册协议等）   页面
    public static final String GET_HTML_TEXT = " /News/getpages";

    //获取智能推荐网贷，热门网贷列表
    public static final String GET_HOME_ITEMS = "home/home/getitems";

    //获取 网贷平台 详情
    public static final String GET_LOAN_PRODUCT_DETAIL = "home/items/getdetail";

    public static final String GET_LOAN_PRODUCT_STATISTICS = "center/member/productStatistics";

    //获取 金额类型信息  列表
    public static final String GET_MONEY_TYPE = "home/items/getmoneytype";

    public static final String GET_TERM_TYPE = "Home/items/getjktimes";

    public static final String SIGN_OUT = "center/Member/layout";

    public static final String MY_MESSAGE = "center/member/mynews";

    public static final String MEMBER_INFO = "center/member/info";

    public static final String UPLOAD_HEAD_IMG = "Upload/Upload/index";

    public static final String MODIFY_MEMBER_INFO = "center/member/modify";

    public static final String MODIFY_PWD = "center/Member/change_psd";

    public static final String VERIFY_SMS_CODE = "center/register/forgetOne";

    public static final String RESET_PWD = "center/register/forgetTwo";

    public static final String GET_I_HAVE_TYPE = "home/items/getcate";

    public static final String GET_NEED_TYPE = "home/items/getneed";

    public static final String GET_CREDIT_CARD_RECOMMEND_LIST = "home/Items/getcredit";

    public static final String GET_CREDIT_CARD_DETAIL = "home/items/getcdetail";

    public static final String PROXY_GRADE = "Center/Member/getlevel";

    // 系统公告和常见问题接口
    public static final String PROBLEM_LIST = "Home/News/getnewslist";

    public static final String GET_CREDIT_CARD_TYPE = "Home/items/gettype";

    public static final String REC_PRODUCT_LISTS = "Home/Loan/getrec";


    public static final String PRODUCT_LIST = "Home/Loan/getproduct";

    public static final String CLIENT_LIST = "Home/Home/getclient";

    public static final String MONEY_DATA = "center/member/getwallet";

    public static final String GET_CREDIT_CARD_BANK_LIST = "Home/Items/getbank";

    public static final String SUBMIT_WITHDRAW = "center/member/draw";

    public static final String PROMOTE_INFO = "center/member/getpromote";

    public static final String PROMOTE_POSTER = "center/member/getposter";

    public static final String GET_CREDIT_CARD_BY = "Home/Items/getclist";

    public static final String GET_LOAN_PRODUCT_LIST_BY = "Home/Items/getitems";

    public static final String QUERY_CREDIT = "center/member/submitinfo";

    public static final String GET_RONG_KE_INFO = "center/member/rongke";

    public static final String QUERY_HISTORY = "center/member/getcredibility";

    public static final String COMMON_NEW_DETAIL = "Home/News/getnotice";

    public static final String PROMOTE_INCOME = "center/member/makeincome";

    public static final String USER_INCOME = "center/member/referincome";

    public static final String CREDIT_INFO = "center/member/getdetail";

    public static final String CREDIT_INFO_PAY = "center/member/orderpay";

    public static final String PROXY_PAY = "center/member/payagent";

    public static final String SHARE_RECOMMEND = "center/member/share";

    public static final String MARK_MESSAGE = "center/member/setreads";

    public static final String NEW_MESSAGE = "center/member/isnoreadmsg";

    public static final String PRICE_LIST = "Home/News/pricelist";

    public static final String HELP_LIST = "Home/News/newerhelps";

    public static final String MEMBER_MODULE = "center/member/bankuais";
}
