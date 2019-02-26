package com.xygj.app.jinrirong.model;

import java.util.List;

/**
 * Created by xuyougen on 2018/4/23.
 */

public class CreditCardDetail {

    /**
     * MainPic : http://192.168.1.109:8085/Upload/image/20180417/5ad59d120283c.jpg
     * Quanyconts : ["权益1","权益2"]
     * yarfeename : 1000元/年
     * Yeardesc : 讽德诵功
     * Jifen1 : RMB ￥1=1积分
     * Jifen2 : USD $1=21积分
     * Freetime : 30天
     * Freedesc : 最低免息30天
     * Levelname : 白金卡
     * Fakaurl : http://192.168.1.109:8085/Upload/image/20180408/5ac9eb38a5337.png
     * Openurl : https://www.cashbus.com/
     */

    private String MainPic;
    private String yarfeename;
    private String Yeardesc;
    private String Jifen1;
    private String Jifen2;
    private String Freetime;
    private String Freedesc;
    private String Levelname;
    private String Fakaurl;
    private String Openurl;
    private List<String> Quanyconts;

    public String getMainPic() {
        return MainPic;
    }

    public void setMainPic(String MainPic) {
        this.MainPic = MainPic;
    }

    public String getYarfeename() {
        return yarfeename;
    }

    public void setYarfeename(String yarfeename) {
        this.yarfeename = yarfeename;
    }

    public String getYeardesc() {
        return Yeardesc;
    }

    public void setYeardesc(String Yeardesc) {
        this.Yeardesc = Yeardesc;
    }

    public String getJifen1() {
        return Jifen1;
    }

    public void setJifen1(String Jifen1) {
        this.Jifen1 = Jifen1;
    }

    public String getJifen2() {
        return Jifen2;
    }

    public void setJifen2(String Jifen2) {
        this.Jifen2 = Jifen2;
    }

    public String getFreetime() {
        return Freetime;
    }

    public void setFreetime(String Freetime) {
        this.Freetime = Freetime;
    }

    public String getFreedesc() {
        return Freedesc;
    }

    public void setFreedesc(String Freedesc) {
        this.Freedesc = Freedesc;
    }

    public String getLevelname() {
        return Levelname;
    }

    public void setLevelname(String Levelname) {
        this.Levelname = Levelname;
    }

    public String getFakaurl() {
        return Fakaurl;
    }

    public void setFakaurl(String Fakaurl) {
        this.Fakaurl = Fakaurl;
    }

    public String getOpenurl() {
        return Openurl;
    }

    public void setOpenurl(String Openurl) {
        this.Openurl = Openurl;
    }

    public List<String> getQuanyconts() {
        return Quanyconts;
    }

    public void setQuanyconts(List<String> Quanyconts) {
        this.Quanyconts = Quanyconts;
    }
}
