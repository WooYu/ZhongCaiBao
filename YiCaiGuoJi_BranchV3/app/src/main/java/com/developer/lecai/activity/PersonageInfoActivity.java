package com.developer.lecai.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.LoginBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.H;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.http.HttpRequestRegister;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/5.
 */

public class PersonageInfoActivity extends BaseActivity {

    private View view;
    private TextView tv_info_psw;
    private EditText tv_info_nickname;
    private EditText et_info_name;
    private EditText et_info_idcard;
    private EditText et_info_phone;
    private EditText et_info_qq;
    private TextView tv_info_alter;

    @Override
    public View getLayout() {
        view = View.inflate(PersonageInfoActivity.this, R.layout.activity_personageinfo,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("个人信息");
        tv_info_psw = (TextView) view.findViewById(R.id.tv_info_psw);
        tv_info_nickname = (EditText) view.findViewById(R.id.tv_info_nickname);
        et_info_name = (EditText) view.findViewById(R.id.et_info_name);
        et_info_idcard = (EditText) view.findViewById(R.id.et_info_idcard);
        et_info_phone = (EditText) view.findViewById(R.id.et_info_phone);
        et_info_qq = (EditText) view.findViewById(R.id.et_info_qq);
        tv_info_alter = (TextView) view.findViewById(R.id.tv_info_alter);

    }

    @Override
    public void initListener() {
        tv_info_alter.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String account= SharedPreferencesUtils.getValue(PersonageInfoActivity.this,"account");
        tv_info_psw.setText(account);
        String psw=SharedPreferencesUtils.getValue(PersonageInfoActivity.this,"password");
        String  signature = MyUtil.getSignature(account);

        new HttpRequestRegister.Builder()
                .addParam(H.Param.account, account)
                .addParam(H.Param.password, psw)
                .addParam(H.Param.signature,signature)
                .build()
                .post(H.URL.Login, new HttpCallback(PersonageInfoActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        // 成功处理
                        // 取出登录信息
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        Log.e("登录返回", biz_content);
                        Log.e("---", "登录信息：" + biz_content);
                        if (state.equals("success")) {
                            UserController userController = UserController.getInstance();
                            userController.saveUserInfoToShare(biz_content);
                            setData();
                        } else if (state.equals("error")) {
                            Toast.makeText(PersonageInfoActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void setData() {
        LoginBean loginBean=UserController.getInstance().getLoginBean();
        tv_info_nickname.setText(loginBean.getUsername());
        et_info_name.setText(loginBean.getRealname());
        et_info_idcard.setText(loginBean.getIdcard());
        et_info_phone.setText(loginBean.getMobilephone());
        et_info_qq.setText(loginBean.getQq());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_info_alter:
                String username=tv_info_nickname.getText().toString().trim();
                String qq=et_info_qq.getText().toString().trim();
                String mobile=et_info_phone.getText().toString().trim();
                String realname=et_info_name.getText().toString().trim();
                String idcard=et_info_idcard.getText().toString().trim();

                MsgController.getInstance().getUpduserinfo(username, qq, mobile, realname, idcard, new HttpCallback(PersonageInfoActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        Log.e("修改用户信息",s);
                        String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                        String state = JsonUtil.getFieldValue(s, "state");
                        Log.e("修改用户信息", s + "------" + biz_content);
                        if (state.equals("success")) {
                         //   Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                            PersonageInfoActivity.this.finish();
                        } else if (state.equals("error")) {
                            Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
               break;
        }
    }
}
