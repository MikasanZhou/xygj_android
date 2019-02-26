package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/9.
 */

public class ProblemBean {
    @SerializedName("ID")
    public int id;
    @SerializedName("Title")
    public String title;
    @SerializedName("Contents")
    public String content;
}
