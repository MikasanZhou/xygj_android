package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 产品推广信息实体类
 * Created by Yangli on 2018/4/25.
 */

public class RankListBean {
    @SerializedName("lists")
    public List<RankBean> list;
    @SerializedName("iteminfo")
    public ProductInfo productInfo;

    public class ProductInfo {

        /**
         * ID : 1
         * Itype : 1
         * Name : 同城快贷
         * GoodsNo : 20180407
         * Logurl : http://jrr.ahceshi.com/Upload/image/20180407/5ac87bcc73710.png
         * Dates : 2018年03月
         */

        @SerializedName("ID")
        private String id;
        @SerializedName("Itype")
        private String type;
        @SerializedName("Name")
        private String name;
        @SerializedName("GoodsNo")
        private String goodsNo;
        @SerializedName("Logurl")
        private String picUrl;
        @SerializedName("Dates")
        private String date;
        /**
         * PartType : ["[结算]，每月初的前五个工作日开始结算。","首次注册申请借贷符合结算标准。","备注：非新用户申请不计入结算标准，请知晓。"]
         * FeeIntro : ["阶梯工资=100×10=1000元","所以您4月工资=7000+1000=8000元"]
         * BaseFee : 基本工资：邀请成功发卡张数×70元
         * StepFee : 阶梯工资：当月累计发卡张数×对应工资
         * StepBase : 700.00
         * StepInc1 : 10.00
         * StepInc2 : 30.00
         * StepUnit : 元
         * StepIntro : ["0张至50张 ","50张至300张2","大于300张"]
         * AccountType : ["每三十天","浮利结算","发放奖金不延迟"]
         * AccountFee1 : 710
         * AccountFee2 : 730
         */

        private String BaseFee;         //基本工资介绍
        private String StepFee;         //阶梯工资介绍
        private double StepBase;        //基础值
        private double StepInc1;        //增长值1
        private double StepInc2;        //增长值2
        private String StepUnit;        //值单位
        private double AccountFee1;     //基础值与增长值1的和
        private double AccountFee2;     //基础值与增长值2的和
        private List<String> PartType;  //参与方式
        private List<String> FeeIntro;  //工资介绍
        private List<String> StepIntro;     //阶梯值描述
        private List<String> AccountType;   //结算方式


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBaseFee() {
            return BaseFee;
        }

        public void setBaseFee(String BaseFee) {
            this.BaseFee = BaseFee;
        }

        public String getStepFee() {
            return StepFee;
        }

        public void setStepFee(String StepFee) {
            this.StepFee = StepFee;
        }

        public double getStepBase() {
            return StepBase;
        }

        public void setStepBase(double StepBase) {
            this.StepBase = StepBase;
        }

        public double getStepInc1() {
            return StepInc1;
        }

        public void setStepInc1(double StepInc1) {
            this.StepInc1 = StepInc1;
        }

        public double getStepInc2() {
            return StepInc2;
        }

        public void setStepInc2(double StepInc2) {
            this.StepInc2 = StepInc2;
        }

        public String getStepUnit() {
            return StepUnit;
        }

        public void setStepUnit(String StepUnit) {
            this.StepUnit = StepUnit;
        }

        public double getAccountFee1() {
            return AccountFee1;
        }

        public void setAccountFee1(double AccountFee1) {
            this.AccountFee1 = AccountFee1;
        }

        public double getAccountFee2() {
            return AccountFee2;
        }

        public void setAccountFee2(double AccountFee2) {
            this.AccountFee2 = AccountFee2;
        }

        public List<String> getPartType() {
            return PartType;
        }

        public void setPartType(List<String> PartType) {
            this.PartType = PartType;
        }

        public List<String> getFeeIntro() {
            return FeeIntro;
        }

        public void setFeeIntro(List<String> FeeIntro) {
            this.FeeIntro = FeeIntro;
        }

        public List<String> getStepIntro() {
            return StepIntro;
        }

        public void setStepIntro(List<String> StepIntro) {
            this.StepIntro = StepIntro;
        }

        public List<String> getAccountType() {
            return AccountType;
        }

        public void setAccountType(List<String> AccountType) {
            this.AccountType = AccountType;
        }
    }
}
