package com.xygj.app.jinrirong.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yangli on 2018/4/25.
 */

public class PosterBean {

    /**
     * ID : 1
     * Itype : 1
     * Name : 同城快贷
     * GoodsNo : 20180407
     * Openurl : https://www.cashbus.com/
     * ZsUrl1 : http://jrr.ahceshi.com/Upload/qrcode/19/item_1/ZsUrl1.png
     * ZsUrl2 : http://jrr.ahceshi.com/Upload/qrcode/19/item_1/ZsUrl2.png
     * ZsUrl3 : http://jrr.ahceshi.com/Upload/qrcode/19/item_1/ZsUrl3.png
     * shareurl : http://jrr.ahceshi.com/detail?uid=19&id=1
     */

    @SerializedName("ID")
    private String id;
    @SerializedName("Itype")
    private String type;
    @SerializedName("Name")
    private String name;
    @SerializedName("GoodsNo")
    private String goodsNo;
    @SerializedName("Openurl")
    private String productUrl;
    @SerializedName("ZsUrl1")
    private String posterUrl1;
    @SerializedName("ZsUrl2")
    private String posterUrl2;
    @SerializedName("ZsUrl3")
    private String posterUrl3;
    @SerializedName("shareurl")
    private String shareUrl;

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

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPosterUrl1() {
        return posterUrl1;
    }

    public void setPosterUrl1(String posterUrl1) {
        this.posterUrl1 = posterUrl1;
    }

    public String getPosterUrl2() {
        return posterUrl2;
    }

    public void setPosterUrl2(String posterUrl2) {
        this.posterUrl2 = posterUrl2;
    }

    public String getPosterUrl3() {
        return posterUrl3;
    }

    public void setPosterUrl3(String posterUrl3) {
        this.posterUrl3 = posterUrl3;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
