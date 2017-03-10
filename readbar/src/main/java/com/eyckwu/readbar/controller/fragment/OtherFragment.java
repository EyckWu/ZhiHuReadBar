package com.eyckwu.readbar.controller.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.eyckwu.readbar.controller.activity.ArticleActivity;
import com.eyckwu.readbar.controller.adapter.ThemeListViewAdapter;
import com.eyckwu.readbar.model.bean.ThemeBean;
import com.eyckwu.readbar.utils.MyUrl;
import com.eyckwu.readbar.utils.NetUtils;
import com.eyckwu.readbar.utils.SpTools;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Eyck on 2017/3/9.
 */

public abstract class OtherFragment extends BaseFragment {
    private NetUtils dailyNetUtils;
    protected String dailyUrl = "http://news-at.zhihu.com/api/4/theme/13";
    private Gson mGson;
    private ThemeBean themeBean;
    private List<ThemeBean.StoriesBean> storiesBeans;
    private String image;
    private String description;
    private String name;
    private ThemeListViewAdapter themeListViewAdapter;
    protected String thememKey = MyUrl.THEME+"DAILYPSYCHOLOGYFRAGMENT";


    @Override
    protected void initListener() {
        vp_main_content_pic.setVisibility(View.GONE);
        ll_main_content_point.setVisibility(View.GONE);
        iv_main_content_pic.setVisibility(View.VISIBLE);

        initCommon();

        lv_content_fragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(storiesBeans != null) {
                    String contentUrl = MyUrl.NEWSCONTENT + storiesBeans.get(position).getId();
                    turnToActivity(contentUrl);
                }
            }
        });
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet();
            }
        });

    }

    private void turnToActivity(String contentUrl) {
        Intent intent = new Intent(mContext, ArticleActivity.class);
        intent.putExtra("article",contentUrl);
        intent.putExtra("type","other");
        mContext.startActivity(intent);
    }

    protected abstract void initCommon();


    @Override
    protected void initData() {
        String jsonCache = SpTools.getString(mContext, thememKey,"");
        if(!TextUtils.isEmpty(jsonCache)) {
            //解析并封装数据
            parseData(jsonCache);
        }else {
            getDataFromNet();
        }
    }

    private void getDataFromNet() {
        dailyNetUtils = new NetUtils();
        dailyNetUtils.getHtml(dailyUrl);
        dailyNetUtils.setOnCompleteListener(new NetUtils.OnCompleteListener() {
            @Override
            public void onComplete(String response) {
                if(response != "获取失败") {
                    SpTools.putString(mContext, thememKey,response);
                    //解析并封装数据
                    refresh_layout.setRefreshing(false);
                    parseData(response);
                }else {
                    Toast.makeText(mContext, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void parseData(String jsonCache) {
        if(mGson == null) {
            mGson = new Gson();
        }
        themeBean = mGson.fromJson(jsonCache,ThemeBean.class);
        processData();
    }

    private void processData() {
        setHeadView();
        setListViewData();
    }

    private void setHeadView() {
        Log.d("EYCK",themeBean.getDescription());
        if(!TextUtils.isEmpty(themeBean.getDescription())) {
            tv_main_content_title.setText(themeBean.getDescription());
        }else {
            tv_main_content_title.setVisibility(View.GONE);
        }
//
        if(!TextUtils.isEmpty(themeBean.getImage())) {
            if(dailyNetUtils == null) {
                dailyNetUtils = new NetUtils();
            }
            dailyNetUtils.getImage(themeBean.getImage());
            dailyNetUtils.setOnImageListener(new NetUtils.OnImageListener() {
                @Override
                public void onImage(Bitmap bitmap) {
                    iv_main_content_pic.setImageBitmap(bitmap);
                }
            });
        }
    }

    private void setListViewData() {
        storiesBeans = themeBean.getStories();
        themeListViewAdapter = new ThemeListViewAdapter(mContext,storiesBeans);
        lv_content_fragment.setAdapter(themeListViewAdapter);
    }
}
