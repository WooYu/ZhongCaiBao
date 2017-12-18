package com.developer.lecai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理类
 * Created by airtf on 16/6/12.
 */
public class DateUtil {
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddhhmmss";

    /**
     * 返回date字符串
     *
     * @param date 日期
     * @param pattern 格式
     * @return
     */
    public static String getFormattedDateString(Date date, String pattern) {
        if (null == date)
            return "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过字符串和格式得到相应date
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseToDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取格式为yyyy-MM-dd HH:mm:ss的日期
     * @param date
     * @return
     */
    public static String getDateByformat(String date, String formatType) {
        return getFormattedDateString(parseToDate(date, formatType),
                formatType);
    }

    /**
     * 根据时间格式，得到当前时间
     * @param formatType 想要获得的时间格式
     * @return
     */
    public static String getDateByFormat(int formatType) {
        String pattern = null;
        if (formatType == 1) {
            pattern = "yyyyMMddHHmmss";
        } else if (formatType == 2) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        } else if (formatType == 3) {
            pattern = "yyyy年MM月dd日 HH时mm分ss秒";
        } else if (formatType == 4) {
            pattern = "HH:mm:ss";
        } else if (formatType == 5) {
            pattern = "HHmmss";
        } else {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        String strDate = format.format(date);
        return strDate;
    }

    public static String getDateByFormat(String pattern) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        String strDate = format.format(date);
        return strDate;
    }

    /**
     * 计算两日期相差天数
     */
    public static int daysBetween(String smdate, String bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两日期相差天数
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 计算两日期相差秒数
     */
    public static long minutesBetween(String bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Date curDate =  new Date(System.currentTimeMillis());
        curDate = sdf.parse(sdf.format(curDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000);

        return between_days;
    }
    /**
     * 格式化时间，得到hh:mm:ss
     * @param time
     * @return
     */
    public static String formatTime(int time){
        String hh=time/3600>9?time/3600+"":"0"+time/3600;
        String  mm=(time % 3600)/60>9?(time % 3600)/60+"":"0"+(time % 3600)/60;
        String ss=(time % 3600) % 60>9?(time % 3600) % 60+"":"0"+(time % 3600) % 60;
        return hh+":"+mm+":"+ss;
    }

    public static Date strToDate(String style, String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String dateToStr(String style, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }

    public static String clanderTodatetime(Calendar calendar, String style) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(calendar.getTime());
    }

    public static String DateTotime(long date, String style) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }

}
