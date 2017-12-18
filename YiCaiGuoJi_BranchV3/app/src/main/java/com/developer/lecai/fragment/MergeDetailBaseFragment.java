package com.developer.lecai.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.developer.lecai.R;
import com.developer.lecai.activity.HeMaiDetailActivity;
import com.developer.lecai.adapter.MergeListViewAdapter;
import com.developer.lecai.bean.MergeBean;
import com.developer.lecai.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liuwei on 2017/7/4.
 */

public abstract class MergeDetailBaseFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener
        , PullToRefreshView.OnFooterRefreshListener {

    private View view;
    protected ListView lv_mergedetail_listview;

    private PullToRefreshView prv_merge_refresh_view;
    private int position = 1;


    @Override
    protected View getLayout() {

        view = View.inflate(getContext(), R.layout.fragment_mergedetail_base, null);
        return view;
    }

    @Override
    protected void initView() {

        prv_merge_refresh_view = (PullToRefreshView) view.findViewById(R.id.prv_merge_refresh_view);
        prv_merge_refresh_view.setOnHeaderRefreshListener(this);
        prv_merge_refresh_view.setOnFooterRefreshListener(this);
        prv_merge_refresh_view.setLastUpdated(new Date().toLocaleString());// 获取时间
        lv_mergedetail_listview = (ListView) view.findViewById(R.id.lv_mergedetail_listview);
    }

    @Override
    protected void initLinstener() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void clickEvent(View view) {

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        prv_merge_refresh_view.postDelayed(new Runnable() {

            @Override
            public void run() {
                prv_merge_refresh_view.onFooterRefreshComplete();
//                PullTorequest(i, caiType, startTime, endTime);
//                i++;
//                System.out.println("i的值" + i);
                position++;
                loadData(position + "");
            }

        }, 3000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        prv_merge_refresh_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                prv_merge_refresh_view.onHeaderRefreshComplete("更新于:" + Calendar.getInstance().getTime().toLocaleString());
                prv_merge_refresh_view.onHeaderRefreshComplete();
                //  betNotesRequest(caiType, startTime, endTime);
                // Toast.makeText(this, "数据刷新完成!", 0).show();
                position=1;
                refreshData();
            }

        }, 3000);
    }


    public abstract void refreshData();

    public abstract void loadData(String position);

}
