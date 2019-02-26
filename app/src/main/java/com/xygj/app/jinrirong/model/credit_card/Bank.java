package com.xygj.app.jinrirong.model.credit_card;

/**
 * Created by xuyougen on 2018/4/24.
 */

public class Bank {

    /**
     * ID : 10
     * BankName : 中信银行
     * Logurl : http://jrr.ahceshi.com/Upload/image/20180416/5ad4676660f13.png
     * Intro : 5分钟极速办卡
     */

    private int ID;
    private String BankName;
    private String Logurl;
    private String Intro;
    private String Desc; // 漂浮文字

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getLogurl() {
        return Logurl;
    }

    public void setLogurl(String Logurl) {
        this.Logurl = Logurl;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String Intro) {
        this.Intro = Intro;
    }
}
