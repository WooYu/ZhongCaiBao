package com.developer.lecai.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.yolanda.nohttp.NoHttp;

import java.util.LinkedList;
import java.util.List;
import com.tencent.bugly.crashreport.CrashReport;

public class XyApplication extends MultiDexApplication {
    private static XyApplication instance;
    public static Context appContext;
    private List<Activity> mlist = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        NoHttp.initialize(instance);
        // 初始化环信
        EMOptions options = new EMOptions();
        EaseUI.getInstance().init(getApplicationContext(), options);
        //初始化XyAppHelper类，并调用其中的方法
        XyAppHelper.getInstance().init(this.getApplicationContext());

        appContext = getApplicationContext();
        //Bugly
        CrashReport.initCrashReport(getApplicationContext(), "eb0f1371b7", false);
    }

    public synchronized static XyApplication getInstance() {
        if (null == instance) {
            instance = new XyApplication();
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void addActivity(Activity activity) {
        if (!mlist.contains(activity)) {
            mlist.add(activity);
        }
    }

    public void exit() {
        for (int i = 0; i < mlist.size(); i++) {
            if (!mlist.get(i).isFinishing()) {
                mlist.get(i).finish();
            }
        }
    }

}
