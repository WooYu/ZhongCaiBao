package com.developer.lecai.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.bean.BankCardBean;
import com.developer.lecai.control.MsgController;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.utils.ImageUtil;
import com.developer.lecai.utils.JsonUtil;
import com.developer.lecai.view.TToast;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.Call;

import static com.developer.lecai.http.H.Code.BankCardResult;

public class ChooseBankCardActivity extends BaseActivity {

    private ListView lvBankCard;
    private List<BankCardBean> list;

    @Override
    public View getLayout() {
        return View.inflate(this, R.layout.activity_choose_bank_card, null);
    }

    @Override
    public void initView() {
        tvTitle.setText("选择银行");
        lvBankCard = (ListView) findViewById(R.id.lv_bank_card);

    }

    @Override
    public void initListener() {
        lvBankCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("BankBean", list.get(position));
                intent.putExtras(bundle);
                ChooseBankCardActivity.this.setResult(BankCardResult, intent);
                ChooseBankCardActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {

        MsgController.getInstance().getBannerList(new HttpCallback(ChooseBankCardActivity.this) {
            @Override
            public void onSuccess(Call call, String s) {
                Log.e("银行列表", s);

                String biz_content = JsonUtil.getFieldValue(s, "biz_content");
                String state = JsonUtil.getFieldValue(s, "state");
                if ("success".equals(state)) {
                    list = (List<BankCardBean>) JsonUtil.parseJsonToList(biz_content, new TypeToken<List<BankCardBean>>() {
                    }.getType());
                    Log.e("银行列表", list.get(0).getBankDesc());
                    lvBankCard.setAdapter(new ChooseBankCardAdapter(ChooseBankCardActivity.this, list));
                } else {
                    TToast.show(ChooseBankCardActivity.this, biz_content, TToast.TIME_2);
                }
            }
        });
    }

    private class ChooseBankCardAdapter extends BaseAdapter {

        private Context mContext;
        private List<BankCardBean> data;

        public ChooseBankCardAdapter(Context context, List<BankCardBean> data) {
            this.mContext = context;
            this.data = data;
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
                convertView = View.inflate(mContext, R.layout.adapter_choose_bankcard, null);
                viewHolder.iv_bank_img = (ImageView) convertView.findViewById(R.id.iv_bank_img);
                viewHolder.tv_bank_title = (TextView) convertView.findViewById(R.id.tv_bank_title);
                viewHolder.tv_bank_code = (TextView) convertView.findViewById(R.id.tv_bank_code);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageUtil.setImage(data.get(position).getBankImg(), viewHolder.iv_bank_img);
            viewHolder.tv_bank_title.setText(data.get(position).getBankTitle());
            viewHolder.tv_bank_code.setText(data.get(position).getBankDesc());
            return convertView;
        }

        public class ViewHolder {
            private ImageView iv_bank_img;
            private TextView tv_bank_title;
            private TextView tv_bank_code;
        }
    }
}
