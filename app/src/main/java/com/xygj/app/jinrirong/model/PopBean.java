package com.xygj.app.jinrirong.model;

/**
 * 首页弹窗信息实体类
 * Created by Yangli on 2018/5/3.
 */

public class PopBean {

    /**
     * YouTan : {"YtanImg":"http://localhost:8085/Upload/image/20180528/5b0b769cd98a5.png","YtanUrl":"http://www.baidu.com"}
     * BigTan : {"TanImg":"http://localhost:8085/Upload/image/20180702/5b3998ca1455f.png","TanUrl":"http://www.baidu.com"}
     */

    private YouTanBean YouTan;
    private BigTanBean BigTan;

    public YouTanBean getYouTan() {
        return YouTan;
    }

    public void setYouTan(YouTanBean YouTan) {
        this.YouTan = YouTan;
    }

    public BigTanBean getBigTan() {
        return BigTan;
    }

    public void setBigTan(BigTanBean BigTan) {
        this.BigTan = BigTan;
    }

    public static class YouTanBean {
        /**
         * YtanImg : http://localhost:8085/Upload/image/20180528/5b0b769cd98a5.png
         * YtanUrl : http://www.baidu.com
         */

        private String YtanImg;
        private String YtanUrl;

        public String getYtanImg() {
            return YtanImg;
        }

        public void setYtanImg(String YtanImg) {
            this.YtanImg = YtanImg;
        }

        public String getYtanUrl() {
            return YtanUrl;
        }

        public void setYtanUrl(String YtanUrl) {
            this.YtanUrl = YtanUrl;
        }
    }

    public static class BigTanBean {
        /**
         * TanImg : http://localhost:8085/Upload/image/20180702/5b3998ca1455f.png
         * TanUrl : http://www.baidu.com
         */

        private String TanImg;
        private String TanUrl;
        private int Tcstatus;

        public int getTcstatus() {
            return Tcstatus;
        }

        public void setTcstatus(int tcstatus) {
            Tcstatus = tcstatus;
        }

        public String getTanImg() {
            return TanImg;
        }

        public void setTanImg(String TanImg) {
            this.TanImg = TanImg;
        }

        public String getTanUrl() {
            return TanUrl;
        }

        public void setTanUrl(String TanUrl) {
            this.TanUrl = TanUrl;
        }
    }
}
