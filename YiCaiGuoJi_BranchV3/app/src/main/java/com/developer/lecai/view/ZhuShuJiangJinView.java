package com.developer.lecai.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;


/**
 * Created by liuwei on 2017/8/13.
 */

public class ZhuShuJiangJinView extends LinearLayout {

    private TextView tv_zsjj_zs;
    private TextView tv_zsjj_jj;
    private View view;

    public ZhuShuJiangJinView(Context context) {
        this(context, null);
    }

    public ZhuShuJiangJinView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public ZhuShuJiangJinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        view = View.inflate(context, R.layout.zsjj_item, null);
        tv_zsjj_zs = (TextView) view.findViewById(R.id.tv_zsjj_zs);
        tv_zsjj_jj = (TextView) view.findViewById(R.id.tv_zsjj_jj);
        removeAllViews();
        addView(view);
    }

    public void setZSJJ(String zs, String jj) {
        if (tv_zsjj_zs != null) {
            tv_zsjj_zs.setText(zs);
        }
        if (tv_zsjj_jj != null) {
            tv_zsjj_jj.setText(jj);
        }
    }
}
