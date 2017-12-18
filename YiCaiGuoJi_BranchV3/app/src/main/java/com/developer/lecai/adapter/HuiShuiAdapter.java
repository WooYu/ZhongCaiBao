package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.GongGaoBean;
import com.developer.lecai.bean.HuiShuiBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class HuiShuiAdapter extends BaseAdapter {
    private Context context;
    private List<HuiShuiBean> list;

    public HuiShuiAdapter(Context context, List<HuiShuiBean> list) {

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
            convertView=View.inflate(context, R.layout.huishui_listviewitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_huishui_time= (TextView) convertView.findViewById(R.id.tv_huishui_time);
            viewHolder.tv_huishui_money= (TextView) convertView.findViewById(R.id.tv_huishui_money);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_huishui_time.setText(list.get(position).getTime());
        viewHolder.tv_huishui_money.setText(list.get(position).getMoney());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_huishui_time;
        private TextView tv_huishui_money;
    }
}
