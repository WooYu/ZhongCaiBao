package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.HomeItemBean;
import com.developer.lecai.utils.ImageUtil;

import java.util.List;

/**
 * Created by raotf on 2017/7/3.
 */

public class HomeItemAdapter extends BaseAdapter{
    public static final String KEY_WORD = "word";
    public static final String KEY_IMG = "img";

    private Context context;
    private List<HomeItemBean> data;

    public HomeItemAdapter(Context context, List<HomeItemBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
        convertView = View.inflate(context, R.layout.adapter_home_item, null);
        ImageView ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView tvWord = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
        HomeItemBean bean = data.get(position);
        ImageUtil.setImage(bean.getImg(),ivImg);
        tvWord.setText(bean.getTitle());
        tvDesc.setText(bean.getDesc());
        return convertView;
    }
}
