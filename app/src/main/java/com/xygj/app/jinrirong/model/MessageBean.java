package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 消息类型和列表实体类
 * Created by Yangli on 2018/3/20.
 */

public class MessageBean {
    @SerializedName("Type")
    public int type; // 0 系统消息 1 通知消息
    @SerializedName("ID")
    public int id; // 消息类型时作为图片的资源
    @SerializedName("Title")
    public String title; // 消息类型时作为类型名称
    @SerializedName("Contents")
    public String contents;
    @SerializedName("SendTime")
    public String time;
    @SerializedName("Status")
    public int status;
    public String jsonStr;
}
