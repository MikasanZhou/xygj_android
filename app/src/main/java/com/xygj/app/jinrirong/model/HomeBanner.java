package com.xygj.app.jinrirong.model;

/**
 * Created by xuyougen on 2018/4/17.
 */

public class HomeBanner {

    /**
     * Name : banner1
     * Pic : http://192.168.1.109:8085/Upload/image/20180412/5aceec06b46d9.png
     * Url : http://www.baidu.com
     */

    private String Name;
    private String Pic;
    private String Url;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
}
