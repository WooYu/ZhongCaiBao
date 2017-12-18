package com.developer.lecai.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.HeMaiDetailBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.utils.LogUtils;
import com.developer.lecai.view.TToast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by liuwei on 2017/7/5.
 */

public class HeMaiDetailActivity extends BaseActivity {

    private View view;
    private String orderId = "";
    private TextView tv_hemaidetail_time;
    private TextView tv_lottery_name;
    private TextView tv_lottery_date;
    private TextView tv_sender;
    private ProgressBar progress_bar;
    private TextView tv_show_percent;
    private TextView tv_project_sum;
    private TextView tv_sender_buy;
    private TextView tv_exact_sum;
    private TextView tv_shopping_win;
    private TextView tv_project_detail;
    private TextView tv_total_sum;
    private TextView tv_particapte;
    private TextView tv_project_advertise;
    private TextView tv_project_number;
    private TextView tv_project_zfsnum;
    private TextView tv_project_total;
    private TextView tv_project_keep;
    private TextView tv_tax_commission;
    private HeMaiDetailBean heMaiDetailBean;
    private TextView tv_send_time;
    private ListView lv_hemaidetail_listview;
    private TextView tv_project_detail_content;
    private EditText et_chippednum;
    private TextView tv_participate;
    private List<String> qiHaoList = new ArrayList<>();
    private List<String> jinEList = new ArrayList<>();

    @Override
    public View getLayout() {

        view = View.inflate(HeMaiDetailActivity.this, R.layout.activity_hemaidetail, null);
        return view;
    }

    @Override
    public void initView() {
        tvTitle.setText("合买记录");
        orderId = getIntent().getStringExtra("orderid");
        tv_hemaidetail_time = (TextView) view.findViewById(R.id.tv_hemaidetail_time);
        tv_lottery_name = (TextView) view.findViewById(R.id.tv_lottery_name);
        tv_lottery_date = (TextView) view.findViewById(R.id.tv_lottery_date);
        tv_sender = (TextView) view.findViewById(R.id.tv_sender);
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        tv_show_percent = (TextView) view.findViewById(R.id.tv_show_percent);
        tv_project_sum = (TextView) view.findViewById(R.id.tv_project_sum);
        tv_sender_buy = (TextView) view.findViewById(R.id.tv_sender_buy);
        tv_exact_sum = (TextView) view.findViewById(R.id.tv_exact_sum);
        tv_shopping_win = (TextView) view.findViewById(R.id.tv_shopping_win);
        tv_project_detail = (TextView) view.findViewById(R.id.tv_project_detail);
        tv_total_sum = (TextView) view.findViewById(R.id.tv_total_sum);
        tv_particapte = (TextView) view.findViewById(R.id.tv_particapte);
        tv_project_advertise = (TextView) view.findViewById(R.id.tv_project_advertise);
        tv_project_number = (TextView) view.findViewById(R.id.tv_project_number);
        tv_project_zfsnum = (TextView) view.findViewById(R.id.tv_project_zfsnum);
        tv_project_total = (TextView) view.findViewById(R.id.tv_project_total);
        tv_project_keep = (TextView) view.findViewById(R.id.tv_project_keep);
        tv_tax_commission = (TextView) view.findViewById(R.id.tv_tax_commission);
        tv_send_time = (TextView) view.findViewById(R.id.tv_send_time);
        tv_project_detail_content = (TextView) view.findViewById(R.id.tv_project_detail_content);
        lv_hemaidetail_listview = (ListView) view.findViewById(R.id.lv_hemaidetail_listview);
        et_chippednum = (EditText) view.findViewById(R.id.et_chippednum);
        tv_participate = (TextView) view.findViewById(R.id.tv_participate);

    }

