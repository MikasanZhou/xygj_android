package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yangli on 2018/4/24.
 */

public class ClientListBean {
    @SerializedName("info")
    public List<ClientBean> list;
    @SerializedName("applytotal")
    public String applyTotal;
}
