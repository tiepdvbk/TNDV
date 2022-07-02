package com.example.dovui.ui.activites;

import android.content.Intent;
import android.os.Handler;

import com.example.dovui.R;
import com.example.dovui.ui.intalizes.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final long DELAY_TIME = 800; //=0.8
    @Override
    protected int getLayout() {
        return R.layout.activity_plash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void main() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                openActivity(new Intent(SplashActivity.this, HomeActivity.class), OPEN);
                finish();
            }
        }, DELAY_TIME);
    }
}
