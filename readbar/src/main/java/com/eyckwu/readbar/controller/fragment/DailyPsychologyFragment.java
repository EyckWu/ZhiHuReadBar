package com.eyckwu.readbar.controller.fragment;

import com.eyckwu.readbar.utils.MyUrl;

/**
 * Created by Eyck on 2017/2/26.
 */


//headView = View.inflate(mContext, R.layout.main_content_head_view,null);
//iv_main_content_pic = (ImageView) headView.findViewById(R.id.iv_main_content_pic);
//vp_main_content_pic = (InterceptViewPager)headView.findViewById(R.id.vp_main_content_pic);
//tv_main_content_title = (TextView) headView.findViewById(R.id.tv_fragment_header_view);
//ll_main_content_point = (LinearLayout) headView.findViewById(R.id.ll_main_content_point);
public class DailyPsychologyFragment extends OtherFragment {

    @Override
    protected void initCommon() {
        dailyUrl = "http://news-at.zhihu.com/api/4/theme/13";
        thememKey = MyUrl.THEME+"DAILYPSYCHOLOGY";

    }
}





