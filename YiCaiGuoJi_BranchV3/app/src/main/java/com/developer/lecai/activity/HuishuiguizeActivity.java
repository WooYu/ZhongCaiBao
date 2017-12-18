package com.developer.lecai.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.HuishuiGuiZeBean;
import com.developer.lecai.utils.LogUtils;


/**
 * 玩法介绍页面
 *
 * @author Administrator
 *
 */
public class HuishuiguizeActivity extends BaseActivity {


	private TextView huishuiLable1;
	private TextView huishuiLable2;
	private TextView huishuiLable3;
	private TextView huishui1;
	private TextView huishui2;
	private TextView huishui3;
	private TextView baobenLable1;
	private TextView baoben1;

	private TextView gaoLable1;
	private TextView gao1;

	private View view;
	@Override
	public View getLayout() {
		view = View.inflate(HuishuiguizeActivity.this, R.layout.activity_huishui,null);
		initView();
		return view;
	}

	@Override
	public void initView() {
		huishuiLable1 = (TextView) view.findViewById(R.id.huishui_lable1);
		huishuiLable2 = (TextView) view.findViewById(R.id.huishui_lable2);
		huishuiLable3 = (TextView) view.findViewById(R.id.huishui_lable3);
		huishui1 = (TextView) view.findViewById(R.id.huishui1);
		huishui2 = (TextView) view.findViewById(R.id.huishui2);
		huishui3 = (TextView) view.findViewById(R.id.huishui3);

		baobenLable1 = (TextView) view.findViewById(R.id.baoben_lable1);
		baoben1 = (TextView) view.findViewById(R.id.baoben1);

		gaoLable1 = (TextView) view.findViewById(R.id.gao_lable1);
		gao1 = (TextView) view.findViewById(R.id.gao1);

	}

	@Override
	public void initListener() {


	}

	@Override
	public void initData() {

		HuishuiGuiZeBean huishuiGuiZeBean = (HuishuiGuiZeBean) getIntent().getSerializableExtra("huishuiGuiZeBean");
		LogUtils.d("回水",huishuiGuiZeBean.getState()+huishuiGuiZeBean.getBiz_content().size());
		tvTitle.setText("回水规则");
		tvTitle.setTextColor(getResources().getColor(R.color.white));
		huishuiLable1.setText("1111111");
		huishuiLable1.setText(huishuiGuiZeBean.getBiz_content().get(0).getMinfee()+"-"+huishuiGuiZeBean.getBiz_content().get(0).getMaxfee());
        huishui1.setText(huishuiGuiZeBean.getBiz_content().get(0).getBackfee()+"%");

		huishuiLable2.setText(huishuiGuiZeBean.getBiz_content().get(1).getMinfee()+"-"+huishuiGuiZeBean.getBiz_content().get(1).getMaxfee());
		huishui2.setText(huishuiGuiZeBean.getBiz_content().get(1).getBackfee()+"%");

		huishuiLable3.setText(huishuiGuiZeBean.getBiz_content().get(2).getMinfee()+"-"+huishuiGuiZeBean.getBiz_content().get(2).getMaxfee());
		huishui3.setText(huishuiGuiZeBean.getBiz_content().get(2).getBackfee()+"%");

		baobenLable1.setText(huishuiGuiZeBean.getBiz_content().get(3).getMinfee()+"-"+huishuiGuiZeBean.getBiz_content().get(3).getMaxfee());
		baoben1.setText(huishuiGuiZeBean.getBiz_content().get(3).getBackfee()+"%");

		gaoLable1.setText(huishuiGuiZeBean.getBiz_content().get(4).getMinfee()+"-"+huishuiGuiZeBean.getBiz_content().get(4).getMaxfee());
		gao1.setText(huishuiGuiZeBean.getBiz_content().get(4).getBackfee()+"%");

	}
}
