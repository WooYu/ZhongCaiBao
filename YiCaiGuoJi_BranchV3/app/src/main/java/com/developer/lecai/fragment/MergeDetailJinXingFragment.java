package com.developer.lecai.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.developer.lecai.activity.HeMaiDetailActivity;
import com.developer.lecai.adapter.MergeListViewAdapter;
import com.developer.lecai.bean.MergeBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MergeDetailJinXingFragment extends MergeDetailBaseFragment {
    private MergeListViewAdapter mergeListViewAdapter;
    private List<MergeBean> list = new ArrayList();

    @Override
    protected void initData() {
        super.initData();

        mergeListViewAdapter = new MergeListViewAdapter(getActivity(), list);
        lv_mergedetail_listview.setAdapter(mergeListViewAdapter);
        refreshData();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        refreshData();
    }

    @Override
    public void refreshData() {
        MsgController.getInstance().getHmbettdetail("", "1", new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                String state = JsonUtil.getFieldValue(s, "state");
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                LogUtils.e("上拉投注记录", biz_content);
                if (state.equals("success")) {

                    list = (List<MergeBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<MergeBean>>() {
                    }.getType());
                    mergeListViewAdapter.setData(list);

                } else if (state.equals("error")) {
                    Toast.makeText(getContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void loadData(String position) {
        MsgController.getInstance().getHmbettdetail("", position, new HttpCallback(getActivity()) {
            @Override
            public void onSuccess(Call call, String s) {

                String state = JsonUtil.getFieldValue(s, "state");
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                LogUtils.e("上拉投注记录", biz_content);
                if (state.equals("success")) {
                    List<MergeBean> mergeBeen = (List<MergeBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<MergeBean>>() {
                    }.getType());
                    if (mergeBeen == null || mergeBeen.size() == 0) {
                        Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    list.addAll(mergeBeen);
                    mergeListViewAdapter.setData(list);
                } else if (state.equals("error")) {
                    Toast.makeText(getContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void initLinstener() {
        lv_mergedetail_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String orderId = list.get(position).getOrderid();
                Intent intent = new Intent(getContext(), HeMaiDetailActivity.class);
                intent.putExtra("orderid", orderId);
                startActivity(intent);
            }
        });
    }
}
