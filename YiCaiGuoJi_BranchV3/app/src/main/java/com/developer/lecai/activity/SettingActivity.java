package com.developer.lecai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.H;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.http.HttpRequest;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.view.TToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/5.
 */

public class SettingActivity extends BaseActivity {

    private View view;
    private RelativeLayout rl_setting_psw;
    private RelativeLayout rl_setting_zjpsw;
    private RelativeLayout rl_setting_kefu;
    private RelativeLayout rl_setting_help;
    private RelativeLayout rl_setting_our;
    private TextView tv_setting_outload;

    @Override
    public View getLayout() {
        view = View.inflate(SettingActivity.this, R.layout.activity_setting, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("设置");
        rl_setting_psw = (RelativeLayout) view.findViewById(R.id.rl_setting_psw);
        rl_setting_zjpsw = (RelativeLayout) view.findViewById(R.id.rl_setting_zjpsw);
        rl_setting_kefu = (RelativeLayout) view.findViewById(R.id.rl_setting_kefu);
        rl_setting_help = (RelativeLayout) view.findViewById(R.id.rl_setting_help);
        rl_setting_our = (RelativeLayout) view.findViewById(R.id.rl_setting_our);
        tv_setting_outload = (TextView) view.findViewById(R.id.tv_setting_outload);


    }

    @Override
    public void initListener() {
        rl_setting_psw.setOnClickListener(this);
        rl_setting_zjpsw.setOnClickListener(this);
        rl_setting_kefu.setOnClickListener(this);
        rl_setting_help.setOnClickListener(this);
        rl_setting_our.setOnClickListener(this);
        tv_setting_outload.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.rl_setting_psw:
                intent.setClass(SettingActivity.this, LoadPswActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_zjpsw:
                intent.setClass(SettingActivity.this, ZiJinPswActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_kefu:
                intent.setClass(SettingActivity.this, KeFuActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_help:
                intent.setClass(SettingActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_our:
                intent.setClass(SettingActivity.this, OurActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_setting_outload:
                logout();
                finish();
                break;
        }

    }

    private void logout() {

        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                LogUtils.e("退出环信","退出环信成功");
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

        if(userController.logout()) {
           // toActivity(LoginActivity.class);
            Toast.makeText(this, "退出登录成功", Toast.LENGTH_SHORT).show();
        }
    }
}
