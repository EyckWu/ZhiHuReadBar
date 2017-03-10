package com.eyckwu.readbar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Eyck on 2017/3/2.
 */

public class DragWebView extends WebView {
    public DragWebView(Context context) {
        super(context);
    }

    public DragWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(completeListener != null && t==0) {
            completeListener.onTop();
        }else if(completeListener != null && t!= 0) {
            completeListener.notOnTop();
        }
    }
    private OnDragScrollListener completeListener;
    public void setOnCompleteListener(@Nullable OnDragScrollListener l) {
        completeListener = l;
    }
    public interface OnDragScrollListener {
        void onTop();
        void notOnTop();
    }
}
