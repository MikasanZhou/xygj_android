package com.xygj.app.jinrirong.model;

/**
 * Created by xuyougen on 2018/4/25.
 */

public class CommonNews {

    /**
     * ID : 11
     * Title : 瓦尔范德萨
     * AddTime : 2018-04-23 09:52:47
     */

    private int ID;
    private String Title;
    private String AddTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }

    @Override
    public String toString() {
        return Title;
    }
}
