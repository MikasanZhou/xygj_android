package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HttpRespond<T> implements Serializable {

    @SerializedName("result")
    public int result;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public T data;
}