    @Override
    public void initListener() {
        tv_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestParticipateInChipped();
                et_chippednum.setText("");
            }
        });
    }

    @Override
    public void initData() {
        if (null == orderId || "".equals(orderId)) {
            return;
        }

        MsgController.getInstance().getHemaidetail(orderId, new HttpCallback(HeMaiDetailActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String state = JsonUtil.getFieldValue(s, "state");
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                LogUtils.e("上拉投注记录", biz_content);
                if (state.equals("success")) {
                    heMaiDetailBean = JsonUtil.parseJsonToBean(biz_content, HeMaiDetailBean.class);
                    setData(heMaiDetailBean);

                } else if (state.equals("error")) {
                    Toast.makeText(HeMaiDetailActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //参与合买
    private void requestParticipateInChipped() {
        if (null == orderId || "".equals(orderId)) {
            return;
        }

        String chippednum = et_chippednum.getText().toString().trim();
        if (null == chippednum || "".equals(chippednum)) {
            TToast.show(this, "请输入参与合买的份数", TToast.TIME_2);
            return;
        }

        MsgController.getInstance().getParticipateInChipped(orderId, chippednum,
                new HttpCallback(this) {
                    @Override
                    public void onSuccess(Call call, String s) {
                        String state = JsonUtil.getFieldValue(s, "state");
                        String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                        LogUtils.e("参与合买", biz_content);

                        if (state.equals("error")) {
                            Toast.makeText(HeMaiDetailActivity.this, biz_content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setData(HeMaiDetailBean heMaiDetailBean) {

        tv_hemaidetail_time.setText("截止：" + heMaiDetailBean.getOpentime());
        tv_lottery_name.setText(heMaiDetailBean.getCpname());
        tv_lottery_date.setText(heMaiDetailBean.getStartnum() + "期");
        tv_sender.setText(heMaiDetailBean.getUsername());
        progress_bar.setProgress((int) heMaiDetailBean.getFsjd());
        tv_show_percent.setText((int) heMaiDetailBean.getFsjd() + "%");
        tv_project_sum.setText(heMaiDetailBean.getTotalfee() + "");
        tv_sender_buy.setText(heMaiDetailBean.getMynum() + "");
        tv_exact_sum.setText(heMaiDetailBean.getShpoint() > 0 ? heMaiDetailBean.getShpoint() + "" : "无提成");
        tv_project_detail_content.setText(heMaiDetailBean.getContent());
        String status = "";
        switch (heMaiDetailBean.getStatus()) {
            case 0:
                status = "未开奖";
                break;
            case 1:
                status = "未中奖";
                break;
            case 2:
                status = "已中奖";
                break;
            case 3:
                status = "撤单";
                break;
            case 5:
                status = "认购中";
                break;
        }
        tv_shopping_win.setText(status);
        tv_project_detail.setText(heMaiDetailBean.getComnum() + "注");
        tv_total_sum.setText(heMaiDetailBean.getTotalfee() + "");
        tv_particapte.setText("参与人数：" + heMaiDetailBean.getPlayusernum() + "人");
        tv_project_advertise.setText(heMaiDetailBean.getHmremark());
        //tv_project_number.setText(heMaiDetailBean.getOrderid());
        tv_project_number.setText(heMaiDetailBean.getStartnum() + "期");
        tv_project_zfsnum.setText(heMaiDetailBean.getZfsnum()+"");
        tv_project_total.setText(heMaiDetailBean.getTotalfee() + "");
        tv_project_keep.setText(heMaiDetailBean.getBdnum() + "份");
        tv_tax_commission.setText(heMaiDetailBean.getShpoint() + "");
        tv_send_time.setText(heMaiDetailBean.getCreatetime());
        String bettcontent = heMaiDetailBean.getBettcontent();
        String[] split = bettcontent.split("\\|");
        for (String s : split) {
            String[] split1 = s.split(getString(R.string.symbol_44));
            if (split1.length > 1) {
                qiHaoList.add(split1[0]);
                jinEList.add(split1[1]);
            }
        }
        lv_hemaidetail_listview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return qiHaoList == null ? 0 : qiHaoList.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ViewHolder viewHolder = null;
                if (view == null) {
                    view = View.inflate(HeMaiDetailActivity.this, R.layout.hemaidetail_zhuihaodetail, null);
                    viewHolder = new ViewHolder();
                    viewHolder.tv_zhuihao_jine = (TextView) view.findViewById(R.id.tv_zhuihao_jine);
                    viewHolder.tv_zhuihao_qihao = (TextView) view.findViewById(R.id.tv_zhuihao_qihao);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                viewHolder.tv_zhuihao_qihao.setText(qiHaoList.get(i));
                viewHolder.tv_zhuihao_jine.setText(jinEList.get(i));
                return view;
            }
        });
    }

    public class ViewHolder {
        private TextView tv_zhuihao_qihao;
        private TextView tv_zhuihao_jine;
    }
}
