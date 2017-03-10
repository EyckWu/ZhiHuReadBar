package com.eyckwu.readbar.utils;

/**
 * Created by Eyck on 2017/2/27.
 */

public interface MyUrl {
    String HOMEURL = "http://news-at.zhihu.com/api/4/news/latest";
    //使用在 最新消息 中获得的 id，拼接在 http://news-at.zhihu.com/api/4/news/ 后，得到对应消息 JSON 格式的内容
    String NEWSCONTENT = "http://news-at.zhihu.com/api/4/news/";
    //过往消息 如果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
    String BEFORENEW = "http://news-at.zhihu.com/api/4/news/before/";
    //URL: http://news-at.zhihu.com/api/4/story-extra/#{id}
    //输入新闻的ID，获取对应新闻的额外信息，如评论数量，所获的『赞』的数量。
    String COMMENTS = "http://news-at.zhihu.com/api/4/story-extra/";
    //主题日报列表
    String ALLTHEME = "http://news-at.zhihu.com/api/4/themes";
    //URL: http://news-at.zhihu.com/api/4/theme/11
    //使用在 主题日报列表查看 中获得需要查看的主题日报的 id，拼接在 http://news-at.zhihu.com/api/4/theme/ 后，得到对应主题日报 JSON 格式的内容
    String THEME = "http://news-at.zhihu.com/api/4/theme/";
    //热门消息
    String HOT = "http://news-at.zhihu.com/api/3/news/hot";
    //缓存
    String CACHEJSON = "CACHEJSONDATA";
}
