package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.ShouYiFenXiBean;
import com.developer.lecai.bean.UserInfoBean;

import java.util.List;

/**
 * Created by liuwei on 2017/7/3.
 */

public class UserInfoAdapter extends BaseAdapter {
    private Context context;
    private List<UserInfoBean> list;

    public UserInfoAdapter(Context context, List<UserInfoBean> list) {

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
            convertView=View.inflate(context, R.layout.item_userinfo,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_userInfo_account= (TextView) convertView.findViewById(R.id.tv_userInfo_account);
            viewHolder.tv_userInfo_money= (TextView) convertView.findViewById(R.id.tv_userInfo_money);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_userInfo_account.setText(list.get(position).getUsername());
        viewHolder.tv_userInfo_money.setText(list.get(position).getYl());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_userInfo_account;
        private TextView tv_userInfo_money;
    }
}