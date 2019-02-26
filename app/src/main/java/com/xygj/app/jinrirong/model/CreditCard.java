package com.xygj.app.jinrirong.model;

/**
 * Created by xuyougen on 2018/4/23.
 */

public class CreditCard {


    /**
     * ID : 3
     * Name : 龙腾信用卡
     * AppNumbs : 2万人
     * Intro : 24小时现金在线，2分钟下款
     * Logurl : http://jrr.ahceshi.com/Upload/image/20180416/5ad46f1c72fca.jpg
     * BankName : 中国银行
     */

    private int ID;
    private String Name;
    private String AppNumbs;
    private String Intro;
    private String Logurl;
    private String BankName;

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

    public String getAppNumbs() {
        return AppNumbs;
    }

    public void setAppNumbs(String AppNumbs) {
        this.AppNumbs = AppNumbs;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String Intro) {
        this.Intro = Intro;
    }

    public String getLogurl() {
        return Logurl;
    }

    public void setLogurl(String Logurl) {
        this.Logurl = Logurl;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }
}
