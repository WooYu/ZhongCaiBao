package com.developer.lecai.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.entiey.TrendMapEntity;

import java.util.List;

import static com.xiaomi.push.service.y.d;
import static com.xiaomi.push.service.y.e;

public class TrendMapAdapter extends BaseAdapter {

	private Context context;
	private List<TrendMapEntity> tmList;

	public TrendMapAdapter(Context context, List<TrendMapEntity> tmList) {

		this.context = context;
		this.tmList = tmList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tmList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tmList.get(position);
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
			convertView = View.inflate(context, R.layout.item_trendmap_listview, null);

			viewHolder.trendMap_qihao = (TextView) convertView.findViewById(R.id.trendMap_qihao);
			viewHolder.trendMap_lottery_result = (TextView) convertView.findViewById(R.id.trendMap_lottery_result);
			viewHolder.trendMap_big = (TextView) convertView.findViewById(R.id.trendMap_big);
			viewHolder.trendMap_small = (TextView) convertView.findViewById(R.id.trendMap_small);
			viewHolder.trendMap_single = (TextView) convertView.findViewById(R.id.trendMap_single);
			viewHolder.trendMap_both = (TextView) convertView.findViewById(R.id.trendMap_both);
			viewHolder.trendMap_big_single = (TextView) convertView.findViewById(R.id.trendMap_big_single);
			viewHolder.trendMap_small_single = (TextView) convertView.findViewById(R.id.trendMap_small_single);
			viewHolder.trendMap_big_both = (TextView) convertView.findViewById(R.id.trendMap_big_both);
			viewHolder.trendMap_small_both = (TextView) convertView.findViewById(R.id.trendMap_small_both);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		TrendMapEntity entity = tmList.get(position);
		String [] data = entity.resulttype.split(",");

		viewHolder.trendMap_qihao.setText(entity.issuenum);
		viewHolder.trendMap_lottery_result.setText(data[0]);
		for (String s : data) {
			if("单".equals(s)){
				entity.setSingle(s);
			}else if("双".equals(s)){
				entity.setDble(s);
			}else if("小".equals(s)){
				entity.setSmall(s);
			}else if("大".equals(s)){
				entity.setBig(s);
			}else if("小单".equals(s)){
				entity.setSsingle(s);
			}else if("大单".equals(s)){
				entity.setLdble(s);
			}else if("小双".equals(s)){
				entity.setSdble(s);
			}else if("大双".equals(s)){
				entity.setLdble(s);
			}
		}

		//��
		if (TextUtils.isEmpty(entity.getBig())) {
			viewHolder.trendMap_big.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_big.setText(entity.getBig());
			viewHolder.trendMap_big.setVisibility(View.VISIBLE);
		}
		//С
		if (TextUtils.isEmpty(entity.getSmall())) {
			viewHolder.trendMap_small.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_small.setText(entity.getSmall());
			viewHolder.trendMap_small.setVisibility(View.VISIBLE);
		}
		//��
		if (TextUtils.isEmpty(entity.getSingle())) {
			viewHolder.trendMap_single.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_single.setText(entity.getSingle());
			viewHolder.trendMap_single.setVisibility(View.VISIBLE);
		}
		//˫
		if (TextUtils.isEmpty(entity.getDble())) {
			viewHolder.trendMap_both.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_both.setText(entity.getDble());
			viewHolder.trendMap_both.setVisibility(View.VISIBLE);
		}
		//��
		if (TextUtils.isEmpty(entity.getLsingle())) {
			viewHolder.trendMap_big_single.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_big_single.setText(entity.getLsingle());
			viewHolder.trendMap_big_single.setVisibility(View.VISIBLE);
		}
		//С��
		if (TextUtils.isEmpty(entity.getSsingle())) {
			viewHolder.trendMap_small_single.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_small_single.setText(entity.getSsingle());
			viewHolder.trendMap_small_single.setVisibility(View.VISIBLE);
		}
		//��˫
		if (TextUtils.isEmpty(entity.getLdble())) {
			viewHolder.trendMap_big_both.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_big_both.setText(entity.getLdble());
			viewHolder.trendMap_big_both.setVisibility(View.VISIBLE);
		}
		//С˫
		if (TextUtils.isEmpty(entity.getSdble())) {
			viewHolder.trendMap_small_both.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_small_both.setText(entity.getSdble());
			viewHolder.trendMap_small_both.setVisibility(View.VISIBLE);
		}

		// System.out.println("��" + entity.getBig());
		return convertView;
	}

	static class ViewHolder {

		TextView trendMap_qihao;
		TextView trendMap_lottery_result;
		TextView trendMap_big;
		TextView trendMap_small;
		TextView trendMap_single;
		TextView trendMap_both;
		TextView trendMap_big_single;
		TextView trendMap_small_single;
		TextView trendMap_big_both;
		TextView trendMap_small_both;

	}

}
