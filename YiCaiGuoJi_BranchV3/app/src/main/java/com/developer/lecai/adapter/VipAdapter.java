package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.GongGaoBean;
import com.developer.lecai.bean.VipBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class VipAdapter extends BaseAdapter {
    private Context context;
    private List<VipBean> list;

    public VipAdapter(Context context, List<VipBean> list) {

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
            convertView=View.inflate(context, R.layout.vip_listviewitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_vip_lebal= (TextView) convertView.findViewById(R.id.tv_vip_lebal);
            viewHolder.tv_vip_min= (TextView) convertView.findViewById(R.id.tv_vip_min);
            viewHolder.tv_vip_max= (TextView) convertView.findViewById(R.id.tv_vip_max);
            viewHolder.tv_vip_money= (TextView) convertView.findViewById(R.id.tv_vip_money);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_vip_lebal.setText(list.get(position).getInfo());
        viewHolder.tv_vip_min.setText(list.get(position).getMinfee()+"");
        viewHolder.tv_vip_max.setText(list.get(position).getMaxfee()+"");
        viewHolder.tv_vip_money.setText(list.get(position).getKvalue());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_vip_lebal;
        private TextView tv_vip_min;
        private TextView tv_vip_max;
        private TextView tv_vip_money;
    }
}
