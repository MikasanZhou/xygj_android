package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 我的钱包实体类
 * Created by Yangli on 2018/4/24.
 */

public class MoneyInfoList {
    @SerializedName("income")
    public String totalIncome;
    @SerializedName("balances")
    public String balance;
    @SerializedName("cost")
    public String cost; // 手续费百分比
    @SerializedName("info")
    public List<WithdrawBean> list;
}
