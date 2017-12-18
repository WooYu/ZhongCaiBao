package com.developer.lecai.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.ChatRoomIdBean;
import com.developer.lecai.bean.OnLineBean;
import com.developer.lecai.bean.RoomInfoBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class Beijing28Activity extends BaseActivity {

    private RelativeLayout rlHuishui;
    private List<ChatRoomIdBean> data = new ArrayList<>();
    private List<OnLineBean> onLineData;
    public static final String WANFATYPE = "wanfaType";
    /**
     * 北京28或者加拿大28玩法类型
     */
    private String wanfaType;
    /**
     * 房间类型
     */
    private String fangjianType;
    /**
     * 北京28返回按钮
     */
    private ImageView beijing28_back;
    /**
     * 初级房人数
     */
    private TextView BJ_chuji_person_Num, fir_huishui_pl, sec_huishui_pl, thr_huishui_pl;
    /**
     * 中级房人数
     */
    private TextView BJ_zhongji_person_Num;
    /**
     * 高级房人数
     */
    private TextView BJ_gaojiji_person_Num;
    /**
     * 初级房image
     */
    private ImageView BJ_chujifang;
    /**
     * 中级房image
     */
    private ImageView BJ_zhongjifang;
    /**
     * 高级房image
     */
    private ImageView BJ_gaojifang;

    private String fir_vip1_personNum;
    private String fir_vip2_personNum;
    private String fir_vip3_personNum;
    private String fir_vip4_personNum;
    /**
     * 初级房总人数
     */
    private int fir_rmtotal;
    private String sec_vip1_personNum;
    private String sec_vip2_personNum;
    private String sec_vip3_personNum;
    private String sec_vip4_personNum;
    /**
     * 中级房总人数
     */
    private int sec_rmtotal;
    private String thr_vip1_personNum;
    private String thr_vip2_personNum;
    private String thr_vip3_personNum;
    private String thr_vip4_personNum;
    /**
     * 高级房总人数
     */
    private int thr_rmtotal;
    private List<RoomInfoBean> roomInfoBean;


    /**
     * 初级房最大人数
     */
    private int fir_Maxuser;
    /**
     * 初级房vip房间最大人数
     */
    private String fir_vipmaxuser;
    /**
     * 进入初级房金额限制
     */
    private int fir_enter;
    /**
     * 中级房最大人数
     */
    private int sec_Maxuser;
    /**
     * 中级房vip房间最大人数
     */
    private String sec_vipmaxuser;
    /**
     * 进入中级房金额限制
     */
    private int sec_enter;
    /**
     * 高级房最大人数
     */
    private int thr_Maxuser;
    /**
     * 高级房vip房间最大人数
     */
    private String thr_vipmaxuser;
    /**
     * 进入高级房金额限制
     */
    private float thr_enter;
    /**
     * 当前余额
     */
    private float balance;
    private String accountfee;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_beijing28, null);
    }

    @Override
    public void initView() {
        // rlHuishui = (RelativeLayout) findViewById(R.id.rl_huishui);
        // 北京28页返回箭头
        beijing28_back = (ImageView) findViewById(R.id.beijing28_back);
        // 初级房人数
        BJ_chuji_person_Num = (TextView) findViewById(R.id.BJ_chuji_person_Num);
        // 中级房人数
        BJ_zhongji_person_Num = (TextView) findViewById(R.id.BJ_zhongji_person_Num);
        // 高级房人数
        BJ_gaojiji_person_Num = (TextView) findViewById(R.id.BJ_gaojiji_person_Num);

        fir_huishui_pl = (TextView) findViewById(R.id.fir_huishui_pl);
        sec_huishui_pl = (TextView) findViewById(R.id.sec_huishui_pl);
        thr_huishui_pl = (TextView) findViewById(R.id.thr_huishui_pl);
        // 初级房image
        BJ_chujifang = (ImageView) findViewById(R.id.BJ_chujifang);
        // 中级房image
        BJ_zhongjifang = (ImageView) findViewById(R.id.BJ_zhongjifang);
        // 高级房image
        BJ_gaojifang = (ImageView) findViewById(R.id.BJ_gaojifang);

//        chuji_peilvshuoming = (ImageView) findViewById(R.id.chuji_peilvshuoming);//初级赔率说明
//        zhongji_peilvshuoming = (ImageView) findViewById(R.id.zhongji_peilvshuoming);//中级赔率说明
//        gaoji_peilvshuoming = (ImageView) findViewById(R.id.gaoji_peilvshuoming);//高级赔率说明


        wanfaType = getIntent().getStringExtra(WANFATYPE);
        LogUtils.d("wanfaType",wanfaType);
        tvTitle.setTextColor(getResources().getColor(R.color.white));
        if("bj".equals(wanfaType))
        {
            tvTitle.setText("北京28");
        }else {
            tvTitle.setText("加拿大28");
        }
    }

    @Override
    public void initListener() {
        //  rlHuishui.setOnClickListener(this);
        BJ_chujifang.setOnClickListener(this);
        BJ_zhongjifang.setOnClickListener(this);
        BJ_gaojifang.setOnClickListener(this);
    }

    @Override
    public void initData() {

        MsgController.getInstance().getChatRoomId("bj", new HttpCallback(Beijing28Activity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                Log.e("北京28", s);
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if ("success".equals(state)) {
                    data = (List<ChatRoomIdBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<ChatRoomIdBean>>() {
                    }.getType());

                    String bj_fir_vip1 = data.get(0).getName();
                    String bj_fir_vip2 = data.get(1).getName();
                    String bj_fir_vip3 = data.get(2).getName();
                    String bj_fir_vip4 = data.get(3).getName();

                    String bj_sec_vip1 = data.get(4).getName();
                    String bj_sec_vip2 = data.get(5).getName();
                    String bj_sec_vip3 = data.get(6).getName();
                    String bj_sec_vip4 = data.get(7).getName();

                    String bj_thr_vip1 = data.get(8).getName();
                    String bj_thr_vip2 = data.get(9).getName();
                    String bj_thr_vip3 = data.get(10).getName();
                    String bj_thr_vip4 = data.get(11).getName();

                    String cakeno_fir_vip1 = data.get(12).getName();
                    String cakeno_fir_vip2 = data.get(13).getName();
                    String cakeno_fir_vip3 = data.get(14).getName();
                    String cakeno_fir_vip4 = data.get(15).getName();

                    String cakeno_sec_vip1 = data.get(16).getName();
                    String cakeno_sec_vip2 = data.get(17).getName();
                    String cakeno_sec_vip3 = data.get(18).getName();
                    String cakeno_sec_vip4 = data.get(19).getName();

                    String cakeno_thr_vip1 = data.get(20).getName();
                    String cakeno_thr_vip2 = data.get(21).getName();
                    String cakeno_thr_vip3 = data.get(22).getName();
                    String cakeno_thr_vip4 = data.get(23).getName();

                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_fir_vip1", bj_fir_vip1);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_fir_vip2", bj_fir_vip2);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_fir_vip3", bj_fir_vip3);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_fir_vip4", bj_fir_vip4);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_sec_vip1", bj_sec_vip1);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_sec_vip2", bj_sec_vip2);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_sec_vip3", bj_sec_vip3);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_sec_vip4", bj_sec_vip4);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_thr_vip1", bj_thr_vip1);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_thr_vip2", bj_thr_vip2);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_thr_vip3", bj_thr_vip3);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "bj_thr_vip4", bj_thr_vip4);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_fir_vip1", cakeno_fir_vip1);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_fir_vip2", cakeno_fir_vip2);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_fir_vip3", cakeno_fir_vip3);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_fir_vip4", cakeno_fir_vip4);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_sec_vip1", cakeno_sec_vip1);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_sec_vip2", cakeno_sec_vip2);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_sec_vip3", cakeno_sec_vip3);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_sec_vip4", cakeno_sec_vip4);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_thr_vip1", cakeno_thr_vip1);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_thr_vip2", cakeno_thr_vip2);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_thr_vip3", cakeno_thr_vip3);
                    SharedPreferencesUtils.putValue(getApplicationContext(), "cakeno_thr_vip4", cakeno_thr_vip4);

                }
            }
        });

        MsgController.getInstance().getRoomonline(wanfaType, new HttpCallback(Beijing28Activity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                Log.e("北京28在线人数", s);
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");
                if (state.equals("error")) {
                    Toast.makeText(Beijing28Activity.this, biz_content, Toast.LENGTH_SHORT).show();
                    return;
                }

                onLineData = (List<OnLineBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<OnLineBean>>() {
                }.getType());
               //回水厅总在线人数和每个房间在线人数
                fir_rmtotal=onLineData.get(0).getRmtotal();
                fir_vip1_personNum = onLineData.get(0).getVip1()+"";
                fir_vip2_personNum = onLineData.get(0).getVip2()+"";
                fir_vip3_personNum = onLineData.get(0).getVip3()+"";
                fir_vip4_personNum = onLineData.get(0).getVip4()+"";
                //保本厅总在线人数和每个房间在线人数
                sec_rmtotal=onLineData.get(1).getRmtotal();
                sec_vip1_personNum = onLineData.get(1).getVip1()+"";
                sec_vip2_personNum = onLineData.get(1).getVip2()+"";
                sec_vip3_personNum = onLineData.get(1).getVip3()+"";
                sec_vip4_personNum = onLineData.get(1).getVip4()+"";

                //高赔率厅总在线人数和每个房间在线人数
                thr_rmtotal=onLineData.get(2).getRmtotal();
                thr_vip1_personNum = onLineData.get(2).getVip1()+"";
                thr_vip2_personNum = onLineData.get(2).getVip2()+"";
                thr_vip3_personNum = onLineData.get(2).getVip3()+"";
                thr_vip4_personNum = onLineData.get(2).getVip4()+"";

                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip1_personNum",
                        fir_vip1_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip2_personNum",
                        fir_vip2_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip3_personNum",
                        fir_vip3_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip4_personNum",
                        fir_vip4_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip1_personNum",
                        sec_vip1_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip2_personNum",
                        sec_vip2_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip3_personNum",
                        sec_vip3_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip4_personNum",
                        sec_vip4_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip1_personNum",
                        thr_vip1_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip2_personNum",
                        thr_vip2_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip3_personNum",
                        thr_vip3_personNum);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip4_personNum",
                        thr_vip4_personNum);

                BJ_chuji_person_Num.setText(fir_rmtotal + "人");

                BJ_zhongji_person_Num.setText(sec_rmtotal + "人");

                BJ_gaojiji_person_Num.setText(thr_rmtotal + "人");
            }
        });

        MsgController.getInstance().getRoomInfo("bj".equals(wanfaType)?"bjkl8":"cakeno", new HttpCallback(Beijing28Activity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                Log.e("北京28房间信息", s);
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");

                roomInfoBean = (List<RoomInfoBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<RoomInfoBean>>() {
                }.getType());

                fir_Maxuser = roomInfoBean.get(0).getMaxuser();
                fir_vipmaxuser = roomInfoBean.get(0).getVipmaxuser() + "";
                fir_enter = roomInfoBean.get(0).getEntermin();
                String oddsrateurl1 = roomInfoBean.get(0).oddsrateurl;

                sec_Maxuser = roomInfoBean.get(1).getMaxuser();
                sec_vipmaxuser = roomInfoBean.get(1).getVipmaxuser() + "";
                sec_enter = roomInfoBean.get(1).getEntermin();
                String oddsrateurl2 = roomInfoBean.get(1).oddsrateurl;

                thr_Maxuser = roomInfoBean.get(2).getMaxuser();
                thr_vipmaxuser = roomInfoBean.get(2).getVipmaxuser() + "";
                thr_enter = roomInfoBean.get(2).getEntermin();
                String oddsrateurl3 = roomInfoBean.get(2).oddsrateurl;


                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vipmaxuser", fir_vipmaxuser);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vipmaxuser", sec_vipmaxuser);
                SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vipmaxuser", thr_vipmaxuser);

                SharedPreferencesUtils.putValue(getApplicationContext(), "oddsrateurl1", oddsrateurl1);
                SharedPreferencesUtils.putValue(getApplicationContext(), "oddsrateurl2", oddsrateurl2);
                SharedPreferencesUtils.putValue(getApplicationContext(), "oddsrateurl3", oddsrateurl3);
            }
        });

        MsgController.getInstance().getMoney(new HttpCallback(Beijing28Activity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                Log.e("获取余额", s);
                String state = JsonUtil.getStringValue(s, "state");
                String biz_content = JsonUtil.getStringValue(s, "biz_content");

                accountfee = JsonUtil.getStringValue(biz_content, "accountfee");
                if (!TextUtils.isEmpty(accountfee)){
                    balance=Float.parseFloat(accountfee);
                }
                String discountfee = JsonUtil.getStringValue(biz_content, "discountfee");
                String intefee = JsonUtil.getStringValue(biz_content, "intefee");

            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.BJ_chujifang:
                fangjianType = "初级";
                if (balance < fir_enter) {
                    Toast.makeText(getApplicationContext(),
                            "账户余额不足" + fir_enter + "元，请充值后进入房间", Toast.LENGTH_SHORT).show();
                      return;
                }

                if (fir_Maxuser <= fir_rmtotal) {
                    Toast.makeText(getApplicationContext(), "当前房间已满", Toast.LENGTH_SHORT).show();
                }else {
                    intent.setClass(Beijing28Activity.this, FangJianActivity.class);
                    intent.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
                            .putExtra("VIP1", fir_vip1_personNum).putExtra("VIP2", fir_vip2_personNum)
                            .putExtra("VIP3", fir_vip3_personNum).putExtra("VIP4", fir_vip4_personNum);
                    startActivity(intent);
                }
                break;
            case R.id.BJ_zhongjifang:
                fangjianType = "中级";
                if (balance < sec_enter) {
                    Toast.makeText(getApplicationContext(),
                            "账户余额不足" + fir_enter + "元，请充值后进入房间", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sec_Maxuser <= sec_rmtotal) {
                    Toast.makeText(getApplicationContext(), "当前房间已满", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent2 = new Intent(Beijing28Activity.this, FangJianActivity.class);
                    intent2.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
                            .putExtra("VIP1", sec_vip1_personNum).putExtra("VIP2", sec_vip2_personNum)
                            .putExtra("VIP3", sec_vip3_personNum).putExtra("VIP4", sec_vip4_personNum);
                    startActivity(intent2);
                }

                break;
            case R.id.BJ_gaojifang:
                fangjianType = "高级";
                // 判断当前余额是否能进入高级房
                if (balance < thr_enter) {
                    Toast.makeText(getApplicationContext(), "账户余额不足" + thr_enter + "元，请充值后进入房间", Toast.LENGTH_SHORT).show();
                } else {
                    if (thr_Maxuser <= thr_rmtotal) {
                        Toast.makeText(getApplicationContext(), "当前房间已满", Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(getApplicationContext(), "欢迎进入北京28高级房",
                        // Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(Beijing28Activity.this, FangJianActivity.class);
                        intent3.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
                                .putExtra("VIP1", thr_vip1_personNum).putExtra("VIP2", thr_vip2_personNum)
                                .putExtra("VIP3", thr_vip3_personNum).putExtra("VIP4", thr_vip4_personNum);
                        startActivity(intent3);
                    }
                }

                break;
        }
    }
}
