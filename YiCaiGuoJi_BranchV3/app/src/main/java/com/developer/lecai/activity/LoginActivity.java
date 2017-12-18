package com.developer.lecai.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.H;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.http.HttpRequestRegister;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.utils.SPUtils;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.utils.wx.WxShareAndLoginUtils;
import com.developer.lecai.view.TToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPassword;
    private Button btSubmit;
    private TextView tv_reg;
    private TextView tv_forget_pwd;
    private String account;
    private ImageView iv_login_avtor;
    private String password;
    private String signature;
    private TextView tvWxLogin;

    private String mOpenid = "";

    public static final String ACTION_WXLOGINSUCCESS = "com.developer.lecai.wxapi.WXEntryActivity";
    private BroadcastReceiver wxLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ACTION_WXLOGINSUCCESS)) {
                return;
            }
            mOpenid = intent.getStringExtra("openid");
            loginWithWeChat();
        }
    };

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_login, null);
    }

    @Override
    public void initView() {
        iv_login_avtor = (ImageView) findViewById(R.id.iv_login_avtor);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSubmit = (Button) findViewById(R.id.bt_submit);
        tv_reg = (TextView) findViewById(R.id.tv_reg);
        tvWxLogin = (TextView) findViewById(R.id.tv_wxlogin);
        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
        tvTitle.setText("登录");

        hideReturnButton();
    }

    @Override
    public void initListener() {
        btSubmit.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        tvWxLogin.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        SPUtils.set2SP("HOMEDIALOGSHOW", false);
        iv_login_avtor.setImageResource(R.mipmap.icolanuch_v3);

        String name = SharedPreferencesUtils.getValue(LoginActivity.this, "account");
        String psw = SharedPreferencesUtils.getValue(LoginActivity.this, "password");
        etAccount.setText(name);
        etPassword.setText(psw);

        registerThirdLoginBroadcast();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_submit:
                login();
                break;
            case R.id.tv_wxlogin:
                WxShareAndLoginUtils.WxLogin();
            break;
            case R.id.tv_reg:
                toActivity(RegActivity.class);
                break;
            case R.id.tv_forget_pwd:
                toActivity(ForgetPwdActivity.class);
                break;
        }
    }

    public void loginWithWeChat(){
        showWaitDialog();
        new HttpRequestRegister.Builder()
                .addParam(H.Param.account, "")
                .addParam(H.Param.password, "")
                .addParam(H.Param.signature,"")
                .addParam(H.Param.openid,mOpenid)
                .build()
                .post(H.URL.Login, new HttpCallback(LoginActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        // 成功处理
                        // 取出登录信息
                        String state = JsonUtil.getStringValue(s, "state");
                        final String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        Log.e("登录返回", biz_content);
                        if (state.equals("success")) {
                            final String loginname = JsonUtil.getStringValue(biz_content,"loginname");
                            String pswd = "666666";
                            EMClient.getInstance().login(loginname, pswd, new EMCallBack() {// 回调
                                @Override
                                public void onSuccess() {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            UserController userController = UserController.getInstance();
                                            userController.saveUserInfoToShare(biz_content);
                                            SharedPreferencesUtils.putValue(LoginActivity.this, "account", loginname);
                                            SharedPreferencesUtils.putValue(LoginActivity.this, "password", "123456");
                                            hideWaitDialog();
                                            Toast.makeText(getApplicationContext(), "登录聊天服务器成功！", Toast.LENGTH_SHORT).show();
                                            Log.d("main", "登录聊天服务器成功！");
                                            //   SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin", true);
                                            System.out.println("注册号码" + account);
                                            // SharedPreferencesUtils.putValue(getApplicationContext(), "phoneNum", account);
                                            // 取出登录信息
                                            //  toActivity(MainActivity.class, true);
                                            LoginActivity.this.finish();
                                        }
                                    });
                                }

                                @Override
                                public void onProgress(int progress, String status) {

                                }

                                @Override
                                public void onError(int code, String message) {
                                    System.out.println("登录聊天服务器失败" + code);
                                    hideWaitDialog();
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "登录聊天服务器失败！code = " +code, Toast.LENGTH_SHORT).show();
                                    if (code == 200){
                                        /**bug 如果判断返回用户已经登录 那么调用loginout接口  提示用户重新登录*/
                                        Toast.makeText(getApplicationContext(), "正在清理缓存信息，请稍后重新登录..." , Toast.LENGTH_SHORT).show();
                                        logout();
                                        finish();
                                    }
                                    Looper.loop();
                                }
                            });

                        } else if (state.equals("error")) {
                            Toast.makeText(LoginActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                            hideWaitDialog();
                        }
                    }

                });

    }

    private void login() {
        account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            TToast.show(this, "请输入用户名", TToast.TIME_3);
            return;
        }
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            TToast.show(this, "请输入密码", TToast.TIME_3);
            return;
        }
        signature = MyUtil.getSignature(account);
        showWaitDialog();
        new HttpRequestRegister.Builder()
                .addParam(H.Param.account, account)
                .addParam(H.Param.password, password)
                .addParam(H.Param.signature,signature)
                .addParam(H.Param.openid,mOpenid)
                .build()
                .post(H.URL.Login, new HttpCallback(LoginActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        // 成功处理
                        // 取出登录信息
                        String state = JsonUtil.getStringValue(s, "state");
                        final String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        Log.e("登录返回", biz_content);
                        if (state.equals("success")) {

                            EMClient.getInstance().login(account, "666666", new EMCallBack() {// 回调
                                @Override
                                public void onSuccess() {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            UserController userController = UserController.getInstance();
                                            userController.saveUserInfoToShare(biz_content);
                                            SharedPreferencesUtils.putValue(LoginActivity.this, "account", account);
                                            SharedPreferencesUtils.putValue(LoginActivity.this, "password", password);
                                            hideWaitDialog();
                                            Toast.makeText(getApplicationContext(), "登录聊天服务器成功！", Toast.LENGTH_SHORT).show();
                                            Log.d("main", "登录聊天服务器成功！");
                                            //   SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin", true);
                                            System.out.println("注册号码" + account);
                                            // SharedPreferencesUtils.putValue(getApplicationContext(), "phoneNum", account);
                                            // 取出登录信息
                                            //  toActivity(MainActivity.class, true);
                                            LoginActivity.this.finish();
                                        }
                                    });
                                }

                                @Override
                                public void onProgress(int progress, String status) {

                                }

                                @Override
                                public void onError(int code, String message) {
                                    System.out.println("登录聊天服务器失败" + code);
                                    hideWaitDialog();
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "登录聊天服务器失败！code = " +code, Toast.LENGTH_SHORT).show();

                                    Looper.loop();
                                }
                            });

                        } else if (state.equals("error")) {
                            Toast.makeText(LoginActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                            hideWaitDialog();
                        }
                    }

                  /*  @Override
                    public void onFailure(Call call, String s) {

                        TToast.show(XyApplication.appContext, "登录失败", TToast.TIME_3);
                    }*/
                });
    }

    @Override
    protected void onDestroy() {
        unregisterThirdLoginBroadcast();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerThirdLoginBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_WXLOGINSUCCESS);
        registerReceiver(wxLoginReceiver, intentFilter);
    }

    private void unregisterThirdLoginBroadcast(){
        unregisterReceiver(wxLoginReceiver);
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
            //Toast.makeText(this, "退出登录成功", Toast.LENGTH_SHORT).show();
        }
    }
}
