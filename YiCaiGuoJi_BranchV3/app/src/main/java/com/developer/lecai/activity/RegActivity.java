package com.developer.lecai.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.app.XyApplication;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.H;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.http.HttpRequestRegister;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.view.TToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import okhttp3.Call;

public class RegActivity extends BaseActivity {

    private EditText et_account;
    private EditText et_password;
    private EditText et_verify_code;
    private TextView tv_verify_code;
    private Button bt_submit;
    private TextView tv_login;
    private String account;
    private Button et_verify_send;
    private String code;
    private UserController userController ;
    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_reg, null);
    }

    @Override
    public void initView() {
        tvTitle.setText("注册");
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        et_verify_code = (EditText) findViewById(R.id.et_verify_code);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        tv_login = (TextView) findViewById(R.id.tv_login);
        et_verify_send = (Button) findViewById(R.id.et_verify_send);
        userController = UserController.getInstance();
    }

    @Override
    public void initListener() {
        bt_submit.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        et_verify_send.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_submit:
                regist();
                break;
            case R.id.tv_login:
                finish();
                break;
            case R.id.et_verify_send:
                sendVerify();
                break;
        }
    }

    private void sendVerify() {
        account = et_account.getText().toString();
        if(TextUtils.isEmpty(account)) {
            TToast.show(this, "请输入用户名", TToast.TIME_3);
            return;
        }
        code = ((int)(Math.random()*9000)+1000)+"";
        Log.e("验证码", "code=" +  code);
        String signature=MyUtil.getSignature(account);
        new HttpRequestRegister.Builder()
                .addParam(H.Param.account, account)
                .addParam(H.Param.code, code)
                .addParam(H.Param.type, "注册应用")
                .addParam(H.Param.imei,MyUtil.getIMEI(XyApplication.appContext))
                .addParam(H.Param.signature, signature)
                .build()
                .post(H.URL.CODEURL, new HttpCallback(RegActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        // 成功处理
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s,"biz_content");
                        Log.e("验证码",biz_content);
                        if (state.equals("error")){
                            TToast.show(RegActivity.this,biz_content,TToast.TIME_2);
                        }
                    }
                });
    }

    private void regist() {
        account = et_account.getText().toString();
        if(TextUtils.isEmpty(account)) {
            TToast.show(this, "请输入用户名", TToast.TIME_3);
            return;
        }
        String password = et_password.getText().toString();
        if(TextUtils.isEmpty(password)) {
            TToast.show(this, "请输入密码", TToast.TIME_3);
            return;
        }

        String verifyCode = et_verify_code.getText().toString();
        if(TextUtils.isEmpty(verifyCode)) {
            TToast.show(this, "请输入验证码", TToast.TIME_3);
            return;
        }

        if (!verifyCode.equals(code)){
            TToast.show(this, "验证码不正确，请重新输入", TToast.TIME_3);
            return;
        }
        String signature= MyUtil.getSignature(account);
        MsgController.getInstance().getRegister(account,password,"",signature,new HttpCallback(RegActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                // 成功处理
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s,"biz_content");
                if (state.equals("success")) {
                    TToast.show(RegActivity.this,biz_content,TToast.TIME_2);
                    logout();

                    finish();
                }else{
                    TToast.show(RegActivity.this,biz_content,TToast.TIME_2);
                }
            }
        });

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
