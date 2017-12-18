package com.developer.lecai.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.TToast;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/5.
 */

public class ZiJinPswActivity extends BaseActivity {

    private View view;
    private EditText et_setting_oldpsw;
    private EditText et_setting_newpsw;
    private EditText et_setting_confirmpsw;
    private TextView tv_setting_zjpsw;

    @Override
    public View getLayout() {

        view = View.inflate(ZiJinPswActivity.this, R.layout.activity_zijinpsw, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("修改资金密码");
        et_setting_oldpsw = (EditText) view.findViewById(R.id.et_setting_oldpsw);
        et_setting_newpsw = (EditText) view.findViewById(R.id.et_setting_newpsw);
        et_setting_confirmpsw = (EditText) view.findViewById(R.id.et_setting_confirmpsw);
        tv_setting_zjpsw = (TextView) view.findViewById(R.id.tv_setting_zjpsw);
    }

    @Override
    public void initListener() {
        tv_setting_zjpsw.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_setting_zjpsw:
                updateTixianPwd();
                break;
        }
    }

    private void updateTixianPwd() {
        String oldPwd = et_setting_oldpsw.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            TToast.show(this, "请输入旧密码", TToast.TIME_3);
            return;
        }

        String password = et_setting_newpsw.getText().toString();
        if (TextUtils.isEmpty(password)) {
            TToast.show(this, "请输入新密码", TToast.TIME_3);
            return;
        }

        String confirmPwd = et_setting_confirmpsw.getText().toString();
        if (TextUtils.isEmpty(confirmPwd) && password.equals(confirmPwd)) {
            TToast.show(this, "请确定新密码", TToast.TIME_3);
            return;
        }

        MsgController.getInstance().getTiXianPsw(password, oldPwd, new HttpCallback(ZiJinPswActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                Log.e("修改资金密码", s);
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                String state = JsonUtil.getStringValue(s, "state");
                if ("error".equals(state)) {
                    TToast.show(ZiJinPswActivity.this, biz_content, TToast.TIME_2);
                }else{
                    TToast.show(ZiJinPswActivity.this, biz_content, TToast.TIME_2);
                    finish();
                }
            }
        });
    }
}
