package com.eyckwu.readbar.controller.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eyckwu.readbar.R;
import com.eyckwu.readbar.controller.activity.ArticleActivity;
import com.eyckwu.readbar.controller.adapter.HomeListViewAdapter;
import com.eyckwu.readbar.controller.adapter.HomePagerAdapter;
import com.eyckwu.readbar.model.bean.HomeDataBean;
import com.eyckwu.readbar.utils.DensityUtil;
import com.eyckwu.readbar.utils.MyLog;
import com.eyckwu.readbar.utils.MyUrl;
import com.eyckwu.readbar.utils.NetUtils;
import com.eyckwu.readbar.utils.SpTools;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyck on 2017/2/26.
 */
//headView = View.inflate(mContext, R.layout.main_content_head_view,null);
//iv_main_content_pic = (ImageView) headView.findViewById(R.id.iv_main_content_pic);
//vp_main_content_pic = (InterceptViewPager)headView.findViewById(R.id.vp_main_content_pic);
//tv_main_content_title = (TextView) headView.findViewById(R.id.tv_main_content_title);
//ll_main_content_point = (LinearLayout) headView.findViewById(R.id.ll_main_content_point);

public class HomeFragment extends BaseFragment {


    private Gson mGson;
    private HomeDataBean homeDataBean;
    private List<HomeDataBean.StoriesBean> storiesBeans;
    private List<HomeDataBean.TopStoriesBean> topStoriesBeans;

    private HomeListViewAdapter listViewAdapter;
    private String latestUrl = "http://news-at.zhihu.com/api/4/news/latest";
    private NetUtils homeNetUtils;
    private HomePagerAdapter homePagerAdapter;

    private static final int CHANGEHEADVIEW = 0;
    private int lastPosition = 0;

    private boolean isDragging = false;

    List<ImageView> points = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  CHANGEHEADVIEW:
                    int totalSize = topStoriesBeans.size();
                    int currItem = vp_main_content_pic.getCurrentItem();
                    currItem++;
                    vp_main_content_pic.setCurrentItem(currItem);
                    handler.removeMessages(CHANGEHEADVIEW);
                    handler.sendEmptyMessageDelayed(CHANGEHEADVIEW,2000);
                    break;

            }
        }
    };


    @Override
    protected void initListener() {
        iv_main_content_pic.setVisibility(View.GONE);
        lv_content_fragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(storiesBeans != null) {
                    String contentUrl = MyUrl.NEWSCONTENT + storiesBeans.get(position-1).getId();
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
        vp_main_content_pic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position%topStoriesBeans.size();
                MyLog.d("position=="+realPosition);
                setTextAndPoint(realPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE && isDragging == true) {//空闲
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(CHANGEHEADVIEW,2000);
                }else if(state == ViewPager.SCROLL_STATE_DRAGGING) {//拖拽

                } else if(state == ViewPager.SCROLL_STATE_SETTLING) {//滑动
                    isDragging = true;
                }
            }
        });


    }

    private void turnToActivity(String contentUrl) {
        Intent intent = new Intent(mContext, ArticleActivity.class);
        intent.putExtra("article",contentUrl);
        intent.putExtra("type","home");
        mContext.startActivity(intent);
    }

    @Override
    protected void initData() {
        String jsonCache = SpTools.getString(mContext, MyUrl.HOMEURL,"");
        if(!TextUtils.isEmpty(jsonCache)) {
            //解析并封装数据
            parseData(jsonCache);
        }else {
            getDataFromNet();
        }
    }

    private void getDataFromNet() {
        homeNetUtils = new NetUtils();
        homeNetUtils.getHtml(latestUrl);
        homeNetUtils.setOnCompleteListener(new NetUtils.OnCompleteListener() {
            @Override
            public void onComplete(String response) {
                if(response != "获取失败") {
                    SpTools.putString(mContext, MyUrl.HOMEURL,response);
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
        homeDataBean = mGson.fromJson(jsonCache,HomeDataBean.class);
        processData();
    }

    private void processData() {
        setHeadView();
        setListViewData();
    }

    private void setHeadView() {
        topStoriesBeans = homeDataBean.getTop_stories();
        setViewPager();
        initPoint();
//        setTextAndPoint(0);
    }

    private void setTextAndPoint(int realPosition) {
        MyLog.d("points=="+points.size());
        MyLog.d("topStoriesBeans.size()=="+topStoriesBeans.size());
        MyLog.d("topStoriesBeans.get(realPosition).getTitle()=="+topStoriesBeans.get(realPosition).getTitle());
        tv_main_content_title.setText(topStoriesBeans.get(realPosition).getTitle());
        if(points.size()>0) {
            points.get(lastPosition).setEnabled(false);
            points.get(realPosition).setEnabled(true);
        }
        lastPosition = realPosition;
    }

    private void initPoint() {
        MyLog.d("initPoint");
        points.clear();
        ll_main_content_point.removeAllViews();
//        points = new ArrayList<>();
        vp_main_content_pic.setCurrentItem(100-100%topStoriesBeans.size());
        int pointNum = topStoriesBeans.size();
        for (int i=0;i<pointNum;i++){
            ImageView point = new ImageView(mContext);
            point.setBackgroundResource(R.drawable.point_selector);
            int px = DensityUtil.dip2px(mContext,8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px,px);
            if(i==0) {
                point.setEnabled(true);
            }else {
                point.setEnabled(false);
                params.leftMargin = px;
            }
            point.setLayoutParams(params);
            points.add(point);
            ll_main_content_point.addView(point);
        }
    }

    private void setViewPager() {
        vp_main_content_pic.setVisibility(View.VISIBLE);
        homePagerAdapter = new HomePagerAdapter(mContext,topStoriesBeans,handler);
        vp_main_content_pic.setAdapter(homePagerAdapter);
        handler.sendEmptyMessageDelayed(CHANGEHEADVIEW,3000);
    }

    private void setListViewData() {
        storiesBeans = homeDataBean.getStories();
        listViewAdapter = new HomeListViewAdapter(mContext,storiesBeans);
        lv_content_fragment.setAdapter(listViewAdapter);
    }
}





