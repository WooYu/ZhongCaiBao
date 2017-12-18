package com.developer.lecai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.utils.SharedPreferencesUtils;

import java.util.List;

import static com.developer.lecai.utils.SPUtils.getContext;

/**
 * Created by liuwei on 2017/8/1.
 */

public class ShiShiCaiAdapter extends BaseAdapter {
    private Context context;
    int[][] list;
    List<String> titleList;
    String cpcode;

    private OnClickNumberListener onClickNumberListener;

    public void setOnClickNumberListener(OnClickNumberListener onClickNumberListener) {
        this.onClickNumberListener = onClickNumberListener;
    }

    public interface OnClickNumberListener {
        void onClickNumber();
    }


    public ShiShiCaiAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        return list == null ? 0 : list.length;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        cpcode = SharedPreferencesUtils.getValue(getContext(), "GameTitle");
        if (cpcode.indexOf("北京") != -1) {
            View view = View.inflate(context, R.layout.shishicai_bjsc_listitem, null);
            LinearLayout ly_zero = (LinearLayout) view.findViewById(R.id.ly_zero);
            final TextView shishicai_item_title = (TextView) view.findViewById(R.id.shishicai_item_title);
            final TextView shishicai_item_zero = (TextView) view.findViewById(R.id.shishicai_item_zero);
            final TextView shishicai_item_first = (TextView) view.findViewById(R.id.shishicai_item_first);
            final TextView shishicai_item_two = (TextView) view.findViewById(R.id.shishicai_item_two);
            final TextView shishicai_item_third = (TextView) view.findViewById(R.id.shishicai_item_third);
            final TextView shishicai_item_four = (TextView) view.findViewById(R.id.shishicai_item_four);
            final TextView shishicai_item_five = (TextView) view.findViewById(R.id.shishicai_item_five);
            final TextView shishicai_item_six = (TextView) view.findViewById(R.id.shishicai_item_six);
            final TextView shishicai_item_seven = (TextView) view.findViewById(R.id.shishicai_item_seven);
            final TextView shishicai_item_eight = (TextView) view.findViewById(R.id.shishicai_item_eight);
            final TextView shishicai_item_nine = (TextView) view.findViewById(R.id.shishicai_item_nine);
            final TextView shishicai_item_ten = (TextView) view.findViewById(R.id.shishicai_item_ten);

            TextView shishicai_item_zero1 = (TextView) view.findViewById(R.id.shishicai_item_zero1);
            TextView shishicai_item_first1 = (TextView) view.findViewById(R.id.shishicai_item_first1);
            TextView shishicai_item_two1 = (TextView) view.findViewById(R.id.shishicai_item_two1);
            TextView shishicai_item_third1 = (TextView) view.findViewById(R.id.shishicai_item_third1);
            TextView shishicai_item_four1 = (TextView) view.findViewById(R.id.shishicai_item_four1);
            TextView shishicai_item_five1 = (TextView) view.findViewById(R.id.shishicai_item_five1);
            TextView shishicai_item_six1 = (TextView) view.findViewById(R.id.shishicai_item_six1);
            TextView shishicai_item_seven1 = (TextView) view.findViewById(R.id.shishicai_item_seven1);
            TextView shishicai_item_eight1 = (TextView) view.findViewById(R.id.shishicai_item_eight1);
            TextView shishicai_item_nine1 = (TextView) view.findViewById(R.id.shishicai_item_nine1);
            TextView shishicai_item_ten1 = (TextView) view.findViewById(R.id.shishicai_item_ten1);
            ly_zero.setVisibility(View.GONE);


            TextView tv_ffc_quan = (TextView) view.findViewById(R.id.tv_ffc_quan);
            TextView tv_ffc_da = (TextView) view.findViewById(R.id.tv_ffc_da);
            TextView tv_ffc_xiao = (TextView) view.findViewById(R.id.tv_ffc_xiao);
            TextView tv_ffc_dan = (TextView) view.findViewById(R.id.tv_ffc_dan);
            TextView tv_ffc_shuang = (TextView) view.findViewById(R.id.tv_ffc_shuang);
            TextView tv_ffc_qing = (TextView) view.findViewById(R.id.tv_ffc_qing);

            String title = titleList.get(position);
            shishicai_item_title.setText(title);

            tv_ffc_quan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 100;
                    list[position][1] = 100;
                    list[position][2] = 100;
                    list[position][3] = 100;
                    list[position][4] = 100;
                    list[position][5] = 100;
                    list[position][6] = 100;
                    list[position][7] = 100;
                    list[position][8] = 100;
                    list[position][9] = 100;
                    //list[position][10] = 100;
                    shishicai_item_zero.setSelected(true);
                    shishicai_item_first.setSelected(true);
                    shishicai_item_two.setSelected(true);
                    shishicai_item_third.setSelected(true);
                    shishicai_item_four.setSelected(true);
                    shishicai_item_five.setSelected(true);
                    shishicai_item_six.setSelected(true);
                    shishicai_item_seven.setSelected(true);
                    shishicai_item_eight.setSelected(true);
                    shishicai_item_nine.setSelected(true);
                    shishicai_item_ten.setSelected(true);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_da.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 100;
                    list[position][1] = 1;
                    list[position][2] = 2;
                    list[position][3] = 3;
                    list[position][4] = 4;
                    list[position][5] = 5;
                    list[position][6] = 100;
                    list[position][7] = 100;
                    list[position][8] = 100;
                    list[position][9] = 100;
                   // list[position][10] = 100;
                    shishicai_item_zero.setSelected(false);
                    shishicai_item_first.setSelected(false);
                    shishicai_item_two.setSelected(false);
                    shishicai_item_third.setSelected(false);
                    shishicai_item_four.setSelected(false);
                    shishicai_item_five.setSelected(false);
                    shishicai_item_six.setSelected(true);
                    shishicai_item_seven.setSelected(true);
                    shishicai_item_eight.setSelected(true);
                    shishicai_item_nine.setSelected(true);
                    shishicai_item_ten.setSelected(true);

                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_xiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 10;
                    list[position][1] = 100;
                    list[position][2] = 100;
                    list[position][3] = 100;
                    list[position][4] = 100;
                    list[position][5] = 100;
                    list[position][6] = 6;
                    list[position][7] = 7;
                    list[position][8] = 8;
                    list[position][9] = 9;
                   // list[position][10] = 10;
                    shishicai_item_zero.setSelected(true);
                    shishicai_item_first.setSelected(true);
                    shishicai_item_two.setSelected(true);
                    shishicai_item_third.setSelected(true);
                    shishicai_item_four.setSelected(true);
                    shishicai_item_five.setSelected(true);
                    shishicai_item_six.setSelected(false);
                    shishicai_item_seven.setSelected(false);
                    shishicai_item_eight.setSelected(false);
                    shishicai_item_nine.setSelected(false);
                    shishicai_item_ten.setSelected(false);

                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_dan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 10;
                    list[position][1] = 100;
                    list[position][2] = 2;
                    list[position][3] = 100;
                    list[position][4] = 4;
                    list[position][5] = 100;
                    list[position][6] = 6;
                    list[position][7] = 100;
                    list[position][8] = 8;
                    list[position][9] = 100;
                   // list[position][10] = 10;
                    shishicai_item_zero.setSelected(false);
                    shishicai_item_first.setSelected(true);
                    shishicai_item_two.setSelected(false);
                    shishicai_item_third.setSelected(true);
                    shishicai_item_four.setSelected(false);
                    shishicai_item_five.setSelected(true);
                    shishicai_item_six.setSelected(false);
                    shishicai_item_seven.setSelected(true);
                    shishicai_item_eight.setSelected(false);
                    shishicai_item_nine.setSelected(true);
                    shishicai_item_ten.setSelected(false);
                    shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_shuang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 100;
                    list[position][1] = 1;
                    list[position][2] = 100;
                    list[position][3] = 3;
                    list[position][4] = 100;
                    list[position][5] = 5;
                    list[position][6] = 100;
                    list[position][7] = 7;
                    list[position][8] = 100;
                    list[position][9] = 9;
                  //  list[position][10] = 100;
                    shishicai_item_zero.setSelected(true);
                    shishicai_item_first.setSelected(false);
                    shishicai_item_two.setSelected(true);
                    shishicai_item_third.setSelected(false);
                    shishicai_item_four.setSelected(true);
                    shishicai_item_five.setSelected(false);
                    shishicai_item_six.setSelected(true);
                    shishicai_item_seven.setSelected(false);
                    shishicai_item_eight.setSelected(true);
                    shishicai_item_nine.setSelected(false);
                    shishicai_item_ten.setSelected(true);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_qing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 10;
                    list[position][1] = 1;
                    list[position][2] = 2;
                    list[position][3] = 3;
                    list[position][4] = 4;
                    list[position][5] = 5;
                    list[position][6] = 6;
                    list[position][7] = 7;
                    list[position][8] = 8;
                    list[position][9] = 9;
                    //list[position][10] = 10;
                    shishicai_item_zero.setSelected(false);
                    shishicai_item_first.setSelected(false);
                    shishicai_item_two.setSelected(false);
                    shishicai_item_third.setSelected(false);
                    shishicai_item_four.setSelected(false);
                    shishicai_item_five.setSelected(false);
                    shishicai_item_six.setSelected(false);
                    shishicai_item_seven.setSelected(false);
                    shishicai_item_eight.setSelected(false);
                    shishicai_item_nine.setSelected(false);
                    shishicai_item_ten.setSelected(false);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });

           /* if (100 == (list[position][0])) {
                shishicai_item_zero.setSelected(true);
                shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_zero.setSelected(false);
                shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }*/
            if (100 == list[position][1]) {
                shishicai_item_first.setSelected(true);
                shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_first.setSelected(false);
                shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][2])) {
                shishicai_item_two.setSelected(true);
                shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_two.setSelected(false);
                shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][3])) {
                shishicai_item_third.setSelected(true);
                shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_third.setSelected(false);
                shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][4])) {
                shishicai_item_four.setSelected(true);
                shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_four.setSelected(false);
                shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][5])) {
                shishicai_item_five.setSelected(true);
                shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_five.setSelected(false);
                shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][6])) {
                shishicai_item_six.setSelected(true);
                shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_six.setSelected(false);
                shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][7])) {
                shishicai_item_seven.setSelected(true);
                shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_seven.setSelected(false);
                shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][8])) {
                shishicai_item_eight.setSelected(true);
                shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_eight.setSelected(false);
                shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][9])) {
                shishicai_item_nine.setSelected(true);
                shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_nine.setSelected(false);
                shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
           if (100 == (list[position][0])) {
                shishicai_item_ten.setSelected(true);
                shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_ten.setSelected(false);
                shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
           /* shishicai_item_zero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][0])) {
                        list[position][0] = 0;
                        shishicai_item_zero.setSelected(false);
                        shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][0] = 100;
                        shishicai_item_zero.setSelected(true);
                        shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });*/
            shishicai_item_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][1])) {
                        list[position][1] = 1;
                        shishicai_item_first.setSelected(false);
                        shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][1] = 100;
                        shishicai_item_first.setSelected(true);
                        shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][2])) {
                        list[position][2] = 2;
                        shishicai_item_two.setSelected(false);
                        shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][2] = 100;
                        shishicai_item_two.setSelected(true);
                        shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][3])) {
                        list[position][3] = 3;
                        shishicai_item_third.setSelected(false);
                        shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][3] = 100;
                        shishicai_item_third.setSelected(true);
                        shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });

            shishicai_item_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][4])) {
                        list[position][4] = 4;
                        shishicai_item_four.setSelected(false);
                        shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][4] = 100;
                        shishicai_item_four.setSelected(true);
                        shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][5])) {
                        list[position][5] = 5;
                        shishicai_item_five.setSelected(false);
                        shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][5] = 100;
                        shishicai_item_five.setSelected(true);
                        shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_six.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][6])) {
                        list[position][6] = 6;
                        shishicai_item_six.setSelected(false);
                        shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][6] = 100;
                        shishicai_item_six.setSelected(true);
                        shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_seven.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][7])) {
                        list[position][7] = 7;
                        shishicai_item_seven.setSelected(false);
                        shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][7] = 100;
                        shishicai_item_seven.setSelected(true);
                        shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_eight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][8])) {
                        list[position][8] = 8;
                        shishicai_item_eight.setSelected(false);
                        shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][8] = 100;
                        shishicai_item_eight.setSelected(true);
                        shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_nine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][9])) {
                        list[position][9] = 9;
                        shishicai_item_nine.setSelected(false);
                        shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][9] = 100;
                        shishicai_item_nine.setSelected(true);
                        shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_ten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][0])) {
                        list[position][0] = 10;
                        shishicai_item_ten.setSelected(false);
                        shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][0] = 100;
                        shishicai_item_ten.setSelected(true);
                        shishicai_item_ten.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            return view;
        } else {
            View view = View.inflate(context, R.layout.shishicai_listitem, null);
            final TextView shishicai_item_title = (TextView) view.findViewById(R.id.shishicai_item_title);

            final TextView shishicai_item_zero = (TextView) view.findViewById(R.id.shishicai_item_zero);
            final TextView shishicai_item_first = (TextView) view.findViewById(R.id.shishicai_item_first);
            final TextView shishicai_item_two = (TextView) view.findViewById(R.id.shishicai_item_two);
            final TextView shishicai_item_third = (TextView) view.findViewById(R.id.shishicai_item_third);
            final TextView shishicai_item_four = (TextView) view.findViewById(R.id.shishicai_item_four);
            final TextView shishicai_item_five = (TextView) view.findViewById(R.id.shishicai_item_five);
            final TextView shishicai_item_six = (TextView) view.findViewById(R.id.shishicai_item_six);
            final TextView shishicai_item_seven = (TextView) view.findViewById(R.id.shishicai_item_seven);
            final TextView shishicai_item_eight = (TextView) view.findViewById(R.id.shishicai_item_eight);
            final TextView shishicai_item_nine = (TextView) view.findViewById(R.id.shishicai_item_nine);

            TextView shishicai_item_zero1 = (TextView) view.findViewById(R.id.shishicai_item_zero1);
            TextView shishicai_item_first1 = (TextView) view.findViewById(R.id.shishicai_item_first1);
            TextView shishicai_item_two1 = (TextView) view.findViewById(R.id.shishicai_item_two1);
            TextView shishicai_item_third1 = (TextView) view.findViewById(R.id.shishicai_item_third1);
            TextView shishicai_item_four1 = (TextView) view.findViewById(R.id.shishicai_item_four1);
            TextView shishicai_item_five1 = (TextView) view.findViewById(R.id.shishicai_item_five1);
            TextView shishicai_item_six1 = (TextView) view.findViewById(R.id.shishicai_item_six1);
            TextView shishicai_item_seven1 = (TextView) view.findViewById(R.id.shishicai_item_seven1);
            TextView shishicai_item_eight1 = (TextView) view.findViewById(R.id.shishicai_item_eight1);
            TextView shishicai_item_nine1 = (TextView) view.findViewById(R.id.shishicai_item_nine1);

            TextView tv_ffc_quan = (TextView) view.findViewById(R.id.tv_ffc_quan);
            TextView tv_ffc_da = (TextView) view.findViewById(R.id.tv_ffc_da);
            TextView tv_ffc_xiao = (TextView) view.findViewById(R.id.tv_ffc_xiao);
            TextView tv_ffc_dan = (TextView) view.findViewById(R.id.tv_ffc_dan);
            TextView tv_ffc_shuang = (TextView) view.findViewById(R.id.tv_ffc_shuang);
            TextView tv_ffc_qing = (TextView) view.findViewById(R.id.tv_ffc_qing);

            String title = titleList.get(position);
            shishicai_item_title.setText(title);

            tv_ffc_quan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 100;
                    list[position][1] = 100;
                    list[position][2] = 100;
                    list[position][3] = 100;
                    list[position][4] = 100;
                    list[position][5] = 100;
                    list[position][6] = 100;
                    list[position][7] = 100;
                    list[position][8] = 100;
                    list[position][9] = 100;
                    shishicai_item_zero.setSelected(true);
                    shishicai_item_first.setSelected(true);
                    shishicai_item_two.setSelected(true);
                    shishicai_item_third.setSelected(true);
                    shishicai_item_four.setSelected(true);
                    shishicai_item_five.setSelected(true);
                    shishicai_item_six.setSelected(true);
                    shishicai_item_seven.setSelected(true);
                    shishicai_item_eight.setSelected(true);
                    shishicai_item_nine.setSelected(true);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_da.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 0;
                    list[position][1] = 1;
                    list[position][2] = 2;
                    list[position][3] = 3;
                    list[position][4] = 4;
                    list[position][5] = 100;
                    list[position][6] = 100;
                    list[position][7] = 100;
                    list[position][8] = 100;
                    list[position][9] = 100;
                    shishicai_item_zero.setSelected(false);
                    shishicai_item_first.setSelected(false);
                    shishicai_item_two.setSelected(false);
                    shishicai_item_third.setSelected(false);
                    shishicai_item_four.setSelected(false);
                    shishicai_item_five.setSelected(true);
                    shishicai_item_six.setSelected(true);
                    shishicai_item_seven.setSelected(true);
                    shishicai_item_eight.setSelected(true);
                    shishicai_item_nine.setSelected(true);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_xiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 100;
                    list[position][1] = 100;
                    list[position][2] = 100;
                    list[position][3] = 100;
                    list[position][4] = 100;
                    list[position][5] = 5;
                    list[position][6] = 6;
                    list[position][7] = 7;
                    list[position][8] = 8;
                    list[position][9] = 9;
                    shishicai_item_zero.setSelected(true);
                    shishicai_item_first.setSelected(true);
                    shishicai_item_two.setSelected(true);
                    shishicai_item_third.setSelected(true);
                    shishicai_item_four.setSelected(true);
                    shishicai_item_five.setSelected(false);
                    shishicai_item_six.setSelected(false);
                    shishicai_item_seven.setSelected(false);
                    shishicai_item_eight.setSelected(false);
                    shishicai_item_nine.setSelected(false);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_dan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 0;
                    list[position][1] = 100;
                    list[position][2] = 2;
                    list[position][3] = 100;
                    list[position][4] = 4;
                    list[position][5] = 100;
                    list[position][6] = 6;
                    list[position][7] = 100;
                    list[position][8] = 8;
                    list[position][9] = 100;
                    shishicai_item_zero.setSelected(false);
                    shishicai_item_first.setSelected(true);
                    shishicai_item_two.setSelected(false);
                    shishicai_item_third.setSelected(true);
                    shishicai_item_four.setSelected(false);
                    shishicai_item_five.setSelected(true);
                    shishicai_item_six.setSelected(false);
                    shishicai_item_seven.setSelected(true);
                    shishicai_item_eight.setSelected(false);
                    shishicai_item_nine.setSelected(true);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_shuang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 100;
                    list[position][1] = 1;
                    list[position][2] = 100;
                    list[position][3] = 3;
                    list[position][4] = 100;
                    list[position][5] = 5;
                    list[position][6] = 100;
                    list[position][7] = 7;
                    list[position][8] = 100;
                    list[position][9] = 9;
                    shishicai_item_zero.setSelected(true);
                    shishicai_item_first.setSelected(false);
                    shishicai_item_two.setSelected(true);
                    shishicai_item_third.setSelected(false);
                    shishicai_item_four.setSelected(true);
                    shishicai_item_five.setSelected(false);
                    shishicai_item_six.setSelected(true);
                    shishicai_item_seven.setSelected(false);
                    shishicai_item_eight.setSelected(true);
                    shishicai_item_nine.setSelected(false);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            tv_ffc_qing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list[position][0] = 0;
                    list[position][1] = 1;
                    list[position][2] = 2;
                    list[position][3] = 3;
                    list[position][4] = 4;
                    list[position][5] = 5;
                    list[position][6] = 6;
                    list[position][7] = 7;
                    list[position][8] = 8;
                    list[position][9] = 9;
                    shishicai_item_zero.setSelected(false);
                    shishicai_item_first.setSelected(false);
                    shishicai_item_two.setSelected(false);
                    shishicai_item_third.setSelected(false);
                    shishicai_item_four.setSelected(false);
                    shishicai_item_five.setSelected(false);
                    shishicai_item_six.setSelected(false);
                    shishicai_item_seven.setSelected(false);
                    shishicai_item_eight.setSelected(false);
                    shishicai_item_nine.setSelected(false);
                    shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));

                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });

            if (100 == (list[position][0])) {
                shishicai_item_zero.setSelected(true);
                shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_zero.setSelected(false);
                shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == list[position][1]) {
                shishicai_item_first.setSelected(true);
                shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_first.setSelected(false);
                shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][2])) {
                shishicai_item_two.setSelected(true);
                shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_two.setSelected(false);
                shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][3])) {
                shishicai_item_third.setSelected(true);
                shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_third.setSelected(false);
                shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][4])) {
                shishicai_item_four.setSelected(true);
                shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_four.setSelected(false);
                shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][5])) {
                shishicai_item_five.setSelected(true);
                shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_five.setSelected(false);
                shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][6])) {
                shishicai_item_six.setSelected(true);
                shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_six.setSelected(false);
                shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][7])) {
                shishicai_item_seven.setSelected(true);
                shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_seven.setSelected(false);
                shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][8])) {
                shishicai_item_eight.setSelected(true);
                shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_eight.setSelected(false);
                shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            if (100 == (list[position][9])) {
                shishicai_item_nine.setSelected(true);
                shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                shishicai_item_nine.setSelected(false);
                shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
            }
            shishicai_item_zero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][0])) {
                        list[position][0] = 0;
                        shishicai_item_zero.setSelected(false);
                        shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][0] = 100;
                        shishicai_item_zero.setSelected(true);
                        shishicai_item_zero.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][1])) {
                        list[position][1] = 1;
                        shishicai_item_first.setSelected(false);
                        shishicai_item_first.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][1] = 100;
                        shishicai_item_first.setSelected(true);
                        shishicai_item_first.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][2])) {
                        list[position][2] = 2;
                        shishicai_item_two.setSelected(false);
                        shishicai_item_two.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][2] = 100;
                        shishicai_item_two.setSelected(true);
                        shishicai_item_two.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][3])) {
                        list[position][3] = 3;
                        shishicai_item_third.setSelected(false);
                        shishicai_item_third.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][3] = 100;
                        shishicai_item_third.setSelected(true);
                        shishicai_item_third.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });

            shishicai_item_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][4])) {
                        list[position][4] = 4;
                        shishicai_item_four.setSelected(false);
                        shishicai_item_four.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][4] = 100;
                        shishicai_item_four.setSelected(true);
                        shishicai_item_four.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][5])) {
                        list[position][5] = 5;
                        shishicai_item_five.setSelected(false);
                        shishicai_item_five.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][5] = 100;
                        shishicai_item_five.setSelected(true);
                        shishicai_item_five.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_six.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][6])) {
                        list[position][6] = 6;
                        shishicai_item_six.setSelected(false);
                        shishicai_item_six.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][6] = 100;
                        shishicai_item_six.setSelected(true);
                        shishicai_item_six.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_seven.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][7])) {
                        list[position][7] = 7;
                        shishicai_item_seven.setSelected(false);
                        shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][7] = 100;
                        shishicai_item_seven.setSelected(true);
                        shishicai_item_seven.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_eight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][8])) {
                        list[position][8] = 8;
                        shishicai_item_eight.setSelected(false);
                        shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][8] = 100;
                        shishicai_item_eight.setSelected(true);
                        shishicai_item_eight.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            shishicai_item_nine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (100 == (list[position][9])) {
                        list[position][9] = 9;
                        shishicai_item_nine.setSelected(false);
                        shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.color_e8554e));
                    } else {
                        list[position][9] = 100;
                        shishicai_item_nine.setSelected(true);
                        shishicai_item_nine.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if (onClickNumberListener != null) {
                        onClickNumberListener.onClickNumber();
                    }
                }
            });
            return view;
        }

    }

    public void setData(int[][] list, List<String> titleList) {
        this.list = list;
        this.titleList = titleList;


        notifyDataSetInvalidated();
    }

    public void setData(int[][] list, List<String> titleList, String cpcode) {
        this.list = list;
        this.titleList = titleList;
        this.cpcode = cpcode;

        notifyDataSetInvalidated();
    }

}
