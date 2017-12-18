package com.developer.lecai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.GongGaoBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class ListViewNoticeAdapter extends BaseAdapter {
    private Context context;
    private List<GongGaoBean> list;

    public ListViewNoticeAdapter(Context context, List<GongGaoBean> list) {

        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {

        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.notice_listviewitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_notice_title= (TextView) convertView.findViewById(R.id.tv_notice_title);
            viewHolder.tv_notice_time= (TextView) convertView.findViewById(R.id.tv_notice_time);
            viewHolder.tv_notice_info= (TextView) convertView.findViewById(R.id.tv_notice_info);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_notice_title.setText(list.get(position).getTitle());
        viewHolder.tv_notice_time.setText(list.get(position).getTime());
        viewHolder.tv_notice_info.setText(list.get(position).getTips());

        return convertView;
    }

    public class ViewHolder {
        private TextView tv_notice_title;
        private TextView tv_notice_time;
        private TextView tv_notice_info;
    }
}
