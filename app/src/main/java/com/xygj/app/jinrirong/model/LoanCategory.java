package com.xygj.app.jinrirong.model;

/**
 * 首页 - 贷款分类
 * Created by xuyougen on 2018/4/17.
 */

public class LoanCategory {

    /**
     * ID : 1
     * Name : 手机号贷
     * Imageurl : http://192.168.1.109:8085/Upload/image/20180412/5acef016ecf22.png
     */

    private int ID;
    private String Name;
    private String Imageurl;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String Imageurl) {
        this.Imageurl = Imageurl;
    }
}
