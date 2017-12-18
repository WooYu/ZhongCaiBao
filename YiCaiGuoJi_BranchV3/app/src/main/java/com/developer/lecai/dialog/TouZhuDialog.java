package com.developer.lecai.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.utils.LogUtils;

import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by lw on 16/5/16.
 */
public class TouZhuDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private ImageView ivCarAnim;
    private ImageView vBg;
    private final TextView tv_hmjl_kaishi;
    private final TextView tv_hmjl_jieshu;
    private final TextView tv_hmjl_cz;
    private final TextView tv_hmjl_qr;
    private HashMap<String, String> hashMap;

    private OnClickHMJLQueRenListener onClickQueRenListener;
    private final TextView tv_hmjl_dlt;
    private final TextView tv_hmjl_ssq;
    private final TextView tv_hmjl_bjxy;
    private final TextView tv_hmjl_jndxy;
    private final TextView tv_hmjl_ffc;
    private final TextView tv_hmjl_sfc;
    private final TextView tv_hmjl_wfc;
    private final TextView tv_hmjl_bjsc;
    private final TextView tv_hmjl_cqssc;
    private final TextView tv_hmjl_lhc;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Calendar ca = Calendar.getInstance();
    private int selecteTime;
    private final TextView tv_hmjl_yizj;
    private final TextView tv_hmjl_weizj;

    public void setOnClickHMJLQueRenListener(OnClickHMJLQueRenListener onClickQueRenListener) {
        this.onClickQueRenListener = onClickQueRenListener;
    }

    public interface OnClickHMJLQueRenListener {

        void onClickQueRen(HashMap<String, String> hashMap);

    }

    public TouZhuDialog(Context context, int statusHeight) {
        super(context, R.style.dlgTheme);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_hemaijilu, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        // 设置窗口属性
        hashMap = new HashMap<>();

        hashMap.put("KSSJ", "0");
        hashMap.put("JSSJ", "0");
        hashMap.put("ZT", "0");
        hashMap.put("FL", "0");

        tv_hmjl_kaishi = (TextView) view.findViewById(R.id.tv_hmjl_kaishi);
        tv_hmjl_jieshu = (TextView) view.findViewById(R.id.tv_hmjl_jieshu);

        tv_hmjl_yizj = (TextView) view.findViewById(R.id.tv_hmjl_yizj);
        tv_hmjl_weizj = (TextView) view.findViewById(R.id.tv_hmjl_weizj);

        tv_hmjl_dlt = (TextView) view.findViewById(R.id.tv_hmjl_dlt);
        tv_hmjl_ssq = (TextView) view.findViewById(R.id.tv_hmjl_ssq);
        tv_hmjl_bjxy = (TextView) view.findViewById(R.id.tv_hmjl_bjxy);
        tv_hmjl_jndxy = (TextView) view.findViewById(R.id.tv_hmjl_jndxy);
        tv_hmjl_ffc = (TextView) view.findViewById(R.id.tv_hmjl_ffc);
        tv_hmjl_sfc = (TextView) view.findViewById(R.id.tv_hmjl_sfc);
        tv_hmjl_wfc = (TextView) view.findViewById(R.id.tv_hmjl_wfc);
        tv_hmjl_bjsc = (TextView) view.findViewById(R.id.tv_hmjl_bjsc);
        tv_hmjl_cqssc = (TextView) view.findViewById(R.id.tv_hmjl_cqssc);
        tv_hmjl_lhc = (TextView) view.findViewById(R.id.tv_hmjl_lhc);

        tv_hmjl_cz = (TextView) view.findViewById(R.id.tv_hmjl_cz);
        tv_hmjl_qr = (TextView) view.findViewById(R.id.tv_hmjl_qr);

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

        //ll_hmjl_total.set
        tv_hmjl_cz.setOnClickListener(this);
        tv_hmjl_qr.setOnClickListener(this);

        tv_hmjl_kaishi.setOnClickListener(this);
        tv_hmjl_jieshu.setOnClickListener(this);

        tv_hmjl_yizj.setOnClickListener(this);
        tv_hmjl_weizj.setOnClickListener(this);

        tv_hmjl_dlt.setOnClickListener(this);
        tv_hmjl_ssq.setOnClickListener(this);
        tv_hmjl_bjxy.setOnClickListener(this);
        tv_hmjl_jndxy.setOnClickListener(this);
        tv_hmjl_ffc.setOnClickListener(this);
        tv_hmjl_sfc.setOnClickListener(this);
        tv_hmjl_wfc.setOnClickListener(this);
        tv_hmjl_bjsc.setOnClickListener(this);
        tv_hmjl_cqssc.setOnClickListener(this);
        tv_hmjl_lhc.setOnClickListener(this);
        setData();
    }

    private void setData() {


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_hmjl_cz:
                hashMap.put("KSSJ", "0");
                hashMap.put("JSSJ", "0");
                hashMap.put("FL", "0");
                hashMap.put("ZT", "0");

                tv_hmjl_yizj.setSelected(false);
                tv_hmjl_weizj.setSelected(false);

                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;

            case R.id.tv_hmjl_qr:
                if (onClickQueRenListener!=null){
                    onClickQueRenListener.onClickQueRen(hashMap);
                }
                break;

            case R.id.tv_hmjl_yizj:
                tv_hmjl_yizj.setSelected(true);
                tv_hmjl_weizj.setSelected(false);
                hashMap.put("ZT", "1");
                break;

            case R.id.tv_hmjl_weizj:
                tv_hmjl_yizj.setSelected(false);
                tv_hmjl_weizj.setSelected(true);
                hashMap.put("ZT", "2");
                break;

            case R.id.tv_hmjl_kaishi:
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
                selecteTime=1;
                break;
            case R.id.tv_hmjl_jieshu:
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
                selecteTime=2;
                break;

            case R.id.tv_hmjl_dlt:
                hashMap.put("FL", "1");
                tv_hmjl_dlt.setSelected(true);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);

                break;
            case R.id.tv_hmjl_ssq:
                hashMap.put("FL", "2");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(true);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_bjxy:
                hashMap.put("FL", "3");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(true);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_jndxy:
                hashMap.put("FL", "4");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(true);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_ffc:
                hashMap.put("FL", "5");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(true);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_sfc:
                hashMap.put("FL", "6");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(true);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_wfc:
                hashMap.put("FL", "7");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(true);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_bjsc:
                hashMap.put("FL", "8");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(true);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_cqssc:
                hashMap.put("FL", "9");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(true);
                tv_hmjl_lhc.setSelected(false);
                break;
            case R.id.tv_hmjl_lhc:
                hashMap.put("FL", "10");
                tv_hmjl_dlt.setSelected(false);
                tv_hmjl_ssq.setSelected(false);
                tv_hmjl_bjxy.setSelected(false);
                tv_hmjl_jndxy.setSelected(false);
                tv_hmjl_ffc.setSelected(false);
                tv_hmjl_sfc.setSelected(false);
                tv_hmjl_wfc.setSelected(false);
                tv_hmjl_bjsc.setSelected(false);
                tv_hmjl_cqssc.setSelected(false);
                tv_hmjl_lhc.setSelected(true);
                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            LogUtils.e("日期对话框",year+"-------"+monthOfYear+"----------"+dayOfMonth);
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }
            LogUtils.e("日期对话框",days);
            if (selecteTime==1){
                tv_hmjl_kaishi.setText(days);
                hashMap.put("KSSJ", days);
            }else if(selecteTime==2){
                tv_hmjl_jieshu.setText(days);
                hashMap.put("JSSJ", days);
            }
        }
    };


}
