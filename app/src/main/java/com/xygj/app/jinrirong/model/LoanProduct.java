package com.xygj.app.jinrirong.model;

import java.util.List;

/**
 * Created by xuyougen on 2018/4/17.
 */

public class LoanProduct {

    /**
     * ID : 7
     * Name : 借贷宝
     * Logurl : http://192.168.1.109:8085/Upload/image/20180413/5ad0460607761.png
     * AppNumbs : 6.6万
     */

    private int ID;
    private String Name;
    private String Logurl;
    private String AppNumbs;
    /**
     * Intro : 24小时现金在线，2分钟下款
     * Jkdays : 7~14天
     * DayfeeRate : 0.15%
     * TypeName : 1001-3000元
     */



    private String Intro;
    private String Jkdays;
    private String DayfeeRate;
    private String TypeName;
    /**
     * PassRate : 98%
     * Downconts : ["a:3:{i:0;s:63:\"备注：非新用户申请不计入结算标准，请知晓。\";i:1;s:63:\"备注：非新用户申请不计入结算标准，请知晓。\";i:2;s:63:\"备注：非新用户申请不计入结算标准，请知晓。\";}"]
     * ConditIDs : ["18-55周岁","实名认证"]
     * NeedIDs : ["芝麻信用","运营商认证"]
     */

    private String PassRate;
    private List<String> Downconts;
    private List<String> ConditIDs;
    private List<String> NeedIDs;

    private String MonthfeeRate;
    private String Ratetype;
    /**
     * Openurl : https://www.cashbus.com/
     */

    private String Openurl;

    public String getMonthfeeRate() {
        return MonthfeeRate;
    }

    public void setMonthfeeRate(String monthfeeRate) {
        MonthfeeRate = monthfeeRate;
    }

    public String getRatetype() {
        return Ratetype;
    }

    public void setRatetype(String ratetype) {
        Ratetype = ratetype;
    }

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

    public String getLogurl() {
        return Logurl;
    }

    public void setLogurl(String Logurl) {
        this.Logurl = Logurl;
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

    public String getJkdays() {
        return Jkdays;
    }

    public void setJkdays(String Jkdays) {
        this.Jkdays = Jkdays;
    }

    public String getDayfeeRate() {
        return DayfeeRate;
    }

    public void setDayfeeRate(String DayfeeRate) {
        this.DayfeeRate = DayfeeRate;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getPassRate() {
        return PassRate;
    }

    public void setPassRate(String PassRate) {
        this.PassRate = PassRate;
    }

    public List<String> getDownconts() {
        return Downconts;
    }

    public void setDownconts(List<String> Downconts) {
        this.Downconts = Downconts;
    }

    public List<String> getConditIDs() {
        return ConditIDs;
    }

    public void setConditIDs(List<String> ConditIDs) {
        this.ConditIDs = ConditIDs;
    }

    public List<String> getNeedIDs() {
        return NeedIDs;
    }

    public void setNeedIDs(List<String> NeedIDs) {
        this.NeedIDs = NeedIDs;
    }

    public String getOpenurl() {
        return Openurl;
    }

    public void setOpenurl(String Openurl) {
        this.Openurl = Openurl;
    }
}
