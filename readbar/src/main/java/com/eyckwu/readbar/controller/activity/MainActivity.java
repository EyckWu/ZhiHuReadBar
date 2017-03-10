package com.eyckwu.readbar.controller.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eyckwu.readbar.R;
import com.eyckwu.readbar.controller.adapter.MenuAdapter;
import com.eyckwu.readbar.controller.fragment.BaseFragment;
import com.eyckwu.readbar.controller.fragment.BigCompanyFragment;
import com.eyckwu.readbar.controller.fragment.CartoonFragment;
import com.eyckwu.readbar.controller.fragment.DailyPsychologyFragment;
import com.eyckwu.readbar.controller.fragment.DesignFragment;
import com.eyckwu.readbar.controller.fragment.FinanceFragment;
import com.eyckwu.readbar.controller.fragment.GameFragment;
import com.eyckwu.readbar.controller.fragment.HomeFragment;
import com.eyckwu.readbar.controller.fragment.InternetSafetyFragment;
import com.eyckwu.readbar.controller.fragment.MovieFragment;
import com.eyckwu.readbar.controller.fragment.MusicFragment;
import com.eyckwu.readbar.controller.fragment.NoBoringFragment;
import com.eyckwu.readbar.controller.fragment.SportsFragment;
import com.eyckwu.readbar.controller.fragment.UserRecommendFragment;
import com.eyckwu.readbar.model.bean.MenuItem;
import com.eyckwu.readbar.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    /**
     * 切换不同Fragment
     */
    private List<BaseFragment> fragments;
    private int position = 0;
    Fragment mFragment = null;

    /**
     * 设置setting按钮的不同状态
     */
    private boolean isSetting = true;

    private DrawerLayout drawer_layout;
//    private ListView list_view_main;
    private ImageButton menu_title_bar;
    private ImageButton message_title_bar;
    private ImageButton setting_title_bar;
    private TextView tv_title_bar;

    private View menuHeaderView;
    private TextView login;
    private TextView favorities;
    private TextView download;

    private TextView toMain;
    private ListView listViewMenu;

    private String[] menus = new String[]{
            "日常心理学","设计日报","用户推荐日报","电影日报","不许无聊","大公司日报",
            "财经日报","互联网安全","开始游戏","音乐日报","动漫日报","体育日报"
    };
    private String[] themeId = new String[]{
            "13","4","12","3","11","5",
            "6","10","2","7","9","8"
    };
    /**
     * 最新消息
     */
    private String latestUrl = "http://news-at.zhihu.com/api/4/news/latest";
    /**
     * 过往消息
     */
    private String beforeUrl = "http://news-at.zhihu.com/api/4/news/before/20131119";
    /**
     * 主题日报内容
     */
    private String themeUrl = "http://news-at.zhihu.com/api/4/theme/11";

    private List<MenuItem> menuItems;
    private MenuAdapter menuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            LinearLayout title_bar = (LinearLayout) findViewById(R.id.title_bar);
            title_bar.setPadding(0,0,0,0);
            ViewGroup.LayoutParams layoutParams = title_bar.getLayoutParams();
            int height = DensityUtil.dip2px(MainActivity.this,53);
            layoutParams.height = height;
            title_bar.setLayoutParams(layoutParams);

        }
        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        list_view_main = (ListView)findViewById(R.id.list_view_main);
        menu_title_bar = (ImageButton)findViewById(R.id.menu_title_bar);
        tv_title_bar = (TextView)findViewById(R.id.tv_title_bar);
        message_title_bar = (ImageButton)findViewById(R.id.message_title_bar);
        setting_title_bar = (ImageButton)findViewById(R.id.setting_title_bar);
        menuHeaderView = View.inflate(MainActivity.this,R.layout.menu_header_view,null);

        login = (TextView)menuHeaderView.findViewById( R.id.login );
        favorities = (TextView)menuHeaderView.findViewById( R.id.favorities );
        download = (TextView)menuHeaderView.findViewById( R.id.download );
        toMain = (TextView)menuHeaderView.findViewById( R.id.to_main );
        listViewMenu = (ListView)findViewById( R.id.list_view_menu );
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new DailyPsychologyFragment());
        fragments.add(new DesignFragment());
        fragments.add(new UserRecommendFragment());
        fragments.add(new MovieFragment());
        fragments.add(new NoBoringFragment());
        fragments.add(new BigCompanyFragment());
        fragments.add(new FinanceFragment());
        fragments.add(new InternetSafetyFragment());
        fragments.add(new GameFragment());
        fragments.add(new MusicFragment());
        fragments.add(new CartoonFragment());
        fragments.add(new SportsFragment());
        /**
         * 默认显示主页
         */
        BaseFragment destFragment = fragments.get(0);
        switchFragments(mFragment,destFragment);
        /**
         * 初始化侧滑菜单
         */
        menuItems = new ArrayList<>();
        for (int i = 0;i<menus.length;i++){
            menuItems.add(new MenuItem(menus[i],false));
        }
        menuAdapter = new MenuAdapter(MainActivity.this,menuItems);
        listViewMenu.addHeaderView(menuHeaderView);
        listViewMenu.setAdapter(menuAdapter);
    }

    private void initEvent() {
        menu_title_bar.setOnClickListener(new MyOnClickListener());
        message_title_bar.setOnClickListener(new MyOnClickListener());
        setting_title_bar.setOnClickListener(new MyOnClickListener());
        /**
         * 侧滑菜单单击监听
         */
        login.setOnClickListener(new MyOnClickListener());
        favorities.setOnClickListener(new MyOnClickListener());
        download.setOnClickListener(new MyOnClickListener());
        toMain.setOnClickListener(new MyOnClickListener());
        /**
         * listView监听
         */
        listViewMenu.setOnItemClickListener(new MyOnItemClickListener());
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("EYCK",position+"");
            if(position != 0) {
                isSetting = false;
                message_title_bar.setVisibility(View.GONE);
                setting_title_bar.setBackgroundResource(R.drawable.theme_add);
                tv_title_bar.setText(menus[position-1]);
            }else {
                isSetting = true;
                message_title_bar.setVisibility(View.VISIBLE);
                setting_title_bar.setBackgroundResource(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
                tv_title_bar.setText("首页");
            }
            BaseFragment destFragment = fragments.get(position);
            switchFragments(mFragment,destFragment);
        }
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case  R.id.menu_title_bar:
                    drawer_layout.openDrawer(GravityCompat.START);
                    break;
                case  R.id.login:
                    Toast.makeText(MainActivity.this, "登录", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.favorities:
                    Toast.makeText(MainActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.download:
                    Toast.makeText(MainActivity.this, "离线下载", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.to_main:
                    position = 0;
                    isSetting = true;
                    message_title_bar.setVisibility(View.VISIBLE);
                    setting_title_bar.setBackgroundResource(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
                    BaseFragment destFragment = fragments.get(0);
                    switchFragments(mFragment,destFragment);
                    break;
                case  R.id.message_title_bar:
                    Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.setting_title_bar:
                    if(isSetting) {
                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "关注", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void switchFragments(Fragment from,Fragment to) {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        }
        if(from != to){
            if(to != null){
                mFragment = to;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //判断有没有添加
                if(!to.isAdded()){
                    if(from != null){
                        ft.hide(from);
                    }
                    if(to != null){
                        ft.add(R.id.fl_main,to).commit();
                    }
                }else{
                    if(from != null){
                        ft.hide(from);
                    }
                    if(to != null){
                        ft.show(to).commit();
                    }
                }
            }
        }
    }

}
