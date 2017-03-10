package com.eyckwu.readbar.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eyckwu.readbar.R;
import com.eyckwu.readbar.model.bean.ArticleBean;
import com.eyckwu.readbar.model.bean.DailyThemeBean;
import com.eyckwu.readbar.model.bean.StoryExtra;
import com.eyckwu.readbar.utils.NetUtils;
import com.google.gson.Gson;

public class ArticleActivity extends Activity implements View.OnClickListener {

    private ImageButton ibArticleBack;
    private TextView tvArticalComment;
    private TextView tvArticalPraise;
//    private EditText test_url;//测试用
    private WebView wvArticleContent;
    private String articleUrl;
    private String testUrl;//测试用
    private String articleId;
    private String extraBaseUrl = "http://news-at.zhihu.com/api/4/story-extra/";
    private String extraUrl;
    private NetUtils netUtils;
    private NetUtils extraNet;
    private StoryExtra storyExtra;
    private Gson gson;
    private ArticleBean articleBean;
    private DailyThemeBean dailyThemeBean;
    private boolean isHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ibArticleBack = (ImageButton)findViewById( R.id.ib_article_back );
        tvArticalComment = (TextView)findViewById( R.id.tv_artical_comment );
        tvArticalPraise = (TextView)findViewById( R.id.tv_artical_praise );
        wvArticleContent = (WebView)findViewById( R.id.wv_article_content );
        WebSettings settings = wvArticleContent.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void initData() {
        articleUrl = "http://news-at.zhihu.com/api/4/news/3892357";
        Intent intent = getIntent();
        String contentUrl = intent.getStringExtra("article");
        String type = intent.getStringExtra("type");
        if(type == "home") {
            isHome = true;
        }else {
            isHome = false;
        }
        if(!TextUtils.isEmpty(contentUrl)) {
            articleUrl = contentUrl;
        }
        int length = articleUrl.length();//字符串长度
        String id = articleUrl.substring(length-7);
        extraUrl  = extraBaseUrl + id;
        loadArticleData();
        loadExtraData();
    }

    private void loadExtraData() {
        extraNet = new NetUtils();
        extraNet.getHtml(extraUrl);
        extraNet.setOnCompleteListener(new NetUtils.OnCompleteListener() {
            @Override
            public void onComplete(String response) {
                if(response == "获取失败"){
                    Toast.makeText(ArticleActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }else {
                    parseExtraJson(response);
                }
            }
        });
    }

    private void parseExtraJson(String response) {
        if(gson == null) {
            gson = new Gson();
        }
        storyExtra = gson.fromJson(response,StoryExtra.class);
        processExtra();
    }

    private void processExtra() {
        if(storyExtra != null) {
//            tvArticalComment.setText("12");
//            tvArticalPraise.setText("12");
            tvArticalComment.setText(storyExtra.getComments()+"");
            tvArticalPraise.setText(storyExtra.getPopularity()+"");
        }
    }

    private void loadArticleData() {
        getDataFromNet(articleUrl,true);
    }

    private void getDataFromNet(String articleUrl, boolean isArticleId) {
        netUtils = new NetUtils();
        netUtils.getHtml(articleUrl);
        netUtils.setOnCompleteListener(new NetUtils.OnCompleteListener() {
            @Override
            public void onComplete(String response) {
                if(response == "获取失败"){
                    Toast.makeText(ArticleActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }else {
                    parseJson(response);
                }
            }
        });
    }

    private void parseJson(String response) {
        if(gson == null) {
            gson = new Gson();
        }
        if(isHome) {
            articleBean = gson.fromJson(response,ArticleBean.class);
        }else {
            dailyThemeBean = gson.fromJson(response,DailyThemeBean.class);
        }
        processArticleData();
    }

    private void processArticleData() {
        if(isHome) {
            wvArticleContent.loadUrl(articleBean.getShare_url());
        }else {
            wvArticleContent.loadUrl(dailyThemeBean.getShare_url());
        }
    }

    private void initEvent() {
        ibArticleBack.setOnClickListener( this );
        tvArticalPraise.setOnClickListener( this );
        tvArticalComment.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        if ( v == ibArticleBack ) {
            finish();
        }else if ( v == tvArticalPraise ) {
//            String getUrl = test_url.getText().toString().trim();
//            if(!TextUtils.isEmpty(getUrl)) {
//                testUrl  = getUrl;
//                getDataFromNet(testUrl,true);
//            }else {
//                Toast.makeText(ArticleActivity.this, "测试网址不能为空", Toast.LENGTH_SHORT).show();
//            }
        }else if ( v == tvArticalComment ) {
            getDataFromNet(articleUrl,true);
        }
    }
}




