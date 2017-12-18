package com.developer.lecai.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.view.TToast;

import okhttp3.Call;

import static cn.bingoogolapple.qrcode.zxing.QRCodeEncoder.syncEncodeQRCode;
import static com.developer.lecai.http.H.DOMAINName;

public class DaiLiKaiHuActivity extends BaseActivity {

    private View view;
    private LinearLayout ll_directly_open_account;
    private EditText et_input_username;
    private EditText et_input_password;
    private EditText et_input_password_sure;
    private Button btn_sure;
    private LinearLayout ll_link_open_account;
    private ImageView tv_qr_code;
    private TextView tv_qr_adddress;
    private TextView tv_copy;
    private TextView tv_dailishouyi_id;
    private TextView tv_directly_open_account;
    private TextView tv_link_open_account;
    private String shareUrl;

    @Override
    public View getLayout() {
        view = View.inflate(DaiLiKaiHuActivity.this, R.layout.activity_dai_li_shou_yi, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("代理开户");
        tv_directly_open_account = (TextView) view.findViewById(R.id.tv_directly_open_account);
        tv_link_open_account = (TextView) view.findViewById(R.id.tv_link_open_account);

        ll_directly_open_account = (LinearLayout) view.findViewById(R.id.ll_directly_open_account);
        et_input_username = (EditText) view.findViewById(R.id.et_input_username);
        et_input_password = (EditText) view.findViewById(R.id.et_input_password);
        et_input_password_sure = (EditText) view.findViewById(R.id.et_input_password_sure);
        btn_sure = (Button) view.findViewById(R.id.btn_sure);

        ll_link_open_account = (LinearLayout) view.findViewById(R.id.ll_link_open_account);
        tv_qr_code = (ImageView) view.findViewById(R.id.tv_qr_code);
        tv_qr_adddress = (TextView) view.findViewById(R.id.tv_qr_adddress);
        tv_copy = (TextView) view.findViewById(R.id.tv_copy);
        tv_dailishouyi_id = (TextView) view.findViewById(R.id.tv_dailishouyi_id);
        tv_directly_open_account.setSelected(true);
    }

    @Override
    public void initListener() {
        tv_directly_open_account.setOnClickListener(this);
        tv_link_open_account.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        tv_copy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_directly_open_account:
                ll_directly_open_account.setVisibility(View.VISIBLE);
                ll_link_open_account.setVisibility(View.GONE);
                tv_directly_open_account.setTextColor(getResources().getColor(R.color.color_fc9005));
                tv_directly_open_account.setSelected(true);

                tv_link_open_account.setTextColor(getResources().getColor(R.color.color_191919));
                tv_link_open_account.setSelected(false);
                break;
            case R.id.tv_link_open_account:
                ll_directly_open_account.setVisibility(View.GONE);
                ll_link_open_account.setVisibility(View.VISIBLE);

                tv_link_open_account.setTextColor(getResources().getColor(R.color.color_fc9005));
                tv_link_open_account.setSelected(true);

                tv_directly_open_account.setTextColor(getResources().getColor(R.color.color_191919));
                tv_directly_open_account.setSelected(false);
                requestShare();
                break;
            case R.id.btn_sure:
                submitKaiHuInfo();
                break;
            case R.id.tv_copy:
                if (!TextUtils.isEmpty(shareUrl)) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", shareUrl);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(DaiLiKaiHuActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DaiLiKaiHuActivity.this, "链接为空，无法复制", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void initData() {
        String shareCode = UserController.getInstance().getLoginBean().getSharecode();
        tv_dailishouyi_id.setText(shareCode);
    }

    private void requestShare() {
        String text = DOMAINName + "/Home/AppRegister/";
        String shareCode = UserController.getInstance().getLoginBean().getSharecode();
        shareUrl = text + shareCode;
        tv_qr_adddress.setText(shareUrl);

        Bitmap qrCodeBitmap = syncEncodeQRCode(shareUrl, 1200, Color.BLACK, null);
        tv_qr_code.setImageBitmap(qrCodeBitmap);
    }

    private void submitKaiHuInfo() {
        String shareCode = UserController.getInstance().getLoginBean().getSharecode();
        String account = et_input_username.getText().toString();
        String psw = et_input_password.getText().toString();
        String confirmPsw = et_input_password_sure.getText().toString();
        String signature = MyUtil.getSignature(account);

        if (TextUtils.isEmpty(account)) {
            Toast.makeText(DaiLiKaiHuActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(DaiLiKaiHuActivity.this, "用户密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(DaiLiKaiHuActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!psw.equals(confirmPsw)) {
            Toast.makeText(DaiLiKaiHuActivity.this, "用户密码和确认密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        MsgController.getInstance().getRegister(account, psw, shareCode, signature, new HttpCallback(DaiLiKaiHuActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if (state.equals("success")) {
                    TToast.show(DaiLiKaiHuActivity.this, biz_content, TToast.TIME_2);
                    finish();
                } else {
                    TToast.show(DaiLiKaiHuActivity.this, biz_content, TToast.TIME_2);
                }
            }
        });
    }
}
