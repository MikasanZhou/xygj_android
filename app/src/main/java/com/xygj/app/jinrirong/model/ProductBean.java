package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 贷款分销产品实体类
 * Created by Yangli on 2018/4/23.
 */

public class ProductBean {
    @SerializedName("ID")
    public int id;
    @SerializedName("Name")
    public String name;
    @SerializedName("Logurl")
    public String picUrl;
    @SerializedName("IsRec")
    public int isRecommend; // 1 是推荐
    @SerializedName("BonusRate")
    public String bonusRate; // type==1 个点
    @SerializedName("Yjtype")
    public int type; // 1按比例 2按金额
    @SerializedName("Ymoney")
    public String money; // type==2 元
    @SerializedName("Smoney")
    public String smoney; // 申请返佣
    @SerializedName("Intro")
    public String des;
    @SerializedName("AppNumbs")
    public String num;
}
