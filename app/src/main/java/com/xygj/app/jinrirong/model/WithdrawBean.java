package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 提现记录实体类
 * Created by Yangli on 2018/4/24.
 */

public class WithdrawBean {
    @SerializedName("ID")
    public String id;
    @SerializedName("Money")
    public String money;
    @SerializedName("CurlMoney")
    public String currentMoney;
    @SerializedName("AddTime")
    public String time;
}
