package com.developer.lecai.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.activity.ActiveActivity;
import com.developer.lecai.activity.DaiLiHouTaiActivity;
import com.developer.lecai.activity.DaiLiKaiHuActivity;
import com.developer.lecai.activity.JiFenDuiHuanActivity;
import com.developer.lecai.activity.KeFuActivity;
import com.developer.lecai.activity.LoginActivity;
import com.developer.lecai.activity.MineHeMaiActivity;
import com.developer.lecai.activity.MineJiFenActivity;
import com.developer.lecai.activity.MineOurShouYiActivity;
import com.developer.lecai.activity.MineTouZhuActivity;
import com.developer.lecai.activity.MineVipActivity;
import com.developer.lecai.activity.MineZiJinActivity;
import com.developer.lecai.activity.NoticeActivity;
import com.developer.lecai.activity.PersonageInfoActivity;
import com.developer.lecai.activity.RechargeActivity;
import com.developer.lecai.activity.SettingActivity;
import com.developer.lecai.activity.TiXianActivity;
import com.developer.lecai.bean.LoginBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.control.UserController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.utils.wx.WxShareAndLoginUtils;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/6/19.
 */

public class UserFragment extends BaseFragment {

    private View view;
    private ImageView user_header_kefu;
    private ImageView user_header_shezhi;
    private ImageView user_header_image;
    private TextView user_header_name;
    private TextView tv_user_keyong;
    private TextView tv_user_keti;
    private TextView tv_user_yingkui;
    private RelativeLayout rl_user_chongzhi;
    private RelativeLayout rl_user_tixian;
    private RelativeLayout rl_user_jifen;
    private LinearLayout ll_user_jifen;
    private LinearLayout ll_user_touzhu;
    private LinearLayout ll_user_hemai;
    private LinearLayout ll_user_zijin;
    private LinearLayout ll_user_youhui;
    // private LinearLayout ll_user_huishui;
    private LinearLayout ll_user_message;

    private UserController userController;
    private LinearLayout ll_user_vip;
    private LinearLayout ll_user_wx;
    private LinearLayout ll_user_shouyi;
    private HttpCallback httpCallback;
    private LinearLayout ll_user_dailikaihu;
    private LinearLayout ll_user_dailihoutai;


    @Override
    public View getLayout() {
        userController = UserController.getInstance();
        view = View.inflate(getContext(), R.layout.fragment_user, null);
        return view;
    }

    public void initView() {

        user_header_kefu = (ImageView) view.findViewById(R.id.user_header_kefu);
        user_header_shezhi = (ImageView) view.findViewById(R.id.user_header_shezhi);
        user_header_image = (ImageView) view.findViewById(R.id.user_header_image);
        user_header_name = (TextView) view.findViewById(R.id.user_header_name);
        tv_user_keyong = (TextView) view.findViewById(R.id.tv_user_keyong);
        tv_user_keti = (TextView) view.findViewById(R.id.tv_user_keti);
        tv_user_yingkui = (TextView) view.findViewById(R.id.tv_user_yingkui);
        rl_user_chongzhi = (RelativeLayout) view.findViewById(R.id.rl_user_chongzhi);
        rl_user_tixian = (RelativeLayout) view.findViewById(R.id.rl_user_tixian);
        rl_user_jifen = (RelativeLayout) view.findViewById(R.id.rl_user_jifen);

        ll_user_jifen = (LinearLayout) view.findViewById(R.id.ll_user_jifen);
        ll_user_touzhu = (LinearLayout) view.findViewById(R.id.ll_user_touzhu);
        ll_user_hemai = (LinearLayout) view.findViewById(R.id.ll_user_hemai);
        ll_user_zijin = (LinearLayout) view.findViewById(R.id.ll_user_zijin);
        ll_user_youhui = (LinearLayout) view.findViewById(R.id.ll_user_youhui);
        // ll_user_huishui = (LinearLayout) view.findViewById(R.id.ll_user_huishui);
        ll_user_message = (LinearLayout) view.findViewById(R.id.ll_user_message);

        ll_user_vip = (LinearLayout) view.findViewById(R.id.ll_user_vip);
        ll_user_wx = (LinearLayout) view.findViewById(R.id.ll_user_wx);
        ll_user_shouyi = (LinearLayout) view.findViewById(R.id.ll_user_shouyi);
        ll_user_dailikaihu = (LinearLayout) view.findViewById(R.id.ll_user_dailikaihu);
        ll_user_dailihoutai = (LinearLayout) view.findViewById(R.id.ll_user_dailihoutai);
    }


    public void initLinstener() {

        user_header_kefu.setOnClickListener(this);
        user_header_shezhi.setOnClickListener(this);
        user_header_image.setOnClickListener(this);
        rl_user_chongzhi.setOnClickListener(this);
        rl_user_tixian.setOnClickListener(this);
        rl_user_jifen.setOnClickListener(this);
        ll_user_jifen.setOnClickListener(this);
        ll_user_touzhu.setOnClickListener(this);
        ll_user_hemai.setOnClickListener(this);
        ll_user_zijin.setOnClickListener(this);
        ll_user_youhui.setOnClickListener(this);
        // ll_user_huishui.setOnClickListener(this);
        ll_user_message.setOnClickListener(this);

        ll_user_vip.setOnClickListener(this);
        ll_user_wx.setOnClickListener(this);
        ll_user_shouyi.setOnClickListener(this);
        user_header_name.setOnClickListener(this);

        ll_user_dailikaihu.setOnClickListener(this);
        ll_user_dailihoutai.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getUserInfo();
    }


