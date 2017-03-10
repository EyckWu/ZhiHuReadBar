package com.eyckwu.readbar.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.eyckwu.readbar.utils.MyLog;

/**
 * Created by Eyck on 2017/2/27.
 */

public class InterceptViewPager extends ViewPager{
    public InterceptViewPager(Context context) {
        super(context);
    }

    public InterceptViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float startX;
    private float startY;

    /**
     * 如果水平滑动大于垂直滑动，请求反拦截
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case  MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                MyLog.d("ACTION_DOWN");
                break;
            case  MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float dX = endX - startX;
                float dY = endY - startY;
                MyLog.d("ACTION_MOVE");
                
                if(Math.abs(dX) > Math.abs(dY) && Math.abs(dX) > 8) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case  MotionEvent.ACTION_UP:
                MyLog.d("ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


}
