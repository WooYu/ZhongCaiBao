package com.developer.lecai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.adapter.ShouYiAdapter;
import com.developer.lecai.bean.ShouYiBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.PullToRefreshView;
import com.developer.lecai.view.TToast;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class MineOurShouYiActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener
        , PullToRefreshView.OnFooterRefreshListener {
    private View view;
    private PullToRefreshView mPullToRefreshView;
    private ListView lv_shouyi_detail;
    private int page = 1;
    private List<ShouYiBean> listAll = new ArrayList<>();
    private ShouYiAdapter shouYiAdapter;

    @Override
    public View getLayout() {

        view = View.inflate(MineOurShouYiActivity.this, R.layout.activity_mine_our_shou_yi, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("我的收益");
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.shouyi_pull_refresh_view);
        lv_shouyi_detail = (ListView) view.findViewById(R.id.lv_shouyi_detail);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
    }

    @Override
    public void initListener() {

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
        MsgController.getInstance().getShouYi(page + "", new HttpCallback(MineOurShouYiActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("我的收益", s + "------" + biz_content);
                if (state.equals("success")) {
                    List<ShouYiBean> list = (List<ShouYiBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<ShouYiBean>>() {
                    }.getType());
                    if (list.size()==0){
                        TToast.show(MineOurShouYiActivity.this,"没有更多数据",TToast.TIME_2);
                        return;
                    }
                    if (page == 1) {
                        listAll.clear();
                    }
                    listAll.addAll(list);
                    if (shouYiAdapter == null) {
                        shouYiAdapter = new ShouYiAdapter(MineOurShouYiActivity.this, listAll);
                        lv_shouyi_detail.setAdapter(shouYiAdapter);
                    } else {
                        shouYiAdapter.notifyDataSetInvalidated();
                    }
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
