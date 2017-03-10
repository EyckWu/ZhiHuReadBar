package com.eyckwu.readbar.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eyckwu.readbar.R;
import com.eyckwu.readbar.view.InterceptViewPager;

/**
 * Created by Eyck on 2017/2/26.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View view ;
    protected View headView;
    protected ListView lv_content_fragment;
    protected SwipeRefreshLayout refresh_layout;
    protected ImageView iv_main_content_pic;
    protected InterceptViewPager vp_main_content_pic;
    protected TextView tv_main_content_title;
    protected LinearLayout ll_main_content_point;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        return view;
    }

    protected void initView() {
        view = View.inflate(mContext, R.layout.content_fragment,null);
        lv_content_fragment = (ListView) view.findViewById(R.id.lv_content_fragment);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        headView = View.inflate(mContext, R.layout.main_content_head_view,null);
        iv_main_content_pic = (ImageView) headView.findViewById(R.id.iv_main_content_pic);
        vp_main_content_pic = (InterceptViewPager)headView.findViewById(R.id.vp_main_content_pic);
        tv_main_content_title = (TextView) headView.findViewById(R.id.tv_main_content_title);
        ll_main_content_point = (LinearLayout) headView.findViewById(R.id.ll_main_content_point);
        lv_content_fragment.addHeaderView(headView);


        initListener();
    }

    protected abstract void initListener();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();
}
