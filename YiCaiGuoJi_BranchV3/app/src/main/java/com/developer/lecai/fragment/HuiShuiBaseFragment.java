package com.developer.lecai.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.developer.lecai.R;
import com.developer.lecai.adapter.HeMaiAdapter;
import com.developer.lecai.adapter.HuiShuiAdapter;
import com.developer.lecai.bean.HeMaiBean;
import com.developer.lecai.bean.HuiShuiBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.view.PullToRefreshView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/3.
 */

public abstract class HuiShuiBaseFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener
        , PullToRefreshView.OnFooterRefreshListener{

    private View view;
    private ListView lv_huishui_detail;
    private PullToRefreshView mPullToRefreshView;
    private int i=1;
    private String daTing;
    private List<HuiShuiBean> list=new ArrayList<>();
    private HuiShuiAdapter huiShuiAdapter;

    @Override
    protected View getLayout() {

        view = View.inflate(getContext(), R.layout.fragment_huishui,null);
        return view;
    }

    @Override
    protected void initView() {
        lv_huishui_detail = (ListView) view.findViewById(R.id.lv_huishui_detail);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
        daTing=setDaTing();
    }

    @Override
    protected void initLinstener() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        shuaXin();
    }

    @Override
    protected void clickEvent(View view) {

    }

    public void setData(){


    }

//上拉加载
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                mPullToRefreshView.onFooterRefreshComplete();
                i++;
                jiaZai();
                System.out.println("i的值" + i);
            }

        }, 3000);
    }
//下拉刷新
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onHeaderRefreshComplete("更新于:" + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
                shuaXin();
                // Toast.makeText(this, "数据刷新完成!", 0).show();
            }

        }, 3000);
    }

    private void shuaXin(){

        MsgController.getInstance().getHuiShui("1", daTing, new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                String state = JsonUtil.getFieldValue(s,"state");
                String biz_content = JsonUtil.getFieldValue(s,"biz_content");
                LogUtils.e("我的回水", biz_content);
                if (state.equals("success")) {
                    list = (List<HuiShuiBean>) JsonUtil.parseJsonToList(biz_content,new TypeToken<List<HuiShuiBean>>(){}.getType());
                   if (huiShuiAdapter==null) {
                       huiShuiAdapter = new HuiShuiAdapter(getActivity(), list);
                       lv_huishui_detail.setAdapter(huiShuiAdapter);
                   }else{
                       huiShuiAdapter.notifyDataSetInvalidated();
                   }
                } else if (state.equals("error")) {
                    Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void jiaZai(){

        MsgController.getInstance().getHuiShui(i+"", daTing, new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                String state = JsonUtil.getFieldValue(s,"state");
                String biz_content = JsonUtil.getFieldValue(s,"biz_content");
                LogUtils.e("我的回水", biz_content);
                if (state.equals("success")) {
                    List<HuiShuiBean> ziList = (List<HuiShuiBean>) JsonUtil.parseJsonToList(biz_content,new TypeToken<List<HuiShuiBean>>(){}.getType());
                   if (ziList!=null) {
                       list.addAll(ziList);
                       if (huiShuiAdapter == null) {
                           huiShuiAdapter = new HuiShuiAdapter(getActivity(), list);
                           lv_huishui_detail.setAdapter(huiShuiAdapter);
                       } else {
                           huiShuiAdapter.notifyDataSetInvalidated();
                       }
                   }
                } else if (state.equals("error")) {
                    Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
    protected abstract String setDaTing();

}
