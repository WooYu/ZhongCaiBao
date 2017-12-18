package com.developer.lecai.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.M;
import com.developer.lecai.R;
import com.developer.lecai.adapter.ShouYiFenXiAdapter;
import com.developer.lecai.adapter.UserInfoAdapter;
import com.developer.lecai.bean.ShouYiFenXiBean;
import com.developer.lecai.bean.UserInfoBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.DateUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.MonPickerDialog;
import com.developer.lecai.view.PullToRefreshView;
import com.developer.lecai.view.TToast;
import com.developer.lecai.view.YearPickerDialog;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class UserInfoActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener
        , PullToRefreshView.OnFooterRefreshListener{

    private View view;
    private TextView tv_userinfo_year;
    private TextView tv_userinfo_month;
    private LinearLayout iv_userinfo_year;
    private LinearLayout iv_userinfo_month;
    Calendar calendar = Calendar.getInstance();
    private YearPickerDialog yearPickerDialog;
    private MonPickerDialog monPickerDialog;
    private String subYear="2017";
    private PullToRefreshView lv_userInfo_listview;
    private ListView lv_userInfo_detail;
    private int page = 1;
    private List<UserInfoBean> listAll = new ArrayList<>();
    private UserInfoAdapter userInfoAdapter;
    private String subMonth="";

    @Override
    public View getLayout() {
        view = View.inflate(UserInfoActivity.this, R.layout.activity_user_info, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("用户信息");
        tv_userinfo_year = (TextView) view.findViewById(R.id.tv_userinfo_year);
        tv_userinfo_month = (TextView) view.findViewById(R.id.tv_userinfo_month);

        iv_userinfo_year = (LinearLayout) view.findViewById(R.id.iv_userinfo_year);
        iv_userinfo_month = (LinearLayout) view.findViewById(R.id.iv_userinfo_month);

        lv_userInfo_listview = (PullToRefreshView) view.findViewById(R.id.lv_userInfo_listview);
        lv_userInfo_detail = (ListView) view.findViewById(R.id.lv_userInfo_detail);

        lv_userInfo_listview.setOnHeaderRefreshListener(this);
        lv_userInfo_listview.setOnFooterRefreshListener(this);
        lv_userInfo_listview.setLastUpdated(new Date().toLocaleString());// 获取时间
    }

    @Override
    public void initListener() {
        iv_userinfo_year.setOnClickListener(this);
        iv_userinfo_month.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_userinfo_year:
                if (yearPickerDialog == null) {
                    yearPickerDialog = new YearPickerDialog(UserInfoActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            subYear = DateUtil.clanderTodatetime(calendar, "yyyy");
                            tv_userinfo_year.setText(subYear);
                            page = 1;
                            loadData();
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                }
                yearPickerDialog.show();
                break;
            case R.id.iv_userinfo_month:
                if (monPickerDialog == null) {
                    monPickerDialog = new MonPickerDialog(UserInfoActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(Calendar.MONTH, monthOfYear);
                            subMonth = DateUtil.clanderTodatetime(calendar, "MM");
                            tv_userinfo_month.setText(subMonth);
                            page = 1;
                            loadData();
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                }
                monPickerDialog.show();
                break;
        }
    }

    @Override
    public void initData() {
        loadData();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        page++;
        loadData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        page = 1;
        loadData();
    }

    private void loadData() {
        MsgController.getInstance().getUserInfo(page + "",subYear,subMonth, new HttpCallback(UserInfoActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("我的收益分析", s + "------" + biz_content);
                if (state.equals("success")) {
                    List<UserInfoBean> list = (List<UserInfoBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<UserInfoBean>>() {
                    }.getType());
                    if (list.size()==0){
                        TToast.show(UserInfoActivity.this,"没有更多数据",TToast.TIME_2);
                        return;
                    }
                    if (page == 1) {
                        listAll.clear();
                    }
                    listAll.addAll(list);
                    if (userInfoAdapter == null) {
                        userInfoAdapter = new UserInfoAdapter(UserInfoActivity.this, listAll);
                        lv_userInfo_detail.setAdapter(userInfoAdapter);
                    } else {
                        userInfoAdapter.notifyDataSetInvalidated();
                    }
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
