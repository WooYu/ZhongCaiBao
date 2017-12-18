package com.developer.lecai.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.NowBankCardBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.ImageUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.MyUtil;

import okhttp3.Call;

import static com.developer.lecai.http.H.Code.TiXianBankCard;
import static com.developer.lecai.http.H.Code.TiXianBankCardResult;

/**
 * Created by liuwei on 2017/7/5.
 */

public class TiXianActivity extends BaseActivity {

    private View view;
    private RelativeLayout rlAddBankcard;
    private TextView tv_look_my_bankcard;
    private Button button_tikuan;
    private NowBankCardBean bankCardBean;
    private ImageView iv_add_bank;
    private TextView tv_add_bank_card;
    private TextView tv_add_bank_card_tip;
    private EditText et_input_password;
    private EditText et_input_number;
    private TextView tv_extract_money;

    @Override
    public View getLayout() {
        view = View.inflate(TiXianActivity.this, R.layout.activity_extract_cash,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("提现");
        tv_look_my_bankcard = (TextView) findViewById(R.id.tv_look_my_bankcard);
        rlAddBankcard = (RelativeLayout) findViewById(R.id.rl_add_bankcard);
        button_tikuan = (Button) findViewById(R.id.button_tikuan);

        iv_add_bank = (ImageView) findViewById(R.id.iv_add_bank);
        tv_add_bank_card = (TextView) findViewById(R.id.tv_add_bank_card);
        tv_add_bank_card_tip = (TextView) findViewById(R.id.tv_add_bank_card_tip);
        tv_extract_money = (TextView) findViewById(R.id.tv_extract_money);

        et_input_password = (EditText) findViewById(R.id.et_input_password);
        et_input_number = (EditText) findViewById(R.id.et_input_number);

    }

    @Override
    public void initListener() {
        rlAddBankcard.setOnClickListener(this);
        tv_look_my_bankcard.setOnClickListener(this);
        button_tikuan.setOnClickListener(this);
    }

    @Override
    public void initData() {
        /*bankCardBean=new NowBankCardBean();
        bankCardBean.setAutoid(1);
        bankCardBean.setBankcode("cbd");
        bankCardBean.setBanname("jijij");
        bankCardBean.setCardcode("188888880000");
        bankCardBean.setIconurl("http://www.baidu.com");*/
        MsgController.getInstance().getMoney(new HttpCallback(TiXianActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if (state.equals("success")) {
                    String accountfee = JsonUtil.getStringValue(biz_content, "accountfee");
                    tv_extract_money.setText(MyUtil.formatAmount(accountfee));
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_add_bankcard:
                intent.setClass(this, SelectBankCardActivity.class);
                startActivityForResult(intent,TiXianBankCard);
                break;
            case R.id.tv_look_my_bankcard:
                intent.setClass(this, SelectBankCardActivity.class);
                startActivityForResult(intent,TiXianBankCard);
                break;
            case R.id.button_tikuan:
                submitInfo();
                break;
        }
    }

    private void submitInfo() {

        if (bankCardBean==null){
            Toast.makeText(TiXianActivity.this,"请选择收款银行卡",Toast.LENGTH_LONG).show();
            return;
        }

        String psw=et_input_password.getText().toString();
        String money=et_input_number.getText().toString();

        if (TextUtils.isEmpty(money)){
            Toast.makeText(TiXianActivity.this,"提现金额不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(psw)){
            Toast.makeText(TiXianActivity.this,"提现密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        MsgController.getInstance().getTiKuan(et_input_password.getText().toString(), money, bankCardBean.getAutoid()+"", new HttpCallback(TiXianActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("提现", s + "------" + biz_content);
               if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == TiXianBankCardResult) {
            if (requestCode == TiXianBankCard) {
                bankCardBean = (NowBankCardBean) data.getSerializableExtra("SelectBankBean");
                String cardNum=bankCardBean.getCardcode();
                if(cardNum.length()>12){
                    cardNum=cardNum.substring(12);
                }else{
                    cardNum="****";
                }
               ImageUtil.setImage(bankCardBean.getIconurl()+"",iv_add_bank);
                tv_add_bank_card.setText(bankCardBean.getBankname());
                tv_add_bank_card_tip.setText("储蓄卡尾号为"+cardNum);
            }
        }



    }
}
