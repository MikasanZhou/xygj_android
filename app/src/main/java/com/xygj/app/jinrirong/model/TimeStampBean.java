package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/16.
 */

public class TimeStampBean {
    @SerializedName("Time")
    public int time;
    @SerializedName("Val")
    public String val;
    @SerializedName("ID")
    public String id;

    @Override
    public String toString() {
        return "TimeStampBean{" +
                "time=" + time +
                ", val='" + val + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
