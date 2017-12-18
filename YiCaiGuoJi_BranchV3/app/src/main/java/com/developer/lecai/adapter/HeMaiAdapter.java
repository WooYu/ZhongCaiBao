package com.developer.lecai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.HeMaiBean;

import java.util.List;

public class HeMaiAdapter extends BaseAdapter{

	private Context context;
	private List<HeMaiBean> mgnList;

	public HeMaiAdapter(Context context, List<HeMaiBean> mgnList) {
		
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
			convertView = View.inflate(context, R.layout.hemai_item, null);

			viewHolder.hemai_caipiao = (TextView) convertView.findViewById(R.id.hemai_caipiao);
			viewHolder.hemai_leixing=(TextView) convertView.findViewById(R.id.hemai_leixing);
			viewHolder.hemai_jiangjin=(TextView) convertView.findViewById(R.id.hemai_jiangjin);
			viewHolder.hemai_zhuangtai=(TextView) convertView.findViewById(R.id.hemai_zhuangtai);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		HeMaiBean entity = mgnList.get(position);
		
		viewHolder.hemai_caipiao.setText(entity.getRemark());
		viewHolder.hemai_leixing.setText(entity.getNum());
		viewHolder.hemai_jiangjin.setText(entity.getWinfee());
		String status="";
		switch (entity.getStatus()){
			case 0:
				status="未开奖";
				break;
			case 1:
				status="未中奖";
				break;
			case 2:
				status="已中奖";
				break;
			case 3:
				status="撤单";
				break;
			case 5:
				status="认购中";
				break;
		}

		viewHolder.hemai_zhuangtai.setText(status);
		return convertView;
	}
	
	static class ViewHolder {
		TextView hemai_caipiao;
		TextView hemai_leixing;
		TextView hemai_jiangjin;
		TextView hemai_zhuangtai;
	}
	
}
