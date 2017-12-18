package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.HuiShuiBean;
import com.developer.lecai.bean.ShouYiBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class ShouYiAdapter extends BaseAdapter {
    private Context context;
    private List<ShouYiBean> list;

    public ShouYiAdapter(Context context, List<ShouYiBean> list) {

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
            convertView=View.inflate(context, R.layout.shouyi_listviewitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_shouyi_huiyuan= (TextView) convertView.findViewById(R.id.tv_shouyi_huiyuan);
            viewHolder.tv_shouyi_tiaojian= (TextView) convertView.findViewById(R.id.tv_shouyi_tiaojian);
            viewHolder.tv_shouyi_yongjin= (TextView) convertView.findViewById(R.id.tv_shouyi_yongjin);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_shouyi_huiyuan.setText(list.get(position).getUsercount());
        viewHolder.tv_shouyi_tiaojian.setText(list.get(position).getYl());
        viewHolder.tv_shouyi_yongjin.setText(list.get(position).getGainfee());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_shouyi_huiyuan;
        private TextView tv_shouyi_tiaojian;
        private TextView tv_shouyi_yongjin;
    }
}
