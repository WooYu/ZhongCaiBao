package com.developer.lecai.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.PatternOfPaymentBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.DateUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.TToast;

import okhttp3.Call;

public class ChongZhiInfoActivity extends BaseActivity {

    private View view;
    private TextView tv_chongzhi_num;
    private TextView tv_chongzhi_bannername;
    private TextView tv_chongzhi_shoukuan;
    private TextView tv_chongzhi_shoukuannum;
    private TextView tv_chongzhi_money;
    private TextView tv_chongzhi_time;
    private EditText tv_chongzhi_zhuanzhang;
    private EditText tv_chongzhi_zhuanzhangnum;
    private TextView tv_chongzhi_commit;
    private String money;
    private PatternOfPaymentBean patternOfPaymentBean;

    @Override
    public View getLayout() {
        view = View.inflate(ChongZhiInfoActivity.this, R.layout.activity_chong_zhi_info, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("充值信息");
        money = getIntent().getStringExtra("money");
        patternOfPaymentBean = (PatternOfPaymentBean) getIntent().getSerializableExtra("payment");

        tv_chongzhi_num = (TextView) view.findViewById(R.id.tv_chongzhi_num);
        tv_chongzhi_bannername = (TextView) view.findViewById(R.id.tv_chongzhi_bannername);
        tv_chongzhi_shoukuan = (TextView) view.findViewById(R.id.tv_chongzhi_shoukuan);
        tv_chongzhi_shoukuannum = (TextView) view.findViewById(R.id.tv_chongzhi_shoukuannum);
        tv_chongzhi_money = (TextView) view.findViewById(R.id.tv_chongzhi_money);
        tv_chongzhi_time = (TextView) view.findViewById(R.id.tv_chongzhi_time);
        tv_chongzhi_zhuanzhang = (EditText) view.findViewById(R.id.tv_chongzhi_zhuanzhang);
        tv_chongzhi_zhuanzhangnum = (EditText) view.findViewById(R.id.tv_chongzhi_zhuanzhangnum);
        tv_chongzhi_commit = (TextView) view.findViewById(R.id.tv_chongzhi_commit);
    }

    @Override
    public void initListener() {
        tv_chongzhi_commit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_chongzhi_num.setText(UserController.getInstance().getLoginBean().getLoginname());
        tv_chongzhi_money.setText(money);
        tv_chongzhi_time.setText(DateUtil.getDateByFormat(3));
        updateView(patternOfPaymentBean);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_chongzhi_commit:
                bankcardRecharge();
                break;
        }
    }

    private void bankcardRecharge() {
        if(null == patternOfPaymentBean)
            return;

        String turenamestr = tv_chongzhi_zhuanzhang.getText().toString().trim();
        if(null == turenamestr || "".equals(turenamestr)){
            TToast.show(this,"请输入姓名！",TToast.TIME_2);
            return;
        }
        String cardcode = tv_chongzhi_zhuanzhangnum.getText().toString().trim();
        if(null == cardcode || "".equals(cardcode)){
            TToast.show(this,"请输入银行账号！",TToast.TIME_2);
            return;
        }

        MsgController.getInstance().getHuiZhi(patternOfPaymentBean.getPaytype(),
                "", turenamestr, cardcode, money, patternOfPaymentBean.getPaytype(),
                new HttpCallback(this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("回执单", s + "------" + biz_content);
                if (state.equals("success")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                    finish();
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateView(PatternOfPaymentBean bean){
        if(null == bean){
            return;
        }

        String paycode = bean.getPaycode();
        if(null == paycode || "".equals(paycode)){
            return;
        }
        String[] split = paycode.split("\\|");
        if(split.length >2){
            tv_chongzhi_bannername.setText(split[0]);
            tv_chongzhi_shoukuannum.setText(split[1]);
            tv_chongzhi_shoukuan.setText(split[2]);
        }

    }
}
