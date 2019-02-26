package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * 是否有新消息
 * Created by Yangli on 2018/5/9.
 */

public class NewMessageBean {

    /**
     * xtmsg : true
     * tzmsg : true
     */

    @SerializedName("xtmsg")
    private boolean newSystemMsg;
    @SerializedName("tzmsg")
    private boolean newNoticeMsg;

    public boolean isNewSystemMsg() {
        return newSystemMsg;
    }

    public void setNewSystemMsg(boolean newSystemMsg) {
        this.newSystemMsg = newSystemMsg;
    }

    public boolean isNewNoticeMsg() {
        return newNoticeMsg;
    }

    public void setNewNoticeMsg(boolean newNoticeMsg) {
        this.newNoticeMsg = newNoticeMsg;
    }
}
