package com.developer.lecai.http;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.developer.lecai.activity.LoginActivity;
import com.developer.lecai.app.XyApplication;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.TToast;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/12.
 */

public abstract class HttpCallback extends StringCallback {
    Activity activity;

    public HttpCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onError(Call call, Exception e) {
        TToast.show(XyApplication.appContext, "网络请求失败", TToast.TIME_3);
    }

    @Override
    public void onResponse(Call call, String s) {
        //onSuccess(call, s);
        String state = JsonUtil.getStringValue(s, "state");
        Object content = JsonUtil.getObjectValue(s, "biz_content");
        //String biz_content = JsonUtil.getStringValue(s, "biz_content");
        //  LogUtils.e("返回值测试",biz_content+"------------"+state);
        if ("error".equals(state)) {
            String biz_content = JsonUtil.getStringValue(s, "biz_content");
            if (biz_content.contains("账号不存在") || biz_content.contains("您的账号已在其他手机登录") || biz_content.contains("需要登录")) {
                toLogin();
            } else {
                onSuccess(call, s);
            }
        } else {
            if (content instanceof String) {
                Toast.makeText(activity, content.toString(), Toast.LENGTH_SHORT).show();
            }
            onSuccess(call, s);
        }
    }

    private void toLogin() {
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    /* public void onFailure(Call call, String s){
         TToast.show(XyApplication.appContext, "接口调用失败===" + s, TToast.TIME_3);
         Log.e("--", "-----" + s);
     }*/
    public abstract void onSuccess(Call call, String s);
}
