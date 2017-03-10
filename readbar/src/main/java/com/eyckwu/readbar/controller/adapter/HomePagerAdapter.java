package com.eyckwu.readbar.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eyckwu.readbar.controller.activity.ArticleActivity;
import com.eyckwu.readbar.model.bean.HomeDataBean;
import com.eyckwu.readbar.utils.MyLog;
import com.eyckwu.readbar.utils.MyUrl;
import com.eyckwu.readbar.utils.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Eyck on 2017/2/27.
 */

public class HomePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<HomeDataBean.TopStoriesBean> topStoriesBean;
    private NetUtils homeNetUtils;
    private boolean isFinish = false;
    private Handler handler;
//    private Bitmap firstBitmap;
    private Map<String ,Bitmap> bitmapMap;
    private static final int CHANGEHEADVIEW = 0;

    public HomePagerAdapter(Context mContext, final List<HomeDataBean.TopStoriesBean> topStoriesBean, Handler handler){
        this.mContext = mContext;
        this.topStoriesBean = topStoriesBean;
        this.handler = handler;
        bitmapMap = new HashMap<>();
        homeNetUtils = new NetUtils();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(mContext);
        final int realPosition = position%topStoriesBean.size();
        String imageurl = topStoriesBean.get(realPosition).getImage();
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Bitmap bitmap = bitmapMap.get(imageurl);
        imageView.setTag(imageurl);
        if(bitmap != null) {
            if(imageView.getTag() == imageurl) {
                imageView.setImageBitmap(bitmap);
            }
        }else {
            setImage(imageurl,imageView);
        }

        container.addView(imageView);
        imageView.setOnTouchListener(new MyOnTouchListener());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = topStoriesBean.get(realPosition).getId();
                String contentUrl = MyUrl.NEWSCONTENT + id;
                turnToActivity(contentUrl);
            }
        });
        return imageView;
    }

    private void turnToActivity(String contentUrl) {
        Intent intent = new Intent(mContext, ArticleActivity.class);
        intent.putExtra("article",contentUrl);
        mContext.startActivity(intent);
    }

    class MyOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://手指按下
                    MyLog.i("ACTION_DOWN");
                    handler.removeMessages(CHANGEHEADVIEW);
                    break;

                case MotionEvent.ACTION_MOVE://手指在这个控件上移动
                    MyLog.i("ACTION_MOVE");
                    break;

                case MotionEvent.ACTION_UP://手指离开
                    MyLog.i("ACTION_UP");
                    handler.removeMessages(CHANGEHEADVIEW);
                    handler.sendEmptyMessageDelayed(CHANGEHEADVIEW,2000);
                    break;
            }
            return false;
        }
    }

    private void setImage(final String imageUrl, final ImageView imageView) {

//        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(imageUrl)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e("EYCK", "onResponse：error");
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
                        Log.e("EYCK", "onResponse：complete");
                        if(bitmap != null) {
                            if(imageView.getTag() == imageUrl) {
                                Log.d("EYCK","Home"+imageUrl);
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    }
                });

    }
}
