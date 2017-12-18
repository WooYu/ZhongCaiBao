package com.developer.lecai.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.lecai.R;

import java.util.List;

public class TestActivity extends BaseActivity {

    private ListView test_lv;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latiao_dialog);

//        test_lv = (ListView) findViewById(R.id.test_lv);

//        ShiShiCaiAdapter shiShiCaiAdapter = new ShiShiCaiAdapter(this, 5, null);
//        test_lv.setAdapter(shiShiCaiAdapter);

    }
*/
    @Override
    public View getLayout() {
        View view=View.inflate(TestActivity.this,R.layout.latiao_dialog,null);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    public class ShiShiCaiAdapter extends BaseAdapter {
        private int count;
        private Context context;
        private List<String> shiShiCaiTitle;

        public ShiShiCaiAdapter(Context context, int count, List<String> shiShiCaiTitle) {
            this.count = count;
            this.context = context;
            this.shiShiCaiTitle = shiShiCaiTitle;
        }

        @Override
        public int getCount() {
            return count;
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
            View view = View.inflate(context, R.layout.shishicai_listitem, null);
            final TextView shishicai_item_title = (TextView) view.findViewById(R.id.shishicai_item_title);
            final TextView shishicai_item_zero = (TextView) view.findViewById(R.id.shishicai_item_zero);
            shishicai_item_zero.setTag(true);
            TextView shishicai_item_first = (TextView) view.findViewById(R.id.shishicai_item_first);
            TextView shishicai_item_two = (TextView) view.findViewById(R.id.shishicai_item_two);
            TextView shishicai_item_third = (TextView) view.findViewById(R.id.shishicai_item_third);
            TextView shishicai_item_four = (TextView) view.findViewById(R.id.shishicai_item_four);
            TextView shishicai_item_five = (TextView) view.findViewById(R.id.shishicai_item_five);
            TextView shishicai_item_six = (TextView) view.findViewById(R.id.shishicai_item_six);
            TextView shishicai_item_seven = (TextView) view.findViewById(R.id.shishicai_item_seven);
            TextView shishicai_item_eight = (TextView) view.findViewById(R.id.shishicai_item_eight);
            TextView shishicai_item_nine = (TextView) view.findViewById(R.id.shishicai_item_nine);
            // shishicai_item_title.setText(shiShiCaiTitle.get(position));

            shishicai_item_zero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  shishicai_item_zero.setT
                    boolean aa= (boolean) shishicai_item_zero.getTag();
                    Log.e("aaaa",aa+"");
                    if ((boolean) shishicai_item_zero.getTag()) {
                        shishicai_item_zero.setSelected(true);
                        shishicai_item_zero.setTag(false);

                    } else {
                        shishicai_item_zero.setSelected(false);
                        shishicai_item_zero.setTag(true);
                    }
                }
            });
            shishicai_item_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shishicai_item_zero.setSelected(true);


                }
            });
            shishicai_item_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    shishicai_item_zero.setSelected(true);

                }
            });
            shishicai_item_third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shishicai_item_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shishicai_item_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shishicai_item_six.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shishicai_item_seven.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shishicai_item_eight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shishicai_item_nine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return view;
        }


    }


}
