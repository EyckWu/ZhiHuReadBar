package com.eyckwu.readbar.controller.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyckwu.readbar.R;
import com.eyckwu.readbar.model.bean.HomeDataBean;
import com.eyckwu.readbar.utils.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Eyck on 2017/2/27.
 */

public class HomeListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomeDataBean.StoriesBean> storiesBeens;
    private NetUtils netUtils;
//    private  mBitmapUtils;

    public HomeListViewAdapter(Context mContext,List<HomeDataBean.StoriesBean> storiesBeens){
        this.mContext = mContext;
        this.storiesBeens = storiesBeens;
    }

    @Override
    public int getCount() {
        return storiesBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item,null);
            viewHolder.tv_list_view_title = (TextView) convertView.findViewById(R.id.tv_list_view_title);
            viewHolder.iv_list_view_title = (ImageView) convertView.findViewById(R.id.iv_list_view_title);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeDataBean.StoriesBean storiesBean = storiesBeens.get(position);
        Log.d("EYCK","Home"+storiesBean.getTitle());
        viewHolder.tv_list_view_title.setText(storiesBean.getTitle());
//        if(netUtils == null) {
//            netUtils = new NetUtils();
//        }
        if(storiesBean.getImages() != null) {
            String imageUrl = storiesBean.getImages().get(0);
            //Log.d("EYCK","Home"+imageUrl);
            if(imageUrl != null) {
                Log.d("EYCK","Home"+imageUrl);
                viewHolder.iv_list_view_title.setTag(imageUrl);
                setImage(imageUrl,viewHolder);
            }

        }

        return convertView;
    }

    private void setImage(final String imageUrl, final ViewHolder viewHolder) {

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
                            if(viewHolder.iv_list_view_title.getTag() == imageUrl) {
                                Log.d("EYCK","Home"+imageUrl);
                                viewHolder.iv_list_view_title.setImageBitmap(bitmap);
                            }
                        }
                    }
                });

    }
    static class ViewHolder{
        TextView tv_list_view_title;
        ImageView iv_list_view_title;
    }
}
