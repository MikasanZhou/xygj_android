package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户信息实体类
 * Created by Yangli on 2018/4/19.
 */

public class MemberInfoBean {
    @SerializedName("ID")
    public int id;
    @SerializedName("TrueName")
    public String trueName;
    @SerializedName("Mtype")
    public int type; // 1普通会员  2渠道代理  3团队经理  4城市经理
    @SerializedName("Mobile")
    public String mobile;
    @SerializedName("Rule")
    public String rule;
    @SerializedName("HeadImgVal")
    public String headImgUrl;
    @SerializedName("Account")
    public String account; // 已结算金额
    @SerializedName("Income")
    public String income; // 总收入金额
    @SerializedName("Balance")
    public String balance; // 可结算金额
    @SerializedName("severTel")
    public String severTel;
    @SerializedName("wechatKefu")
    public String wechatKefu;//微信客服
    @SerializedName("wechatQR")
    public String wechatQR;//公众号二维码
    @SerializedName("CardNo")
    public String cardNo;
    @SerializedName("Tjcode")
    public String recommendCode;
    @SerializedName("UseTime")
    public String useTime;
    @SerializedName("IsCredit")
    public String isCredit;
    @SerializedName("Referee")
    public String referee;
    @SerializedName("Position")
    public String position;
}
