package com.eyckwu.readbar.controller.fragment;

import com.eyckwu.readbar.utils.MyUrl;

/**
 * Created by Eyck on 2017/2/26.
 */

public class MovieFragment extends OtherFragment {
    @Override
    protected void initCommon() {
        dailyUrl = "http://news-at.zhihu.com/api/4/theme/3";
        thememKey = MyUrl.THEME+"MOVIE";

    }
}





