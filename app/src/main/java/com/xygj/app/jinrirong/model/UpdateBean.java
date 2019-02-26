package com.xygj.app.jinrirong.model;

public class UpdateBean {

    /**
     * ID : 1
     * Ver : 1.1
     * isForced : 2
     * Url : http://www.abc.com/a.apk
     */

//    Ver 版本号  isForced  是否强制更新 1是 2不是    url 下载地址

    private String ID;
    private String Ver;
    private int isForced;
    private String Url;
    private String Updates;

    public String getUpdates() {
        return Updates;
    }

    public void setUpdates(String updates) {
        Updates = updates;
    }

    public boolean isForceUpdate() {
        return isForced == 1;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getVer() {
        return Ver;
    }

    public void setVer(String Ver) {
        this.Ver = Ver;
    }

    public int getIsForced() {
        return isForced;
    }

    public void setIsForced(int isForced) {
        this.isForced = isForced;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
}
