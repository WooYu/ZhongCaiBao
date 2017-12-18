package com.developer.lecai.bean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/29.
 */

public class PeiLvBean {


    /**
     * pname : 大小单双
     * pids : 128_2dxds
     * playinfo :
     * childplays : [{"pname":"大","pids":"128_2dxds_3da","playinfo":"14,15,16,17,18,19,20,21,22,23,24,25,26,27","childplays":null,"content":"2","spacerate":""},{"pname":"小","pids":"128_2dxds_3xiao","playinfo":"0,1,2,3,4,5,6,7,8,9,10,11,12,13","childplays":null,"content":"2","spacerate":""},{"pname":"单","pids":"128_2dxds_3dan","playinfo":"1,3,5,7,9,11,13,15,17,19,21,23,25,27","childplays":null,"content":"2","spacerate":""},{"pname":"双","pids":"128_2dxds_3shuang","playinfo":"0,2,4,6,8,10,12,14,16,18,20,22,24,26","childplays":null,"content":"2","spacerate":""},{"pname":"大单","pids":"128_2dxds_3dadan","playinfo":"15,17,19,21,23,25,27","childplays":null,"content":"4.2","spacerate":""},{"pname":"小单","pids":"128_2dxds_3xdan","playinfo":"1,3,5,7,9,11,13","childplays":null,"content":"4.6","spacerate":""},{"pname":"大双","pids":"128_2dxds_3dshuang","playinfo":"14,16,18,20,22,24,26","childplays":null,"content":"4.6","spacerate":""},{"pname":"小双","pids":"128_2dxds_3xshuang","playinfo":"0,2,4,6,8,10,12","childplays":null,"content":"4.2","spacerate":""},{"pname":"极大","pids":"128_2dxds_3jida","playinfo":"22,23,24,25,26,27","childplays":null,"content":"15","spacerate":""},{"pname":"极小","pids":"128_2dxds_3jixiao","playinfo":"0,1,2,3,4,5","childplays":null,"content":"15","spacerate":""}]
     * content :
     * spacerate :
     */

    private String pname;
    private String pids;
    private String playinfo;
    private String content;
    private String spacerate;
    private List<ChildplaysBean> childplays;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getPlayinfo() {
        return playinfo;
    }

    public void setPlayinfo(String playinfo) {
        this.playinfo = playinfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpacerate() {
        return spacerate;
    }

    public void setSpacerate(String spacerate) {
        this.spacerate = spacerate;
    }

    public List<ChildplaysBean> getChildplays() {
        return childplays;
    }

    public void setChildplays(List<ChildplaysBean> childplays) {
        this.childplays = childplays;
    }

    public static class ChildplaysBean {
        /**
         * pname : 大
         * pids : 128_2dxds_3da
         * playinfo : 14,15,16,17,18,19,20,21,22,23,24,25,26,27
         * childplays : null
         * content : 2
         * spacerate :
         */

        private String pname;
        private String pids;
        private String playinfo;
        private Object childplays;
        private String content;
        private String spacerate;

        @Override
        public String toString() {
            return "ChildplaysBean{" +
                    "pname='" + pname + '\'' +
                    ", pids='" + pids + '\'' +
                    ", playinfo='" + playinfo + '\'' +
                    ", childplays=" + childplays +
                    ", content='" + content + '\'' +
                    ", spacerate='" + spacerate + '\'' +
                    '}';
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPids() {
            return pids;
        }

        public void setPids(String pids) {
            this.pids = pids;
        }

        public String getPlayinfo() {
            return playinfo;
        }

        public void setPlayinfo(String playinfo) {
            this.playinfo = playinfo;
        }

        public Object getChildplays() {
            return childplays;
        }

        public void setChildplays(Object childplays) {
            this.childplays = childplays;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSpacerate() {
            return spacerate;
        }

        public void setSpacerate(String spacerate) {
            this.spacerate = spacerate;
        }
    }

    @Override
    public String toString() {
        return "PeiLvBean{" +
                "pname='" + pname + '\'' +
                ", pids='" + pids + '\'' +
                ", playinfo='" + playinfo + '\'' +
                ", content='" + content + '\'' +
                ", spacerate='" + spacerate + '\'' +
                ", childplays=" + childplays +
                '}';
    }
}
