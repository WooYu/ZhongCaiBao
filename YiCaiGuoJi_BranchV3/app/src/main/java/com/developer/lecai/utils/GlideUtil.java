package com.developer.lecai.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.developer.lecai.R;
import com.developer.lecai.utils.glide.GlideCircleTransform;
import com.developer.lecai.utils.glide.GlideRoundTransform;

import java.util.concurrent.ExecutionException;


/**
 * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
 * load assets资源：load("file:///android_asset/f003.gif")
 * load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
 * load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
 * load ContentProvider资源：load("content://media/external/images/media/139469")
 * load http资源：load("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg")
 * load https资源：load(" http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg ")
 *
 * Created by raotf on 2017/6/7.
 */
public class GlideUtil {

    private static final int PLACE_IMG = R.color.white;
    private static final int ERROR_IMG = R.color.white;

    /**
     * 设置图片，可设置默认图
     * @param context
     * @param view 显示组件
     * @param url 图片路径
     * @param defaultImg 默认图片
     * @param errorImg 加载错误图片
     */
    public static void setImage(Context context, ImageView view, String url, int defaultImg, int errorImg) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImg)
                .error(errorImg)
                .crossFade()
                .into(view);
    }

    /**
     * 设置图片,显示默认图
     * @param context
     * @param view 显示组件
     * @param url 图片路径
     */
    public static void setImage(Context context, ImageView view, String url) {
        setImage(context, view, url, PLACE_IMG, ERROR_IMG);
    }

    /**
     * 设置圆形图片
     * @param context
     * @param view
     * @param url
     * @param defaultImg
     * @param errorImg
     */
    public static void setCircleImage(Context context, ImageView view, String url, int defaultImg, int errorImg) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImg)
                .error(errorImg)
                .bitmapTransform(new GlideCircleTransform(context))
                .crossFade()
                .into(view);
    }

    public static void setCircleImage(Context context, ImageView view, String url) {
        setCircleImage(context, view, url, PLACE_IMG, ERROR_IMG);
    }

    /**
     * 设置圆形图片
     * @param context
     * @param view
     * @param url
     * @param defaultImg
     * @param errorImg
     */
    public static void setCircleImage2(final Context context, ImageView view, String url, int defaultImg, int errorImg) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(defaultImg)
                .error(errorImg)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setCircleImage2(final Context context, ImageView view, String url) {
        setCircleImage2(context, view, url, PLACE_IMG, ERROR_IMG);
    }

    /**
     * 设置圆角图片
     * @param context
     * @param view
     * @param url
     * @param defaultImg
     * @param errorImg
     */
    public static void setRoundImage(Context context, ImageView view, String url, int defaultImg, int errorImg) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImg)
                .error(errorImg)
                .bitmapTransform(new GlideRoundTransform(context))
                .crossFade()
                .into(view);
    }

    public static void setRoundImage(Context context, ImageView view, String url) {
        setRoundImage(context, view, url, PLACE_IMG, ERROR_IMG);
    }

    /**
     * 获取Bitmap
     * @param context
     * @param url
     * @return
     */
    public static Bitmap getBitmapByUrl(Context context, String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


}
