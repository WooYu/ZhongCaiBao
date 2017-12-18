package com.developer.lecai.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;

import java.util.HashMap;


/**
 * Created by lw on 16/5/16.
 */
public class JiFenMingXiDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private final TextView tv_jfmx_kaishi;
    private final TextView tv_jfmx_jieshu;
    private final TextView tv_jfmx_dlt;
    private final TextView tv_jfmx_ssq;
    private final TextView tv_jfmx_bjxy;
    private final TextView tv_jfmx_jndxy;
    private final TextView tv_jfmx_ffc;
    private final TextView tv_jfmx_sfc;
    private final TextView tv_jfmx_wfc;
    private final TextView tv_jfmx_bjsc;
    private final TextView tv_jfmx_cqssc;
    private final TextView tv_jfmx_lhc;
    private final LinearLayout ll_jfmx_total;
    private final TextView tv_jfmx_cz;
    private final TextView tv_jfmx_qr;
    private HashMap<String, Integer> hashMap;

    private OnClickJFMXQueRenListener onClickQueRenListener;

    public void setOnClickJFQueRenListener(OnClickJFMXQueRenListener onClickQueRenListener) {
        this.onClickQueRenListener = onClickQueRenListener;
    }

    public interface OnClickJFMXQueRenListener {

        void onClickQueRen(HashMap<String, Integer> hashMap);

    }

    public JiFenMingXiDialog(Context context, int statusHeight) {
        super(context, R.style.dlgTheme);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_jifenmingxi, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        // 设置窗口属性
        hashMap = new HashMap<>();

        hashMap.put("SJ", 0);
        hashMap.put("FL", 0);

        tv_jfmx_kaishi = (TextView) view.findViewById(R.id.tv_jfmx_kaishi);
        tv_jfmx_jieshu = (TextView) view.findViewById(R.id.tv_jfmx_jieshu);
        tv_jfmx_dlt = (TextView) view.findViewById(R.id.tv_jfmx_dlt);
        tv_jfmx_ssq = (TextView) view.findViewById(R.id.tv_jfmx_ssq);
        tv_jfmx_bjxy = (TextView) view.findViewById(R.id.tv_jfmx_bjxy);
        tv_jfmx_jndxy = (TextView) view.findViewById(R.id.tv_jfmx_jndxy);
        tv_jfmx_ffc = (TextView) view.findViewById(R.id.tv_jfmx_ffc);
        tv_jfmx_sfc = (TextView) view.findViewById(R.id.tv_jfmx_sfc);
        tv_jfmx_wfc = (TextView) view.findViewById(R.id.tv_jfmx_wfc);
        tv_jfmx_bjsc = (TextView) view.findViewById(R.id.tv_jfmx_bjsc);
        tv_jfmx_cqssc = (TextView) view.findViewById(R.id.tv_jfmx_cqssc);
        tv_jfmx_lhc = (TextView) view.findViewById(R.id.tv_jfmx_lhc);
        ll_jfmx_total = (LinearLayout) view.findViewById(R.id.ll_jfmx_total);

        tv_jfmx_cz = (TextView) view.findViewById(R.id.tv_jfmx_cz);
        tv_jfmx_qr = (TextView) view.findViewById(R.id.tv_jfmx_qr);

        Window window = this.getWindow();
//		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
        window.setWindowAnimations(R.style.Animation);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

       /* Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;*/

        WindowManager.LayoutParams params = window.getAttributes();
        params.y = statusHeight + getContext().getResources().getDimensionPixelSize(R.dimen.h_144px);
        params.width = width;
        params.height = height - params.y;
        params.gravity = Gravity.RIGHT|Gravity.TOP;
        window.setAttributes(params);
        setCanceledOnTouchOutside(true);

        //ll_sx_total.set
        tv_jfmx_cz.setOnClickListener(this);
        tv_jfmx_qr.setOnClickListener(this);

        tv_jfmx_kaishi.setOnClickListener(this);
        tv_jfmx_jieshu.setOnClickListener(this);
        tv_jfmx_dlt.setOnClickListener(this);
        tv_jfmx_ssq.setOnClickListener(this);
        tv_jfmx_bjxy.setOnClickListener(this);
        tv_jfmx_jndxy.setOnClickListener(this);
        tv_jfmx_ffc.setOnClickListener(this);
        tv_jfmx_sfc.setOnClickListener(this);
        tv_jfmx_wfc.setOnClickListener(this);
        tv_jfmx_bjsc.setOnClickListener(this);
        tv_jfmx_cqssc.setOnClickListener(this);
        tv_jfmx_lhc.setOnClickListener(this);
        setData();
    }

    private void setData() {


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_jfmx_cz:
                hashMap.put("SJ", 0);
                hashMap.put("FL", 0);

                tv_jfmx_kaishi.setSelected(false);
                tv_jfmx_jieshu.setSelected(false);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;

            case R.id.tv_jfmx_qr:

                if (onClickQueRenListener!=null){
                    onClickQueRenListener.onClickQueRen(hashMap);
                }
                break;
            case R.id.tv_jfmx_kaishi:
                hashMap.put("SJ", 1);
                tv_jfmx_kaishi.setSelected(true);
                tv_jfmx_jieshu.setSelected(false);
                break;
            case R.id.tv_jfmx_jieshu:
                hashMap.put("SJ", 2);
                tv_jfmx_kaishi.setSelected(false);
                tv_jfmx_jieshu.setSelected(true);
                break;

            case R.id.tv_jfmx_dlt:
                hashMap.put("FL", 1);
                tv_jfmx_dlt.setSelected(true);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);

                break;
            case R.id.tv_jfmx_ssq:
                hashMap.put("FL", 2);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(true);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_bjxy:
                hashMap.put("FL", 3);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(true);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_jndxy:
                hashMap.put("FL", 4);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(true);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_ffc:
                hashMap.put("FL", 5);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(true);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_sfc:
                hashMap.put("FL", 6);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(true);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_wfc:
                hashMap.put("FL", 7);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(true);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_bjsc:
                hashMap.put("FL", 8);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(true);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_cqssc:
                hashMap.put("FL", 9);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(true);
                tv_jfmx_lhc.setSelected(false);
                break;
            case R.id.tv_jfmx_lhc:
                hashMap.put("FL", 10);
                tv_jfmx_dlt.setSelected(false);
                tv_jfmx_ssq.setSelected(false);
                tv_jfmx_bjxy.setSelected(false);
                tv_jfmx_jndxy.setSelected(false);
                tv_jfmx_ffc.setSelected(false);
                tv_jfmx_sfc.setSelected(false);
                tv_jfmx_wfc.setSelected(false);
                tv_jfmx_bjsc.setSelected(false);
                tv_jfmx_cqssc.setSelected(false);
                tv_jfmx_lhc.setSelected(true);
                break;
        }
    }
}
