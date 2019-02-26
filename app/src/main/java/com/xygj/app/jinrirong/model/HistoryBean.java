package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/25.
 */

public class HistoryBean {
    /**
     * ID : 征信列表ID
     * TrueName : 真实姓名
     * Mobile : 手机号码
     * Checktime : 查询时间
     * Status : 查询状态
     */

    @SerializedName("ID")
    private String id;
    @SerializedName("TrueName")
    private String trueName;
    @SerializedName("Mobile")
    private String phoneNum;
    @SerializedName("Checktime")
    private String checkTime;
    private String Status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
