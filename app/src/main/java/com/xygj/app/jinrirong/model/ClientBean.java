package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 客户相关的产品实体类
 * Created by Yangli on 2018/4/24.
 */

public class ClientBean {
    @SerializedName("ID")
    public String id;
    @SerializedName("Itype")
    public String type;
    @SerializedName("Name")
    public String name;
    @SerializedName("GoodsNo")
    public String productNo;
    @SerializedName("applycount")
    public String applyNum;
    @SerializedName("fksuccess")
    public String successNum;
    @SerializedName("BonusAll")
    public String bonus;
}
