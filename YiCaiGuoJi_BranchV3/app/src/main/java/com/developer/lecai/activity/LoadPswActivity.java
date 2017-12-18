package com.developer.lecai.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.view.TToast;

import okhttp3.Call;

public class LoadPswActivity extends BaseActivity {


    private EditText et_setting_oldpsw;
    private EditText et_setting_newpsw;
    private EditText et_setting_confirmpsw;
    private TextView tv_setting_loadpsw;
    private View view;
    private String password;

    @Override
    public View getLayout() {
        view = View.inflate(LoadPswActivity.this, R.layout.activity_load_psw, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("修改登录密码");
        et_setting_oldpsw = (EditText) view.findViewById(R.id.et_setting_oldpsw);
        et_setting_newpsw = (EditText) view.findViewById(R.id.et_setting_newpsw);
        et_setting_confirmpsw = (EditText) view.findViewById(R.id.et_setting_confirmpsw);
        tv_setting_loadpsw = (TextView) view.findViewById(R.id.tv_setting_loadpsw);
    }

    @Override
    public void initListener() {
        tv_setting_loadpsw.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_setting_loadpsw:
                updatePwd();
                break;
        }
    }

    private void updatePwd() {
        String oldPwd = et_setting_oldpsw.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            TToast.show(this, "请输入旧密码", TToast.TIME_3);
            return;
        }

        password = et_setting_newpsw.getText().toString();
        if (TextUtils.isEmpty(password)) {
            TToast.show(this, "请输入新密码", TToast.TIME_3);
            return;
        }

        String confirmPwd = et_setting_confirmpsw.getText().toString();
        if (TextUtils.isEmpty(confirmPwd) && password.equals(confirmPwd)) {
            TToast.show(this, "请确定新密码", TToast.TIME_3);
            return;
        }

        MsgController.getInstance().getUpdateLoginPWD(password, oldPwd, new HttpCallback(LoadPswActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                // 成功处理
                String state = JsonUtil.getFieldValue(s, "state");
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                LogUtils.e("修改登录密码", biz_content);
                if (state.equals("success")) {
                    SharedPreferencesUtils.putValue(LoadPswActivity.this, "password", password);
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                    finish();
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
