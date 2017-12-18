package com.developer.lecai.control;

import android.content.Context;
import android.text.TextUtils;

import com.developer.lecai.app.XyApplication;
import com.developer.lecai.bean.LoginBean;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.SharedPreferencesUtils;

/**
 * Created by raotf on 2017/7/13.
 */

public class UserController {
    public static final String USER_INFO = "user_info";

    private static UserController instance;
    private LoginBean loginBean;

    private UserController() {

    }

    public static UserController getInstance() {
        if(instance == null) {
            synchronized (UserController.class) {
                if(instance == null) {
                    instance = new UserController();
                }
            }
        }

        return instance;
    }

    public void saveUserInfoToShare(String value) {
        LoginBean loginBean=null;
        if (TextUtils.isEmpty(value)){
            SharedPreferencesUtils.putValue(XyApplication.appContext, USER_INFO, "");
            loginBean=null;
        }else {
            SharedPreferencesUtils.putValue(XyApplication.appContext, USER_INFO, value);
            loginBean = JsonUtil.parseJsonToBean(value, LoginBean.class);
        }
        setLoginBean(loginBean);
    }

    /**
     * 从share文件中缓存的用户信息同步
     */
    public void syncUserInfo() {
        String saveUserInfo = SharedPreferencesUtils.getValue(XyApplication.appContext, USER_INFO);
        if(!TextUtils.isEmpty(saveUserInfo)) {
            LoginBean loginBean = JsonUtil.parseJsonToBean(saveUserInfo, LoginBean.class);
            setLoginBean(loginBean);
        }
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoginBean getLoginBean() {
        if(loginBean == null) {
            syncUserInfo();
        }
        return this.loginBean;
    }

    public boolean isLogin() {
        return loginBean == null ? false : true;
    }

    public boolean logout() {
        loginBean = null;
        saveUserInfoToShare("");
        return true;
    }
}
