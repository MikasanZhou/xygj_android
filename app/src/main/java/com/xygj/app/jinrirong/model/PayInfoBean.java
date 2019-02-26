package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 支付信息
 * Created by Yangli on 2018/5/2.
 */

public class PayInfoBean {
    public String id;
    @SerializedName("oid")
    public String orderId;
}
