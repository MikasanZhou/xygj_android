package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 查征信结果实体类
 * Created by Yangli on 2018/4/25.
 */

public class QueryResultBean {

    /**
     * ZxPay : 征信支付金额
     * ID : 征信提交列表的ID
     */

    @SerializedName("ZxPay")
    private String payment;
    private String ID;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
