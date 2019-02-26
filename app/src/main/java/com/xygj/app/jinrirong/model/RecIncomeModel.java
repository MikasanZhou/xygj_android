package com.xygj.app.jinrirong.model;

public class RecIncomeModel {

    /**
     * OrderSn : 1806117161546744
     * Mobile : 173****8830
     * Addtime : 2018-06-11 16:15:46
     * Yjtype : 2
     * BonusRate : null
     * Ymoney : 3.00
     * Status : 0
     * Bonus : 0.00
     * goodname : 建设银行信用卡
     * Logurl : http://jrr.ahceshi.com/Upload/image/20180517/5afce73d540f7.jpg
     * Settletime : 半个月
     */

    private String OrderSn;
    private String Mobile;
    private String Addtime;
    private int Yjtype;
    private Object BonusRate;
    private String Ymoney;
    private int Status;
    private String Bonus;
    private String goodname;
    private String Logurl;
    private String Settletime;
    private String applyBonus;

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String OrderSn) {
        this.OrderSn = OrderSn;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getAddtime() {
        return Addtime;
    }

    public void setAddtime(String Addtime) {
        this.Addtime = Addtime;
    }

    public int getYjtype() {
        return Yjtype;
    }

    public void setYjtype(int Yjtype) {
        this.Yjtype = Yjtype;
    }

    public Object getBonusRate() {
        return BonusRate;
    }

    public void setBonusRate(Object BonusRate) {
        this.BonusRate = BonusRate;
    }

    public String getYmoney() {
        return Ymoney;
    }

    public void setYmoney(String Ymoney) {
        this.Ymoney = Ymoney;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getBonus() {
        return Bonus;
    }

    public void setBonus(String Bonus) {
        this.Bonus = Bonus;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getLogurl() {
        return Logurl;
    }

    public void setLogurl(String Logurl) {
        this.Logurl = Logurl;
    }

    public String getSettletime() {
        return Settletime;
    }

    public void setSettletime(String Settletime) {
        this.Settletime = Settletime;
    }

    public String getApplyBonus() {
        return applyBonus;
    }

    public void setApplyBonus(String applyBonus) {
        this.applyBonus = applyBonus;
    }
}
