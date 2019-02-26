package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 产品推广排行榜实体类
 * Created by Yangli on 2018/4/25.
 */

public class RankBean {

    /**
     * ID : 3
     * Mobile : 136****4652
     * Money : 3000.00元
     */
    @SerializedName("ID")
    public String id;
    @SerializedName("Mobile")
    public String phoneNum;
    @SerializedName("Money")
    public String money;
}
