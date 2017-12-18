package com.developer.lecai.adapter;

import android.content.Context;
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

public class ListViewMessageAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;

    public ListViewMessageAdapter(Context context, List<T> list) {

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
            convertView=View.inflate(context, R.layout.message_listviewitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_message_title= (TextView) convertView.findViewById(R.id.tv_message_title);
            viewHolder.tv_message_time= (TextView) convertView.findViewById(R.id.tv_message_time);
            viewHolder.tv_message_info= (TextView) convertView.findViewById(R.id.tv_message_info);
            convertView.setTag(viewHolder);
        }else{

            viewHolder= (ViewHolder) convertView.getTag();
        }
        GongGaoBean entity = (GongGaoBean) list.get(position);
        viewHolder.tv_message_title.setText(entity.getTitle());
        //viewHolder.tv_message_time.setText(entity.getTime());
        viewHolder.tv_message_info.setText(entity.getTips());


        return convertView;
    }

    public class ViewHolder {

        private TextView tv_message_title;
        private TextView tv_message_time;
        private TextView tv_message_info;
    }


}
