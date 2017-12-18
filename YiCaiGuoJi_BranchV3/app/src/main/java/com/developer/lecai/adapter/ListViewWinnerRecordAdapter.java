package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.GongGaoBean;
import com.developer.lecai.bean.WinnerRecordBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class ListViewWinnerRecordAdapter extends BaseAdapter {
    private Context context;
    private List<WinnerRecordBean> list;

    public ListViewWinnerRecordAdapter(Context context, List<WinnerRecordBean> list) {

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
            convertView=View.inflate(context, R.layout.winnerrecord_listviewitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_winner_user= (TextView) convertView.findViewById(R.id.tv_winner_user);
            viewHolder.tv_winner_cai= (TextView) convertView.findViewById(R.id.tv_winner_cai);
            viewHolder.tv_winner_money= (TextView) convertView.findViewById(R.id.tv_winner_money);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_winner_user.setText(list.get(position).getUsername());
        viewHolder.tv_winner_cai.setText(list.get(position).getCpname());
        viewHolder.tv_winner_money.setText(list.get(position).getWinfee());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_winner_user;
        private TextView tv_winner_cai;
        private TextView tv_winner_money;
    }
}
