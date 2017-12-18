package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.ShouYiBean;
import com.developer.lecai.bean.ShouYiFenXiBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class ShouYiFenXiAdapter extends BaseAdapter {
    private Context context;
    private List<ShouYiFenXiBean> list;

    public ShouYiFenXiAdapter(Context context, List<ShouYiFenXiBean> list) {

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
            convertView=View.inflate(context, R.layout.item_income_analy,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_time_line= (TextView) convertView.findViewById(R.id.tv_time_line);
            viewHolder.tv_member_count= (TextView) convertView.findViewById(R.id.tv_member_count);
            viewHolder.tv_commisson= (TextView) convertView.findViewById(R.id.tv_commisson);
            viewHolder.tv_money= (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_time_line.setText(list.get(position).getReporttime());
        viewHolder.tv_member_count.setText(list.get(position).getUsercount());
        viewHolder.tv_commisson.setText(list.get(position).getGainfee());
        String yl=list.get(position).getYl();
        viewHolder.tv_money.setText(yl);
        if (Integer.parseInt(yl)>0){
            viewHolder.tv_money.setTextColor(context.getResources().getColor(R.color.title_bar_bg));
        }else{
            viewHolder.tv_money.setTextColor(context.getResources().getColor(R.color.green));
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_time_line;
        private TextView tv_member_count;
        private TextView tv_commisson;
        private TextView tv_money;
    }
}
