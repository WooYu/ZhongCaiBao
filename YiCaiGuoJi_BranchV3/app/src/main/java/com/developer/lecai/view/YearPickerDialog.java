package com.developer.lecai.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.lang.reflect.Field;

/**
 * Created by liuwei on 2017/8/29.
 */

public class YearPickerDialog extends DatePickerDialog {

    public YearPickerDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme, listener, year, monthOfYear, dayOfMonth);
        hidePicker();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        this.setTitle(year + "年");
    }

    /**
     * 传入一个DatePicker对象,隐藏或者显示相应的时间项
     * 1表示只显示年； 2表示显示年月 ;3表示显示年月日
     */
    private void hidePicker() {
        // 利用java反射技术得到picker内部的属性，并对其进行操作
        Class<? extends DatePicker> c = getDatePicker().getClass();
        try {
            Field fd = null, fm = null, fy = null;
            // 系统版本大于5.0
            if (Build.VERSION.SDK_INT >= 5) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
                //      int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
                //隐藏日
                if (daySpinnerId != 0) {
                    View daySpinner = getDatePicker().findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
                //隐藏月
                if (monthSpinnerId != 0) {
                    View monthSpinner = getDatePicker().findViewById(monthSpinnerId);
                    if (monthSpinner != null) {
                        monthSpinner.setVisibility(View.GONE);
                    }
                }
                return;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                // 系统版本大于4.0
                fd = c.getDeclaredField("mDaySpinner");
                fm = c.getDeclaredField("mMonthSpinner");
                fy = c.getDeclaredField("mYearSpinner");
            } else {
                fd = c.getDeclaredField("mDayPicker");
                fm = c.getDeclaredField("mMonthPicker");
                fy = c.getDeclaredField("mYearPicker");
            }
            // 对字段获取设置权限
            fd.setAccessible(true);
            fm.setAccessible(true);
            fy.setAccessible(true);
            // 得到对应的控件
            View vd = (View) fd.get(getDatePicker());
            View vm = (View) fm.get(getDatePicker());
            View vy = (View) fy.get(getDatePicker());

            vd.setVisibility(View.GONE);
            vm.setVisibility(View.GONE);
            vy.setVisibility(View.VISIBLE);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
