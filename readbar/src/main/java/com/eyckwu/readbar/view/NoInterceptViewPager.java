package com.eyckwu.readbar.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Eyck on 2017/2/26.
 */

public class NoInterceptViewPager extends ViewPager {
    public NoInterceptViewPager(Context context) {
        this(context,null);
    }

    public NoInterceptViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 不拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 不处理事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
