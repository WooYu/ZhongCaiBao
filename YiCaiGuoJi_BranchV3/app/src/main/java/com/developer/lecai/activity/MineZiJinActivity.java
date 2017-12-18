package com.developer.lecai.activity;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.developer.lecai.R;
import com.developer.lecai.adapter.JiFenAdapter;
import com.developer.lecai.adapter.TouZhuAdapter;
import com.developer.lecai.adapter.ZiJinAdapter;
import com.developer.lecai.bean.ZiJinBean;
import com.developer.lecai.control.UserController;
import com.developer.lecai.dialog.TouZhuDialog;
import com.developer.lecai.dialog.ZiJinDialog;
import com.developer.lecai.entiey.MyGameNotesEntity;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.utils.MyUtil;
import com.developer.lecai.utils.SharedPreferencesUtils;
import com.developer.lecai.utils.XyMyContent;
import com.developer.lecai.view.PullToRefreshView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 资金明细页面
 *
 * @author Administrator
 */

public class MineZiJinActivity extends BaseActivity
        implements PullToRefreshView.OnHeaderRefreshListener
        , PullToRefreshView.OnFooterRefreshListener {

    private static final int BETNOTES = 0;
    private static final int PULLREFRESH = 1;
    private ListView bet_note_listview;
    private List<ZiJinBean> betList=new ArrayList<>();
    private ZiJinAdapter betAdapter;
    private ImageView touzhu_back;
    private String account;
    private String imei;
    private Request<JSONObject> request;
    private RequestQueue betQueue;
    private String wanfaType;
    /**
     * 自定义刷新
     */
    private PullToRefreshView mPullToRefreshView;
    /**
     * 等待动画
     */
    ProgressDialog p;
    private TextView tv_betting_filtrate;
    private View view;
    private ZiJinDialog touZhuJiLuDialog;
    private int status;
    private String signature;
    private String caiType;
    private String startTime;
    private String endTime;
    private String zhuangTai;

    @Override
    public View getLayout() {
        view = View.inflate(MineZiJinActivity.this, R.layout.activity_betting_record, null);
        return view;
    }

    @Override
    public void initView() {
        //hideTitleBar();
        tvTitle.setText("资金明细");
        setRightTitleBar("筛选");
        betQueue = NoHttp.newRequestQueue();
        // 用户账号
        account = UserController.getInstance().getLoginBean().getLoginname();
        signature = MyUtil.getSignature(account);
        // 玩法类型
        wanfaType = SharedPreferencesUtils.getValue(getApplicationContext(), "wanfaType");
        // imei号
        imei = MyUtil.getIMEI(getApplicationContext());

        caiType = "-1";
        startTime = "-1";
        endTime = "-1";
        zhuangTai="-1";
        betNotesRequest(caiType, startTime, endTime,zhuangTai);
        // 上拉刷新 下拉加载
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
        //tv_betting_filtrate = (TextView) findViewById(R.id.tv_betting_filtrate);

        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
        bet_note_listview = (ListView) findViewById(R.id.bet_note_listview);
        //touzhu_back = (ImageView) findViewById(R.id.touzhu_back);
        status = MyUtil.getStatusBarHeight(this);
    }

    @Override
    public void initListener() {
        //touzhu_back.setOnClickListener(this);
        //	tv_betting_filtrate.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        touZhuJiLuDialog = new ZiJinDialog(MineZiJinActivity.this, status);
        touZhuJiLuDialog.setOnClickZJMXQueRenListener(new ZiJinDialog.OnClickZJMXQueRenListener() {
            @Override
            public void onClickQueRen(HashMap<String, String> hashMap) {
                startTime = hashMap.get("KSSJ");
                endTime = hashMap.get("JSSJ");
                String FL = hashMap.get("FL");
                String ZT = hashMap.get("ZT");
                if ("0".equals(startTime)) {
                    if ("0".equals(endTime)) {
                        startTime = "-1";
                        endTime = "-1";
                    } else {
                        startTime = endTime;
                    }
                } else {
                    if ("0".equals(endTime)) {
                        endTime = startTime;
                    } else {

                    }
                }
                if ("0".equals(ZT)) {
                    zhuangTai = "-1";
                } else if ("1".equals(ZT)) {
                    zhuangTai = "8";
                } else if ("2".equals(ZT)) {
                    zhuangTai = "0";
                }
                if ("0".equals(FL)) {
                    caiType = "-1";
                } else if ("1".equals(FL)) {
                    caiType = "12";
                } else if ("2".equals(FL)) {
                    caiType = "23";
                } else if ("3".equals(FL)) {
                    caiType = "3";
                } else if ("4".equals(FL)) {
                    caiType = "22";
                } else if ("5".equals(FL)) {
                    caiType = "28";
                } else if ("6".equals(FL)) {
                    caiType = "-1";
                } else if ("7".equals(FL)) {
                    caiType = "26";
                } else if ("8".equals(FL)) {
                    caiType = "4";
                } else if ("9".equals(FL)) {
                    caiType = "27";
                } else if ("10".equals(FL)) {
                    caiType = "8";
                } else if ("11".equals(FL)) {
                    caiType = "7";
                } else if ("12".equals(FL)) {
                    caiType = "9";
                } else if ("13".equals(FL)) {
                    caiType = "1";
                } else if ("14".equals(FL)) {
                    caiType = "24";
                } else if ("15".equals(FL)) {
                    caiType = "11";
                } else if ("16".equals(FL)) {
                    caiType = "2";
                } else if ("17".equals(FL)) {
                    caiType = "15";
                } else if ("18".equals(FL)) {
                    caiType = "29";
                } else if ("19".equals(FL)) {
                    caiType = "21";
                } else if ("20".equals(FL)) {
                    caiType = "25";
                } else if ("21".equals(FL)) {
                    caiType = "20";
                }
                /*String time="";
                String status="";
				String caiPiao="";*/
                touZhuJiLuDialog.dismiss();
                betNotesRequest(caiType, startTime, endTime, zhuangTai);
                i = 2;
            }
        });
    }

    @Override
    public void initData() {
    }

    private void betNotesRequest(String caiType, String startTime, String endTime, String zhuangTai) {
        request = NoHttp.createJsonObjectRequest(XyMyContent.CHANGE, RequestMethod.POST);
        request.add("account", account);
        request.add("playtype", caiType);
        request.add("page", "1");
        request.add("imei", imei);
        request.add("signature", signature);
        request.add("start", startTime);
        request.add("end", endTime);
        request.add("status", zhuangTai);
        System.out.println(account);
        System.out.println(wanfaType);
        System.out.println(imei);
        betQueue.add(BETNOTES, request, responseListener);
    }

    OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {
        @Override
        public void onSucceed(int what, Response<JSONObject> response) {
            switch (what) {
                case BETNOTES:
                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");
                        LogUtils.e("投注记录", biz_content);
                        if (state.equals("success")) {

                            betList = JSON.parseArray(biz_content, ZiJinBean.class);

                            betAdapter = new ZiJinAdapter(getApplicationContext(), betList);

                            bet_note_listview.setAdapter(betAdapter);
                        } else if (state.equals("error")) {
                            betList.clear();
                            if (betAdapter==null) {
                                betAdapter = new ZiJinAdapter(getApplicationContext(), betList);
                                bet_note_listview.setAdapter(betAdapter);
                            }else{
                                betAdapter.notifyDataSetInvalidated();
                            }
                            Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case PULLREFRESH:

                    JSONArray jsonarray;
                    try {
                        String state = response.get().getString("state");
                        String biz_content = response.get().getString("biz_content");
                        LogUtils.e("上拉投注记录", biz_content);
                        if (state.equals("success")) {
                            jsonarray = response.get().getJSONArray("biz_content");
                            ZiJinBean a;
                            if (jsonarray.length() == 0) {
                                Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i = 0; i < jsonarray.length(); i++) {
                                a = new ZiJinBean();
                                a = JSON.parseObject(jsonarray.getString(i), ZiJinBean.class);
                                betList.add(a);
                            }
                            betAdapter.notifyDataSetChanged();
                        } else if (state.equals("error")) {
                            Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onStart(int what) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinish(int what) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode,
                             long networkMillis) {
            // TODO Auto-generated method stub

        }
    };
    private int i = 2;

    // 上拉加载
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                mPullToRefreshView.onFooterRefreshComplete();
                PullTorequest(i, caiType, startTime, endTime,zhuangTai);
                i++;
                System.out.println("i的值" + i);
            }

        }, 3000);

    }

    protected void PullTorequest(int i, String caiType, String startTime, String endTime,String zhuangTai) {

        request = NoHttp.createJsonObjectRequest(XyMyContent.CHANGE, RequestMethod.POST);

        request.add("account", account);
        request.add("playtype", caiType);
        request.add("page", i+"");
        request.add("imei", imei);
        request.add("signature", signature);
        request.add("start", startTime);
        request.add("end", endTime);
        request.add("status", zhuangTai);
        betQueue.add(PULLREFRESH, request, responseListener);
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onHeaderRefreshComplete("更新于:" + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
                betNotesRequest(caiType, startTime, endTime,zhuangTai);
                // Toast.makeText(this, "数据刷新完成!", 0).show();
            }

        }, 3000);

    }

    private void setListener() {

        //tv_right.
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_right:
                if (touZhuJiLuDialog == null) {
                    touZhuJiLuDialog = new ZiJinDialog(MineZiJinActivity.this, status);
                }
                touZhuJiLuDialog.show();
                break;
            default:
                break;
        }

    }

}
