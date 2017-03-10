package com.eyckwu.readbar.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.eyckwu.readbar.R;

public class LauncherActivity extends Activity {
    private RelativeLayout activity_launcher;
    private AnimationSet animationSet;
    private boolean hasStart;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(!hasStart) {
                hasStart = true;
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launcher);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initAnimation();
        handler.sendEmptyMessageDelayed(0,3000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void initView() {
        setContentView(R.layout.activity_launcher);
        activity_launcher = (RelativeLayout)findViewById(R.id.activity_launcher);
    }

    private void initAnimation() {
        animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        activity_launcher.startAnimation(animationSet);
    }
}
