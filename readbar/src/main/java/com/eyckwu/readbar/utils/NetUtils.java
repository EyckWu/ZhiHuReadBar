package com.eyckwu.readbar.utils;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Eyck on 2017/2/27.
 */

public class NetUtils {

    /**
     * 获取数据成功回调监听
     */
    public void setOnCompleteListener(@Nullable OnCompleteListener l) {
        completeListener = l;
    }

    private OnCompleteListener completeListener;
    public interface OnCompleteListener {
        void onComplete(String response);
    }

    /**
     * 获取图片成功回调监听
     */
    public void setOnImageListener(@Nullable OnImageListener l) {
        imageListener = l;
    }

    private OnImageListener imageListener;
    public interface OnImageListener {
        void onImage(Bitmap bitmap);
    }



    public void getHtml(String url)
    {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public void getImage(String url)
    {
//        mTv.setText("");
//        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
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
//                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
//                        Log.e("TAG", "onResponse：complete");
                        imageListener.onImage(bitmap);
//                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }

    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {
//            setTitle("loading...");
        }

        @Override
        public void onAfter(int id)
        {
//            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            String response = "获取失败";
            if(completeListener != null) {
                completeListener.onComplete(response);
            }
//            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id)
        {
//            Log.e("EYCK", "onResponse：complete");
            if(completeListener != null) {
                completeListener.onComplete(response);
            }
//            mTv.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
//                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
//                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
//            Log.e(TAG, "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }
}
