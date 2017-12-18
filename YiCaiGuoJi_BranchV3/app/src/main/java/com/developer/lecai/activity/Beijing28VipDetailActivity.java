package com.developer.lecai.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.lecai.R;

import java.util.ArrayList;
import java.util.List;

public class Beijing28VipDetailActivity extends BaseActivity {

    private ListView  lvQishu;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_beijing28_vip_detail, null);
    }

    @Override
    public void initView() {
        hideTitleBar();
        lvQishu = (ListView) findViewById(R.id.lv_qishu);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        List<String> data = new ArrayList<>();
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        data.add("test");
        lvQishu.setAdapter(new QishuAdapter(this, data));
    }

    private class QishuAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> data;

        public QishuAdapter(Context context, List<String> data) {
            this.mContext = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                convertView = View.inflate(mContext, R.layout.adapter_qishu, null);
                holder = new ViewHolder();
                holder.tvQishu = (TextView) convertView.findViewById(R.id.tv_qishu);
                holder.tvQishuContext = (TextView) convertView.findViewById(R.id.tv_qishu_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }

        public class ViewHolder {
            public TextView tvQishu;
            public TextView tvQishuContext;
        }
    }
}
