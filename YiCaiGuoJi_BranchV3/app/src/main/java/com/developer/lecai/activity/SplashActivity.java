package com.developer.lecai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.developer.lecai.R;
import com.developer.lecai.control.UserController;


/**
 * Created by liuwei on 2017/08/26.
 */
public class SplashActivity extends Activity {
    private int i = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (i > 0) {
                        i--;
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        handler.sendEmptyMessage(1);
                    }

                    break;
                case 1:
                   /* Intent intent = null;
                    if ((Boolean) CommonUtil.get4SP("isfirst", true)) {
                        CommonUtil.set2SP("isfirst", false);
                        intent = new Intent(SplashActivity.this, GuideActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }*/
                   //if (UserController.getInstance().getLoginBean()!=null){
                       Intent intent = null;
                       intent = new Intent(SplashActivity.this, MainActivity.class);
                       startActivity(intent);
                  /* }else {
                       Intent intent = null;
                       intent = new Intent(SplashActivity.this, LoginActivity.class);
                       startActivity(intent);
                   }*/
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessage(0);
    }

}
