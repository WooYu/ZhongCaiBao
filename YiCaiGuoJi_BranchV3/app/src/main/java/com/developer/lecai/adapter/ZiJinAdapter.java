package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.ZiJinBean;
import com.developer.lecai.entiey.MyGameNotesEntity;

import java.util.List;

public class ZiJinAdapter extends BaseAdapter{

	private Context context;
	private List<ZiJinBean> mgnList;

	public ZiJinAdapter(Context context, List<ZiJinBean> mgnList) {
		
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
			convertView = View.inflate(context, R.layout.zijin_item, null);

			viewHolder.zijin_caipiao = (TextView) convertView.findViewById(R.id.zijin_caipiao);
			viewHolder.zijin_leixing=(TextView) convertView.findViewById(R.id.zijin_leixing);
			viewHolder.zijin_jiangjin=(TextView) convertView.findViewById(R.id.zijin_jiangjin);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ZiJinBean entity = mgnList.get(position);
		
		viewHolder.zijin_caipiao.setText(entity.getPlaytypename());
		viewHolder.zijin_leixing.setText(entity.getCreatetime());
		viewHolder.zijin_jiangjin.setText(entity.getAccountchange());
		return convertView;
	}
	
	static class ViewHolder {
		TextView zijin_caipiao;
		TextView zijin_leixing;
		TextView zijin_jiangjin;
	}
	
}
