package com.developer.lecai.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.adapter.VipAdapter;
import com.developer.lecai.bean.VipBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.view.TToast;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;

import static cn.bingoogolapple.qrcode.zxing.QRCodeEncoder.syncEncodeQRCode;
import static com.developer.lecai.http.H.DOMAINName;

public class MineVipActivity extends BaseActivity {

    private View view;
    private TextView tv_vip_kaihu;
    private TextView tv_vip_share;
    private ImageView iv_vip_erweima;
    private TextView tv_vip_lianjie;
    private TextView tv_vip_fuzhi;
    private LinearLayout ll_vip_lianjie;
    private LinearLayout ll_vip_kaihu;
    private EditText et_vip_account;
    private EditText et_vip_psw;
    private EditText et_vip_confirmPsw;
    private TextView tv_vip_confirm;
    private ListView lv_vip_listview;
    private String shareUrl;
    private List<VipBean> list;
    private VipAdapter vipAdapter;
    private TextView tv_vip_sharecode;

    @Override
    public View getLayout() {
        view = View.inflate(MineVipActivity.this, R.layout.activity_vip, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("VIP分享");
        tv_vip_kaihu = (TextView) view.findViewById(R.id.tv_vip_kaihu);
        tv_vip_share = (TextView) view.findViewById(R.id.tv_vip_share);

        iv_vip_erweima = (ImageView) view.findViewById(R.id.iv_vip_erweima);
        tv_vip_lianjie = (TextView) view.findViewById(R.id.tv_vip_lianjie);
        tv_vip_fuzhi = (TextView) view.findViewById(R.id.tv_vip_fuzhi);
        tv_vip_sharecode = (TextView) view.findViewById(R.id.tv_vip_sharecode);
        ll_vip_lianjie = (LinearLayout) view.findViewById(R.id.ll_vip_lianjie);

        ll_vip_kaihu = (LinearLayout) view.findViewById(R.id.ll_vip_kaihu);
        et_vip_account = (EditText) view.findViewById(R.id.et_vip_account);
        et_vip_psw = (EditText) view.findViewById(R.id.et_vip_psw);
        et_vip_confirmPsw = (EditText) view.findViewById(R.id.et_vip_confirmPsw);
        tv_vip_confirm = (TextView) view.findViewById(R.id.tv_vip_confirm);
        lv_vip_listview = (ListView) view.findViewById(R.id.lv_vip_listview);
    }

    @Override
    public void initListener() {
        tv_vip_kaihu.setOnClickListener(this);
        tv_vip_share.setOnClickListener(this);
        tv_vip_confirm.setOnClickListener(this);
        tv_vip_fuzhi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.tv_vip_kaihu:
                ll_vip_lianjie.setVisibility(View.GONE);
                ll_vip_kaihu.setVisibility(View.VISIBLE);

                tv_vip_kaihu.setTextColor(getResources().getColor(R.color.color_f67355));
                tv_vip_kaihu.setSelected(true);
                tv_vip_share.setTextColor(getResources().getColor(R.color.color_333333));
                tv_vip_share.setSelected(false);

                requestKaiHu();
                break;
            case R.id.tv_vip_share:
                ll_vip_lianjie.setVisibility(View.VISIBLE);
                ll_vip_kaihu.setVisibility(View.GONE);

                tv_vip_share.setTextColor(getResources().getColor(R.color.color_f67355));
                tv_vip_share.setSelected(true);
                tv_vip_kaihu.setTextColor(getResources().getColor(R.color.color_333333));
                tv_vip_kaihu.setSelected(false);

                requestShare();
                break;
            case R.id.tv_vip_confirm:
                submitKaiHuInfo();
                break;
            case R.id.tv_vip_fuzhi:
                if (!TextUtils.isEmpty(shareUrl)) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", shareUrl);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(MineVipActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MineVipActivity.this, "链接为空，无法复制", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void submitKaiHuInfo() {
        String shareCode = UserController.getInstance().getLoginBean().getSharecode();
        String account = et_vip_account.getText().toString();
        String psw = et_vip_psw.getText().toString();
        String confirmPsw = et_vip_confirmPsw.getText().toString();
        String signature = MyUtil.getSignature(account);

        if (TextUtils.isEmpty(account)){
            Toast.makeText(MineVipActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw)){
            Toast.makeText(MineVipActivity.this,"用户密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(account)){
            Toast.makeText(MineVipActivity.this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!psw.equals(confirmPsw)){
            Toast.makeText(MineVipActivity.this,"用户密码和确认密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        MsgController.getInstance().getRegister(account, psw, shareCode, signature, new HttpCallback(MineVipActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if (state.equals("success")) {
                    TToast.show(MineVipActivity.this, biz_content, TToast.TIME_2);
                    finish();
                } else {
                    TToast.show(MineVipActivity.this, biz_content, TToast.TIME_2);
                }
            }
        });
    }


    private void requestKaiHu() {

        MsgController.getInstance().getVipShare(new HttpCallback(MineVipActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if (state.equals("success")) {
                    list = (List<VipBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<VipBean>>() {
                    }.getType());
                    if (vipAdapter == null) {
                        vipAdapter = new VipAdapter(MineVipActivity.this, list);
                        lv_vip_listview.setAdapter(vipAdapter);
                    } else {
                        vipAdapter.notifyDataSetInvalidated();
                    }
                } else {
                    TToast.show(MineVipActivity.this, biz_content, TToast.TIME_2);
                }
            }
        });
    }

    private void requestShare() {
        String host = "http://qr.liantu.com/api.php?w=200&text=";
        String text = DOMAINName + "/Home/AppRegister/";
        String shareCode = UserController.getInstance().getLoginBean().getSharecode();
        shareUrl = host + text + shareCode;
        tv_vip_lianjie.setText(text);
        shareUrl = text + shareCode;
        tv_vip_lianjie.setText(shareUrl);

        Bitmap qrCodeBitmap = syncEncodeQRCode(shareUrl, 1200, Color.BLACK, null);
        iv_vip_erweima.setImageBitmap(qrCodeBitmap);
    }

    @Override
    public void initData() {
        requestKaiHu();
        String shareCode = UserController.getInstance().getLoginBean().getSharecode();
        tv_vip_sharecode.setText(shareCode);
    }
}