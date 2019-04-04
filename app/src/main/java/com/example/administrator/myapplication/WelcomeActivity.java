package com.example.administrator.myapplication;

import android.view.animation.Animation;

import com.example.administrator.myapplication.base.SplashActivity;


/**
 * @author kyle
 */

public class WelcomeActivity extends SplashActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    public Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivityFinish(MainActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }
}
