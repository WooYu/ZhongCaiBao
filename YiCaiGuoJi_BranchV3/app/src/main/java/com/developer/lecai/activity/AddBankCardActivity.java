package com.developer.lecai.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.BankCardBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.ImageUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.TToast;

import okhttp3.Call;

import static com.developer.lecai.http.H.Code.BankCard;
import static com.developer.lecai.http.H.Code.BankCardResult;

public class AddBankCardActivity extends BaseActivity {

    private RelativeLayout rlChooseBank;
    private BankCardBean bankCardBean;
    private ImageView iv_bank_img;
    private TextView tv_bank_title;
    private TextView tv_bank_code;
    private EditText et_add_cardnum;
    private EditText et_add_kaihu;
    private EditText et_add_realName;
    private Button bt_button_submit;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_add_bank_card, null);
    }

    @Override
    public void initView() {
        tvTitle.setText("添加银行卡");
        iv_bank_img = (ImageView) findViewById(R.id.iv_bank_img);
        tv_bank_title = (TextView) findViewById(R.id.tv_bank_title);
        tv_bank_code = (TextView) findViewById(R.id.tv_bank_code);
        et_add_cardnum = (EditText) findViewById(R.id.et_add_cardnum);
        et_add_kaihu = (EditText) findViewById(R.id.et_add_kaihu);
        et_add_realName = (EditText) findViewById(R.id.et_add_realName);
        rlChooseBank = (RelativeLayout) findViewById(R.id.rl_choose_bank);
        bt_button_submit = (Button) findViewById(R.id.bt_button_submit);
    }

    @Override
    public void initListener() {
        rlChooseBank.setOnClickListener(this);
        bt_button_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_choose_bank:
                intent.setClass(this, ChooseBankCardActivity.class);
                startActivityForResult(intent, BankCard);
                break;
            case R.id.bt_button_submit:
                submitInfo();
                break;
        }
    }

    private void submitInfo() {
        String realName = et_add_realName.getText().toString();
        String cardnum = et_add_cardnum.getText().toString();
        String kaihu = et_add_kaihu.getText().toString();
        if (TextUtils.isEmpty(realName)) {
            Toast.makeText(AddBankCardActivity.this, "真实姓名不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(cardnum)) {
            Toast.makeText(AddBankCardActivity.this, "银行卡号不能为空", Toast.LENGTH_LONG).show();
            return;
        }else if(cardnum.length()<16){
            Toast.makeText(this,"银行卡输入不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(kaihu)) {
            Toast.makeText(AddBankCardActivity.this, "开户地址不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        MsgController.getInstance().getAddBankCard(realName
                , bankCardBean.getBankTitle() + "", cardnum, kaihu, bankCardBean.getBankDesc() + "", new HttpCallback(AddBankCardActivity.this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                        String state = JsonUtil.getFieldValue(s, "state");
                        if ("success".equals(state)) {
                            finish();
                        } else {
                            TToast.show(AddBankCardActivity.this, biz_content, TToast.TIME_2);
                        }
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BankCardResult) {
            if (requestCode == BankCard) {
                bankCardBean = (BankCardBean) data.getSerializableExtra("BankBean");
                ImageUtil.setImage(bankCardBean.getBankImg() + "", iv_bank_img);
                tv_bank_title.setText(bankCardBean.getBankTitle() + "");
                tv_bank_code.setText(bankCardBean.getBankDesc() + "");
            }
        }
    }
}
