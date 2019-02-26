package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/26.
 */

public class IncomeBean {

    /**
     * Amount : 推广收入
     * UpdateTime : 推广的会员贷款或查征信的时间
     * Mobile : 推广会员的手机号码
     * name : 贷款项目的名称或被查征信人的真实姓名
     * headImgUrl 会员收入时显示
     */

    @SerializedName("Amount")
    private String income;
    @SerializedName("UpdateTime")
    private String time;
    @SerializedName("Mobile")
    private String accountNo;
    @SerializedName("Name")
    private String name;
    @SerializedName("HeadImg")
    private String headImgUrl;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }
}
