package com.developer.lecai.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.developer.lecai.R;
import com.developer.lecai.adapter.TopUpTypeAdapter;
import com.developer.lecai.bean.RechargeTypeBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity {

    private TextView tv_account;
    private EditText et_money;
    private ToggleButton tb_money_100;
    private ToggleButton tb_money_200;
    private ToggleButton tb_money_300;
    private RecyclerView rv_channel;

    private List<RechargeTypeBean> mRechargeChannelList;
    private TopUpTypeAdapter topUpTypeAdapter;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_recharge, null);
    }

    @Override
    public void initView() {
        tvTitle.setText("充值");

        tv_account = (TextView) findViewById(R.id.tv_account);
        et_money = (EditText) findViewById(R.id.et_money);
        tb_money_100 = (ToggleButton) findViewById(R.id.tb_money_100);
        tb_money_200 = (ToggleButton) findViewById(R.id.tb_money_200);
        tb_money_300 = (ToggleButton) findViewById(R.id.tb_money_300);
        rv_channel = (RecyclerView) findViewById(R.id.rv_channel);

        getRechargeType();
    }

    @Override
    public void initListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney(v.getId());
            }
        };
        tb_money_100.setOnClickListener(listener);
        tb_money_200.setOnClickListener(listener);
        tb_money_300.setOnClickListener(listener);
    }

    public void selectMoney(int resId) {
        switch (resId) {
            case R.id.tb_money_100:
                et_money.setText("" + 100);
                tb_money_100.setChecked(tb_money_100.isChecked());
                tb_money_200.setChecked(false);
                tb_money_300.setChecked(false);
                break;
            case R.id.tb_money_200:
                et_money.setText("" + 200);
                tb_money_100.setChecked(false);
                tb_money_200.setChecked(tb_money_200.isChecked());
                tb_money_300.setChecked(false);
                break;
            case R.id.tb_money_300:
                et_money.setText("" + 300);
                tb_money_100.setChecked(false);
                tb_money_200.setChecked(false);
                tb_money_300.setChecked(tb_money_300.isChecked());
                break;
        }
    }

    @Override
    public void initData() {
        tv_account.setText(UserController.getInstance().getLoginBean().getLoginname());
    }

    //获取充值方式
    private void getRechargeType() {
        mRechargeChannelList = new ArrayList<>();
        rv_channel.setLayoutManager(new LinearLayoutManager(this));
        topUpTypeAdapter = new TopUpTypeAdapter(this, mRechargeChannelList);
        rv_channel.setAdapter(topUpTypeAdapter);
        topUpTypeAdapter.setItemClickListener(new TopUpTypeAdapter.OnItemClickListener() {
            @Override
            public void clickOnlinePayment(int position) {
               turn2ChannelList(position);
            }

            @Override
            public void clickBankPayment(int position) {
                turn2ChannelList(position);
            }
        });

        MsgController.getInstance().getRechargeChannel(new HttpCallback(this) {
            @Override
            public void onSuccess(Call call, String s) {
                LogUtils.d("RechargeActivity", "充值类型：" + s);

                String state = JsonUtil.getFieldValue(s, "state");
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                if (state.equals("success")) {
                    mRechargeChannelList = (List<RechargeTypeBean>) JsonUtil.parseJsonToList(
                            biz_content, new TypeToken<List<RechargeTypeBean>>() {
                            }.getType());
                    addData();
                } else if (state.equals("error")) {
                    Toast.makeText(RechargeActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //添加数据
    private void addData() {
        if (null == mRechargeChannelList) {
            mRechargeChannelList = new ArrayList<>();
        }

        RechargeTypeBean bankChannel = new RechargeTypeBean();
        bankChannel.setTypename("银行支付");
        int size = mRechargeChannelList.size();
        if (0 < mRechargeChannelList.size()
                && "bank".equals(mRechargeChannelList.get(size - 1).getTypecode())) {
            mRechargeChannelList.add((Math.max(0,size -1)),bankChannel);
        } else {
            mRechargeChannelList.add(bankChannel);
        }

        RechargeTypeBean onlineChannel = new RechargeTypeBean();
        onlineChannel.setTypename("在线充值");
        mRechargeChannelList.add(0, onlineChannel);

        topUpTypeAdapter.setData(mRechargeChannelList);
    }

    private void turn2ChannelList(int position){
        String money = et_money.getText().toString();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(RechargeActivity.this, "请输入充值金额", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent();
        intent.setClass(this, RechargeChannelAcitivity.class);
        intent.putExtra("rechargetype",mRechargeChannelList.get(position));
        intent.putExtra("money", money);
        startActivity(intent);
    }
}