    @Override
    public void onResume() {
        super.onResume();
        LoginBean loginBean = userController.getLoginBean();
        if (null != loginBean) {
            String username = loginBean.getUsername();
            if (null != username && username.length() == 11) {
                user_header_name.setText(username.substring(0,3) + "****"+username.substring(7,11));
            }else{
                user_header_name.setText(username);
            }

            if (loginBean.getType() == 1) {
                ll_user_dailikaihu.setVisibility(View.VISIBLE);
                ll_user_dailihoutai.setVisibility(View.VISIBLE);
            } else {
                ll_user_dailikaihu.setVisibility(View.GONE);
                ll_user_dailihoutai.setVisibility(View.GONE);
            }
        } else {
            user_header_name.setText("登录/注册");
            ll_user_dailikaihu.setVisibility(View.GONE);
            ll_user_dailihoutai.setVisibility(View.GONE);
        }
        getUserInfo();
    }

    @Override
    protected void clickEvent(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.user_header_kefu:
                //客服
                intent.setClass(getContext(), KeFuActivity.class);
                startActivity(intent);
                break;
            case R.id.user_header_shezhi:
                //设置
                if (UserController.getInstance().getLoginBean() != null) {
                    intent.setClass(getContext(), SettingActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.user_header_image:
                //头像
                if (UserController.getInstance().getLoginBean() != null) {
                    intent.setClass(getContext(), PersonageInfoActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_user_chongzhi:
                //充值
                intent.setClass(getContext(), RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_user_tixian:
                //提现
                intent.setClass(getContext(), TiXianActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_user_jifen:
                //积分兑换
                intent.setClass(getContext(), JiFenDuiHuanActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_jifen:
                //积分明细
                intent.setClass(getContext(), MineJiFenActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_touzhu:
                //投注记录
                intent.setClass(getContext(), MineTouZhuActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_hemai:
                //合买记录
                intent.setClass(getContext(), MineHeMaiActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_zijin:
                //资金明细
                intent.setClass(getContext(), MineZiJinActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_youhui:
                //优惠活动
                intent.setClass(getContext(), ActiveActivity.class);
                startActivity(intent);
                break;
           /* case R.id.ll_user_huishui:
                //我的回水
                intent.setClass(getContext(), HuiShuiActivity.class);
                break;*/
            case R.id.ll_user_message:
                //消息公告
                intent.setClass(getContext(), NoticeActivity.class);
                startActivity(intent);
                // getUserInfo();
                break;
            case R.id.ll_user_vip:
                //vip分享
                intent.setClass(getContext(), MineVipActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_wx:
                //微信分享
                String url = "http://www.lsls188.com";
                String title = getString(R.string.app_name);
                String desc = "投资2元钱，赚来500万";
                WxShareAndLoginUtils.WxUrlShare(url, title, desc, 0);
                break;
            case R.id.ll_user_shouyi:
                //我的收益
                intent.setClass(getContext(), MineOurShouYiActivity.class);
                startActivity(intent);
                break;
            case R.id.user_header_name:
                if (UserController.getInstance().getLoginBean() == null) {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_user_dailikaihu:
                intent.setClass(getContext(), DaiLiKaiHuActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_dailihoutai:
                intent.setClass(getContext(), DaiLiHouTaiActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取用户信息，包括用户名、头像、余额等
     */
    private void getUserInfo() {
        /*Map<String, String> params = new HashMap<>();
        new HttpRequest.Builder()
                .build()
                .post(H.URL.Regist, new HttpCallback() {
                    @Override
                    public void onSuccess(Call call, String s) {
                        // 成功处理
                    }
                });*/
        if (httpCallback == null) {
            httpCallback = new HttpCallback(getActivity()) {
                @Override
                public void onSuccess(Call call, String s) {
                    String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                    String state = JsonUtil.getFieldValue(s, "state");
                    Log.e("回执单", s + "------" + biz_content);
                    if (state.equals("success")) {
                        String accountfee = JsonUtil.getStringValue(biz_content, "accountfee");
                        String discountfee = JsonUtil.getStringValue(biz_content, "discountfee");
                        String intefee = JsonUtil.getStringValue(biz_content, "intefee");
                        String profitfee = JsonUtil.getStringValue(biz_content,"profitfee");
                        tv_user_keyong.setText(MyUtil.formatAmount(accountfee));
                        tv_user_keti.setText(MyUtil.formatAmount(accountfee));
                        tv_user_yingkui.setText(MyUtil.formatAmount(profitfee));
                    } else if (state.equals("error")) {
                        Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }
        MsgController.getInstance().getMoney(httpCallback);
    }
}
