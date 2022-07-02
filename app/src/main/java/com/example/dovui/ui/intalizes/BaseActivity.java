package com.example.dovui.ui.intalizes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dovui.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayout();
    protected abstract void initViews();
    protected abstract void main();
    protected static final int OPEN = 0;
    protected static final int CLOSE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initViews();
        main();
    }
    protected void openActivity(Intent intent, int type){
        startActivity(intent);
        if(type==OPEN){
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
        }else{
            overridePendingTransition(R.anim.anim_right_out, R.anim.anim_left_in);
        }
    }
}
