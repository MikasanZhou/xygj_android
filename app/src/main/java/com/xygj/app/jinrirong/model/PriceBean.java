package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/5/30.
 */

public class PriceBean {

    /**
     * Name : 价格表1
     * Pic : http://jrr.ahceshi.com/Upload/image/20180528/5b0bc80116974.png
     */

    @SerializedName("Name")
    private String name;
    @SerializedName("Pic")
    private String picUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
