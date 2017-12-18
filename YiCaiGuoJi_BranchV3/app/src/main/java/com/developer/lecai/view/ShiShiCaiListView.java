package com.developer.lecai.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.developer.lecai.adapter.ShiShiCaiAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liuwei on 2017/7/31.
 */

public class ShiShiCaiListView extends ListView {

    private HashMap<String, Integer> hash1;
    private HashMap<String, Integer> hash2;
    private HashMap<String, Integer> hash3;
    private HashMap<String, Integer> hash4;
    private HashMap<String, Integer> hash5;
    private Context context;
    private int count;
    private List<String> list;
    private ShiShiCaiAdapter shiShiCaiAdapter;

    private OnClickNumberListener1 onClickNumberListener;

    public void setOnClickNumberListener1(OnClickNumberListener1 onClickNumberListener) {
        this.onClickNumberListener = onClickNumberListener;
    }

    public interface OnClickNumberListener1 {
        void onClickNumber(HashMap<String, Integer> hash1);

        void onClickNumber(HashMap<String, Integer> hash1, HashMap<String, Integer> hash2);

        void onClickNumber(HashMap<String, Integer> hash1, HashMap<String, Integer> hash2, HashMap<String, Integer> hash3);

        void onClickNumber(HashMap<String, Integer> hash1, HashMap<String, Integer> hash2, HashMap<String, Integer> hash3, HashMap<String, Integer> hash4);

        void onClickNumber(HashMap<String, Integer> hash1, HashMap<String, Integer> hash2, HashMap<String, Integer> hash3, HashMap<String, Integer> hash4, HashMap<String, Integer> hash5);
    }

    public ShiShiCaiListView(Context context) {
        this(context, null);
    }

    public ShiShiCaiListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShiShiCaiListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
    }

    public void setData(final int count, List<String> list) {
        this.count = count;
        this.list = list;
      //  shiShiCaiAdapter = new ShiShiCaiAdapter(context, count, list);
      //  ShiShiCaiListView.this.setAdapter(shiShiCaiAdapter);

    }

}
