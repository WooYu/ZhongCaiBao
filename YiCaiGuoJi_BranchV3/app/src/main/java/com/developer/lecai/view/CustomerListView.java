package com.developer.lecai.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by liuwei on 2017/8/28.
 */

public class CustomerListView extends ListView {

    private float downY;
    private float downX;

    public CustomerListView(Context context) {
        this(context, null);
    }

    public CustomerListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                downX = ev.getX();
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                float moveX = ev.getX();
                float distanceX = moveX - downX;
                float distanceY = moveY - downY;
                if (Math.abs(distanceY) > Math.abs(distanceX)) {
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
    }
}
