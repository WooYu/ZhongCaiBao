package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.JiFenBean;

import java.util.List;

public class JiFenAdapter extends BaseAdapter{

	private Context context;
	private List<JiFenBean> mgnList;

	public JiFenAdapter(Context context, List<JiFenBean> mgnList) {
		
		this.context=context;
		this.mgnList=mgnList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mgnList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mgnList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.jifen_item, null);

			viewHolder.jifen_leixing = (TextView) convertView.findViewById(R.id.jifen_leixing);
			viewHolder.jifen_shijian=(TextView) convertView.findViewById(R.id.jifen_shijian);
			viewHolder.jifen_shuliang=(TextView) convertView.findViewById(R.id.jifen_shuliang);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		JiFenBean entity = mgnList.get(position);
		
		viewHolder.jifen_leixing.setText(entity.getPlaytypename());
		viewHolder.jifen_shijian.setText(entity.getCreatetime());
		viewHolder.jifen_shuliang.setText(entity.getAccountchange()+"");
		return convertView;
	}
	
	static class ViewHolder {
		TextView jifen_leixing;
		TextView jifen_shijian;
		TextView jifen_shuliang;
	}
	
}
