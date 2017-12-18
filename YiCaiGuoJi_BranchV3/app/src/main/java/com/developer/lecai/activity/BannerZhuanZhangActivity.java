package com.developer.lecai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.PatternOfPaymentBean;

/**
 * Created by liuwei on 2017/7/6.
 */

public class BannerZhuanZhangActivity extends BaseActivity {

    private View view;
    private TextView tv_banner_idcard;
    private TextView tv_banner_people;
    private TextView tv_banner_name;
    private TextView tv_banner_outload;
    private String money;
    private PatternOfPaymentBean rechargeTypeBean;

    @Override
    public View getLayout() {

        view = View.inflate(BannerZhuanZhangActivity.this, R.layout.activity_bannerzhuanzhang, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("银行");
        tv_banner_idcard = (TextView) view.findViewById(R.id.tv_banner_idcard);
        tv_banner_people = (TextView) view.findViewById(R.id.tv_banner_people);
        tv_banner_name = (TextView) view.findViewById(R.id.tv_banner_name);
        tv_banner_outload = (TextView) view.findViewById(R.id.tv_banner_outload);
    }

    @Override
    public void initListener() {
        money = getIntent().getStringExtra("money");
        rechargeTypeBean = (PatternOfPaymentBean) getIntent().getSerializableExtra("payment");
        if(null == rechargeTypeBean || null == money){
            return;
        }

        tvTitle.setText(rechargeTypeBean.getPaytype());
        tv_banner_outload.setOnClickListener(this);
        updateView(rechargeTypeBean);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_banner_outload:
                Intent intent=new Intent(BannerZhuanZhangActivity.this,ChongZhiInfoActivity.class);
                intent.putExtra("money",money);
                intent.putExtra("payment",rechargeTypeBean);
                startActivity(intent);
                break;
        }
    }

    private void updateView(PatternOfPaymentBean bean){
        String paycode = bean.getPaycode();
        if(null == paycode || "".equals(paycode)){
            return;
        }
        String[] split = paycode.split("\\|");
        if(split.length >2){
            tv_banner_name.setText(split[0]);
            tv_banner_idcard.setText(split[1]);
            tv_banner_people.setText(split[2]);
        }

    }
}
