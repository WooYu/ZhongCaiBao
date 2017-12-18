package com.developer.lecai.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class MyUtil {

    /**
     * 获取手机操作系统版本
     *
     * @return
     * @author SHANHY
     * @date 2015年12月4日
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        if (null == imei || "".equals(imei)) {
            imei = String.valueOf(123456789);
        }
        return imei;
    }

    public static String getAand() {

        String annd = "http://";

        return annd;

    }

    public static String getSignature(String phone) {

        String appkey = "LinkQQ79365450".toLowerCase();
        String appSecret = "Mobile18217624362".toLowerCase();
        Log.e("排序", appkey + "--------" + appSecret);

        String[] strings1 = new String[]{appkey, appSecret};

        Arrays.sort(strings1);

        Log.e("排序", strings1.toString());
        String heBing = charToString(strings1);

        Log.e("排序", heBing);
        String heBing1 = MD5Util.getMD5String(heBing).toLowerCase();

        Log.e("排序", heBing1);

        String[] strings2 = new String[]{heBing1, phone};

        Arrays.sort(strings2);

        String heBing1Char1 = charToString(strings2);

        Log.e("排序", heBing1Char1);

        Log.e("排序", getSha1(heBing1Char1));

        return getSha1(heBing1Char1);
    }

    //MD5加密
    public static String EncoderByMd5(String str) {
        //确定计算方法
        MessageDigest md5 = null;
        String newstr = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
          /*  BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            try {
                 newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            try {
                Base64.encodeToString(md5.digest(str.getBytes("utf-8")), 0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return newstr;
    }

    //SHA1加密
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public static String charToString(String[] chars) {

        StringBuffer stringBuffer = new StringBuffer();

        for (String c : chars) {
            stringBuffer.append(c);
        }

        return stringBuffer.toString();
    }

    /*这个方法是inittitleView（）方法调用的方法，作用是计算状态栏的高度*/
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    public static void getDisplayInfo(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
//        screenWidth  = dm.widthPixels;
//        screenHeight = dm.heightPixels;
//
//        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(
//                new int[] { android.R.attr.actionBarSize });
//        actionBarHeight = (int) actionbarSizeTypedArray.getDimension(0, 0);
        int navigationBarHeight = 0;
        int statusBarHeight = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }

        resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        Log.e("导航栏", "状态栏高度" + statusBarHeight + "导航栏高度" + navigationBarHeight);
    }


    public static String spiltAmt(String s, int len) {
        if (s == null || s.equals("") || s.equals("null")) {
            s = "0";
        }
        NumberFormat formater = null;
        double num = parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,##0.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            formater = new DecimalFormat(buff.toString());
        }
        formater.setRoundingMode(RoundingMode.HALF_UP);

        return formater.format(num);

    }

    public static double parseDouble(String s) {
        double ans = 0.00;
        if (TextUtils.isEmpty(s)) {
            return ans;
        }
        try {
            ans = Double.parseDouble(s);
        } catch (Exception e) {
            ans = 0.00;
        }
        return ans;
    }

    //格式化金额，避免出现科学计算方
    public static String formatAmount(String amount) {
        if(null == amount || "".equals(amount))
            return "0";

        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(Double.parseDouble(amount));
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
