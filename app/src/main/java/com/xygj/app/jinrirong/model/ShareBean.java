package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/27.
 */

public class ShareBean {

    /**
     * Title : 分享推广
     * Contents : ""
     * shareUrl : http://jrr.ahceshi.com/Register/index?uid=55
     */

    @SerializedName("Title")
    private String title;
    @SerializedName("Contents")
    private String content;
    @SerializedName("shareurl")
    private String shareUrl;
    public String url2;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
