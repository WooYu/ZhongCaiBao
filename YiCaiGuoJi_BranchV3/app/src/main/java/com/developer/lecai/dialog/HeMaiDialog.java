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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.lecai.R;
import com.developer.lecai.utils.LogUtils;

import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by lw on 16/5/16.
 */
public class HeMaiDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private ImageView ivCarAnim;
    private ImageView vBg;
    private final TextView tv_sx_kaishi;
    private final TextView tv_sx_jieshu;
    private final TextView tv_sx_yizj;
    private final TextView tv_sx_weizj;
    private final TextView tv_sx_cz;
    private final TextView tv_sx_qr;
    private HashMap<String, String> hashMap;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Calendar ca = Calendar.getInstance();
    private int selecteTime;

    private OnClickQueRenListener onClickQueRenListener;

    public void setOnClickQueRenListener(OnClickQueRenListener onClickQueRenListener) {
        this.onClickQueRenListener = onClickQueRenListener;
    }

    public interface OnClickQueRenListener {

        void onClickQueRen(HashMap<String, String> hashMap);

    }

    public HeMaiDialog(Context context, int statusHeight) {
        super(context, R.style.dlgTheme);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_touzhujilu, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        // 设置窗口属性
        hashMap = new HashMap<>();

        hashMap.put("KSSJ", "0");
        hashMap.put("JSSJ", "0");
        hashMap.put("ZT", "0");

        tv_sx_kaishi = (TextView) view.findViewById(R.id.tv_sx_kaishi);
        tv_sx_jieshu = (TextView) view.findViewById(R.id.tv_sx_jieshu);
        tv_sx_yizj = (TextView) view.findViewById(R.id.tv_sx_yizj);
        tv_sx_weizj = (TextView) view.findViewById(R.id.tv_sx_weizj);

        tv_sx_cz = (TextView) view.findViewById(R.id.tv_sx_cz);
        tv_sx_qr = (TextView) view.findViewById(R.id.tv_sx_qr);

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
        tv_sx_cz.setOnClickListener(this);
        tv_sx_qr.setOnClickListener(this);

        tv_sx_kaishi.setOnClickListener(this);
        tv_sx_jieshu.setOnClickListener(this);
        tv_sx_yizj.setOnClickListener(this);
        tv_sx_weizj.setOnClickListener(this);
        setData();
    }

    private void setData() {


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_sx_cz:
                hashMap.put("KSSJ", "0");
                hashMap.put("JSSJ", "0");
                hashMap.put("ZT", "0");

                tv_sx_yizj.setSelected(false);
                tv_sx_weizj.setSelected(false);
                break;

            case R.id.tv_sx_qr:

                if (onClickQueRenListener!=null){
                    onClickQueRenListener.onClickQueRen(hashMap);
                }
                break;
            case R.id.tv_sx_kaishi:
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
                selecteTime=1;
                break;
            case R.id.tv_sx_jieshu:
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
                selecteTime=2;
                break;
            case R.id.tv_sx_yizj:
                hashMap.put("ZT", "1");
                tv_sx_yizj.setSelected(true);
                tv_sx_weizj.setSelected(false);
                break;
            case R.id.tv_sx_weizj:
                hashMap.put("ZT", "2");
                tv_sx_yizj.setSelected(false);
                tv_sx_weizj.setSelected(true);
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
                tv_sx_kaishi.setText(days);
                hashMap.put("KSSJ", days);
            }else if(selecteTime==2){
                tv_sx_jieshu.setText(days);
                hashMap.put("JSSJ", days);
            }
        }
    };

}
