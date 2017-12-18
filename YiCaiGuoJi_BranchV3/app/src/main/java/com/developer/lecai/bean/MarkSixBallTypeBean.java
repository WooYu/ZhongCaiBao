package com.developer.lecai.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-08-24.
 * 六合彩游戏类型下选注种类
 */

public class MarkSixBallTypeBean {

    /**
     * pname : 特码a
     * pids : 1tema_2a
     * playinfo : 香港六合彩公司当期开出的最后一号码为特别号码（或称特码）假设投注号码为开奖号码之特别号，视为中奖，其余情形视为不中奖
     * childplays : [{"pname":"1","pids":"1tema_2a_31","playinfo":"","childplays":null,"content":"42.52","spacerate":""}]
     */

    private String pname;
    private String pids;
    private String playinfo;
    private List<ChildplaysBean> childplays;
    private boolean pitchon;//是否被选中
    private String content;
    private String spacerate;

    public String getSpacerate() {
        return spacerate;
    }

    public void setSpacerate(String spacerate) {
        this.spacerate = spacerate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPitchon() {
        return pitchon;
    }

    public void setPitchon(boolean pitchon) {
        this.pitchon = pitchon;
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

    public List<ChildplaysBean> getChildplays() {
        return childplays;
    }

    public void setChildplays(List<ChildplaysBean> childplays) {
        this.childplays = childplays;
    }

    public static class ChildplaysBean {
        /**
         * pname : 1
         * pids : 1tema_2a_31
         * playinfo :
         * childplays : null
         * content : 42.52
         * spacerate :
         */

        private String pname;
        private String pids;
        private String playinfo;
        private Object childplays;
        private String content;
        private String spacerate;

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
}
