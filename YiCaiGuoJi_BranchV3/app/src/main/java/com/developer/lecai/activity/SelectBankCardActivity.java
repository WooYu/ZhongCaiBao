package com.developer.lecai.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lecai.R;
import com.developer.lecai.bean.NowBankCardBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.developer.lecai.http.H.Code.TiXianBankCardResult;

public class SelectBankCardActivity extends BaseActivity {

    private LinearLayout llAddBank;
    private ListView lv_bankcard_listview;
    private List<NowBankCardBean> list = new ArrayList<>();
    private NowBankCardAdapter nowBankCardAdapter;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_select_bank_card, null);
    }

    @Override
    public void initView() {
        tvTitle.setText("选择银行卡");
        lv_bankcard_listview = (ListView) findViewById(R.id.lv_bankcard_listview);
        llAddBank = (LinearLayout) findViewById(R.id.ll_add_bank);
    }

    @Override
    public void initListener() {
        llAddBank.setOnClickListener(this);
        lv_bankcard_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("SelectBankBean", list.get(position));
                intent.putExtras(bundle);
                SelectBankCardActivity.this.setResult(TiXianBankCardResult, intent);
                SelectBankCardActivity.this.finish();
            }
        });
        nowBankCardAdapter = new NowBankCardAdapter(this, list);
        lv_bankcard_listview.setAdapter(nowBankCardAdapter);
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MsgController.getInstance().getBankCard(new HttpCallback(SelectBankCardActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                Log.e("当前绑定的银行卡", s + "------" + biz_content);
                if (state.equals("success")) {
                    list = (List<NowBankCardBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<NowBankCardBean>>() {
                    }.getType());
                    nowBankCardAdapter.setData(list);
                } else if (state.equals("error")) {
                    Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_add_bank:
                intent.setClass(this, AddBankCardActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class NowBankCardAdapter extends BaseAdapter {

        private Context mContext;
        private List<NowBankCardBean> data;

        public NowBankCardAdapter(Context context, List<NowBankCardBean> data) {
            this.mContext = context;
            this.data = data;
        }

        public void setData(List<NowBankCardBean> list){
            if(null == list){
                data = new ArrayList<>();
            }else{
                this.data = list;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
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

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.adapter_now_bankcard, null);
                viewHolder.tv_bank_cardnum = (TextView) convertView.findViewById(R.id.tv_bank_cardnum);
                viewHolder.tv_bank_title = (TextView) convertView.findViewById(R.id.tv_bank_title);
                viewHolder.tv_bank_code = (TextView) convertView.findViewById(R.id.tv_bank_code);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String cardCode=data.get(position).getCardcode();
            if(cardCode.length()>12){
                cardCode=cardCode.substring(12);
            }else{
                cardCode="****";
            }
            viewHolder.tv_bank_title.setText(data.get(position).getBankname());
            viewHolder.tv_bank_code.setText(data.get(position).getBankcode());
            viewHolder.tv_bank_cardnum.setText(cardCode);
            return convertView;
        }

        public class ViewHolder {
            private TextView tv_bank_cardnum;
            private TextView tv_bank_title;
            private TextView tv_bank_code;
        }
    }
}
