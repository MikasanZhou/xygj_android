package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yangli on 2018/5/15.
 */

public class LoanCateAndLocation {
    @SerializedName("cateList")
    public List<LoanCategory> list;
    @SerializedName("cityname")
    public String cityName;
}
