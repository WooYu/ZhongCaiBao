package com.developer.lecai.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-08-24.
 * 六合彩游戏类型
 */

public class MarkSixCategoryBean {
    /**
     * pname : 特码a
     * pids : 1tema_2a
     * playinfo : 香港六合彩公司当期开出的最后一号码为特别号码（或称特码）假设投注号码为开奖号码之特别号，视为中奖，其余情形视为不中奖
     * childplays : [{"pname":"1","pids":"1tema_2a_31","playinfo":"","childplays":null,"content":"42.52","spacerate":""}]
     */

    private String pname;
    private String pids;
    private String playinfo;
    private List<MarkSixBallTypeBean> childplays;
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

    public List<MarkSixBallTypeBean> getChildplays() {
        return childplays;
    }

    public void setChildplays(List<MarkSixBallTypeBean> childplays) {
        this.childplays = childplays;
    }

}
