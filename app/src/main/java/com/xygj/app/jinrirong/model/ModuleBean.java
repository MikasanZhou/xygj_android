package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/5/30.
 */

public class ModuleBean {

    /**
     * ID : 板块的主键id
     * Name : 名称
     * Intro : 简单描述
     * bgUrl : 背景图片
     */

    @SerializedName("ID")
    private String id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Intro")
    private String des;
    @SerializedName("Backurl")
    private String bgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }
}
