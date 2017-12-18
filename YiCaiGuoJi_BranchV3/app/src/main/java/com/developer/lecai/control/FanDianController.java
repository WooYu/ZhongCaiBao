package com.developer.lecai.control;

import android.text.TextUtils;

import com.developer.lecai.app.XyApplication;
import com.developer.lecai.bean.FanDianBean;
import com.developer.lecai.bean.LoginBean;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by raotf on 2017/7/13.
 */

public class FanDianController {
    private static FanDianController instance;
    private List<FanDianBean> fanDianList;

    private FanDianController() {

    }

    public static FanDianController getInstance() {
        if(instance == null) {
            synchronized (FanDianController.class) {
                if(instance == null) {
                    instance = new FanDianController();
                }
            }
        }

        return instance;
    }


    public void setFanDianBean(List<FanDianBean> fanDianList) {
        this.fanDianList = fanDianList;
    }

    public List<FanDianBean> getFanDianBean() {
        return this.fanDianList;
    }

}
