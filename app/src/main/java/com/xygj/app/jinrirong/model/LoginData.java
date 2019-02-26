package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/16.
 */

public class LoginData {
    @SerializedName("KEY")
    public String key;
    @SerializedName("IV")
    public String iv;
    @SerializedName("Token")
    public String token;
    @SerializedName("ID")
    public int id;
    @SerializedName("Mtype")
    public int type;

    @Override
    public String toString() {
        return "LoginData{" +
                "key='" + key + '\'' +
                ", iv='" + iv + '\'' +
                ", token='" + token + '\'' +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
