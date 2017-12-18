package com.developer.lecai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.adapter.ShouYiAdapter;
import com.developer.lecai.adapter.ShouYiFenXiAdapter;
import com.developer.lecai.bean.ShouYiBean;
import com.developer.lecai.bean.ShouYiFenXiBean;
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

public class ShouYiFenXiActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener
        , PullToRefreshView.OnFooterRefreshListener{

    private View view;
    private PullToRefreshView lv_shouyi_listview;
    private int page = 1;
    private List<ShouYiFenXiBean> listAll = new ArrayList<>();
    private ShouYiFenXiAdapter shouYiFenXiAdapter;
    private ListView lv_shouyifenxi_detail;
    @Override
    public View getLayout() {
        view = View.inflate(ShouYiFenXiActivity.this, R.layout.activity_shou_yi_fen_xi,null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("收益分析");
        lv_shouyi_listview = (PullToRefreshView) view.findViewById(R.id.lv_shouyi_listview);
        lv_shouyifenxi_detail = (ListView) view.findViewById(R.id.lv_shouyifenxi_detail);
        lv_shouyi_listview.setOnHeaderRefreshListener(this);
        lv_shouyi_listview.setOnFooterRefreshListener(this);
        lv_shouyi_listview.setLastUpdated(new Date().toLocaleString());// 获取时间
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
        MsgController.getInstance().getShouYiFenXi(page + "", new HttpCallback(ShouYiFenXiActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("我的收益分析", s + "------" + biz_content);
                if (state.equals("success")) {
                    List<ShouYiFenXiBean> list = (List<ShouYiFenXiBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<ShouYiFenXiBean>>() {
                    }.getType());
                    if (list.size()==0){
                        TToast.show(ShouYiFenXiActivity.this,"没有更多数据",TToast.TIME_2);
                        return;
                    }
                    if (page == 1) {
                        listAll.clear();
                    }
                    listAll.addAll(list);
                    if (shouYiFenXiAdapter == null) {
                        shouYiFenXiAdapter = new ShouYiFenXiAdapter(ShouYiFenXiActivity.this, listAll);
                        lv_shouyifenxi_detail.setAdapter(shouYiFenXiAdapter);
                    } else {
                        shouYiFenXiAdapter.notifyDataSetInvalidated();
                    }
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
