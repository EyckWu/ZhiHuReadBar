package com.eyckwu.readbar.controller.fragment;

import com.eyckwu.readbar.utils.MyUrl;

/**
 * Created by Eyck on 2017/2/26.
 */

public class InternetSafetyFragment extends OtherFragment {
    @Override
    protected void initCommon() {
        dailyUrl = "http://news-at.zhihu.com/api/4/theme/10";
        thememKey = MyUrl.THEME+"INTERNET";

    }
}





