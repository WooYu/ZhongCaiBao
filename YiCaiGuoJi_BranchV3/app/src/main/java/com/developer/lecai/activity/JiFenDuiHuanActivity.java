package com.developer.lecai.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.MyUtil;

import okhttp3.Call;

public class JiFenDuiHuanActivity extends BaseActivity {

    private TextView tv_jifen_jifen;
    private TextView tv_jifen_yue;
    private TextView tv_jifen_keyongjifen;
    private EditText et_jifen_duihuan;
    private TextView tv_jifen_submit;
    private View view;

    @Override
    public View getLayout() {
        view = View.inflate(JiFenDuiHuanActivity.this, R.layout.activity_ji_fen_dui_huan, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("积分兑换");
        tv_jifen_jifen = (TextView) findViewById(R.id.tv_jifen_jifen);
        tv_jifen_yue = (TextView) findViewById(R.id.tv_jifen_yue);
        tv_jifen_keyongjifen = (TextView) findViewById(R.id.tv_jifen_keyongjifen);
        et_jifen_duihuan = (EditText) findViewById(R.id.et_jifen_duihuan);
        tv_jifen_submit = (TextView) findViewById(R.id.tv_jifen_submit);
    }

    @Override
    public void initListener() {
        tv_jifen_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_jifen_submit:
                submitJiFen();
                break;
        }
    }

    private void submitJiFen() {
        String jifen = et_jifen_duihuan.getText().toString();
        if (TextUtils.isEmpty(jifen)) {
            Toast.makeText(JiFenDuiHuanActivity.this, "兑换积分不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        MsgController.getInstance().getJiFen(jifen, new HttpCallback(JiFenDuiHuanActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("积分兑换", s + "------" + biz_content);
                if (state.equals("success")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void initData() {

        MsgController.getInstance().getMoney(new HttpCallback(JiFenDuiHuanActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if (state.equals("success")) {
                    String accountfee = JsonUtil.getStringValue(biz_content, "accountfee");
                    if (!TextUtils.isEmpty(accountfee)) {
                        float balance = Float.parseFloat(accountfee);
                    }
                    String discountfee = JsonUtil.getStringValue(biz_content, "discountfee");
                    String intefee = JsonUtil.getStringValue(biz_content, "intefee");

                    tv_jifen_jifen.setText(intefee + "");
                    tv_jifen_yue.setText(MyUtil.formatAmount(accountfee));
                    tv_jifen_keyongjifen.setText(intefee + "");
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}
