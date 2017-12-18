package com.developer.lecai.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.developer.lecai.R;


/**
 * Created by dell-3020 on 2017/7/10.
 */

public class MultiLinesCheckBox extends ViewGroup {
    private float line_height;
    private int columnNum = 6;

    public MultiLinesCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        line_height = (int)getResources().getDimension(R.dimen.h_203px);
    }

    public MultiLinesCheckBox(Context context) {
        super(context);
        line_height = (int)getResources().getDimension(R.dimen.h_203px);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取到孩子的个数
        int childCount = getChildCount();
        //得到ViewGroup的初始宽高
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        float height = MeasureSpec.getSize(heightMeasureSpec)
                + getPaddingBottom() + getPaddingTop();
        //获取第一个子View的起始点位置
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        //遍历孩子，并对它们进行测量
        for (int i = 0; i < childCount; i++) {
            //获取到孩子
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final LayoutParams lp = child.getLayoutParams();
                //算出子View宽的MeasureSpec值
                int wSpec = MeasureSpec.makeMeasureSpec(
                        lp.width, MeasureSpec.EXACTLY);
                //算出子View高的MeasureSpec值
                int hSpec = MeasureSpec.makeMeasureSpec(
                        lp.height, MeasureSpec.EXACTLY);
                child.measure(wSpec, hSpec);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight(); //计算出大小
                line_height = Math.max(line_height, childHeight);
                if (xpos + childWidth > width) {
                    //初始坐标的x偏移值+子View宽度>ViewGroup宽度 就换行
                    xpos = getPaddingLeft();//坐标x偏移值归零
                    ypos += line_height;//坐标y偏移值再加上本行的行高也就是换行
                }
                //算出下一个子View的起始点x偏移值
                xpos += childWidth;
            }
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            //对高度期望值没有限制
            height = ypos + line_height;

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (ypos + line_height < height) {
                height = ypos + line_height;
            }//达不到指定高度则缩小高度

        } else {
            height = ypos + line_height;
        }
        //设置ViewGroup宽高值
        setMeasuredDimension(width, (int)height);
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            //对高度期望值没有限制
            height = ypos + line_height;

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (ypos + line_height < height) {
                height = ypos + line_height;
            }//达不到指定高度则缩小高度

        } else {
            height = ypos + line_height;
        }
        //设置ViewGroup宽高值
        setMeasuredDimension(width, (int) height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取到孩子的个数
        int childCount = getChildCount();
        //计算最大宽度
        int maxWidth = r - l;
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int columnWidth = maxWidth / columnNum;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final int childw = child.getMeasuredWidth();
                final int childh = child.getMeasuredHeight();
                if (i>0 && i % columnNum == 0) {
                    xpos = getPaddingLeft();
                    ypos += line_height;
                }
                child.layout(xpos + (columnWidth - childw) / 2, ypos, xpos + childw + (columnWidth - childw) / 2, ypos + childh);
                xpos += columnWidth;
            }
        }

    }
}
