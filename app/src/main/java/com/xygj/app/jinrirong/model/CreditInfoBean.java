package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 征信信息实体类
 * Created by Yangli on 2018/4/27.
 */

public class CreditInfoBean {

    /**
     * ID : 6
     * UserID : 34
     * OrderSn : 1804259152440169
     * OrderAmount : 0.00
     * TrueName :
     * IDCard : 341623199603205652
     * Mobile : 15056946093
     */

    @SerializedName("ID")
    private String id;
    @SerializedName("UserID")
    private String userId;
    @SerializedName("OrderSn")
    private String orderNo;
    @SerializedName("OrderAmount")
    private String payment;
    @SerializedName("TrueName")
    private String trueName;
    @SerializedName("IDCard")
    private String idCardNo;
    private String Mobile;
    private String detailurl;
    @SerializedName("Status")
    private int status; // 1.待付款   2已付款   3查询失败  4已查询    5已取消

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CreditInfoBean{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", payment='" + payment + '\'' +
                ", trueName='" + trueName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", Mobile='" + Mobile + '\'' +
                '}';
    }
}
