package com.example.dovui.ui.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.dovui.R;
import com.example.dovui.common.interfaces.LevelListening;
import com.example.dovui.common.objects.Level;
import com.example.dovui.model.database.DatabaseAccsess;
import com.example.dovui.ui.fragment.GuideFragment;
import com.example.dovui.ui.fragment.PlayFragment;
import com.example.dovui.ui.fragment.ScoreFragment;
import com.example.dovui.ui.intalizes.BaseActivity;
import com.google.android.material.progressindicator.BaseProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener, LevelListening {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Button btnPlay, btnScore, btnGuide, btnExit;
    private FrameLayout frHome;
    MediaPlayer mediaPlayer;
    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        btnPlay = findViewById(R.id.btnPlay);
        btnScore = findViewById(R.id.btnScore);
        btnGuide = findViewById(R.id.btnGuide);
        btnExit = findViewById(R.id.btnExit);
        frHome = findViewById(R.id.frHome);

        btnPlay.setOnClickListener(this);
        btnScore.setOnClickListener(this);
        btnGuide.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.clickcut);
    }
    @Override
    protected void main() {
        ShowAnimationButton();
        replayFr(new PlayFragment());
    }
    private void ShowAnimationButton() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(btnPlay);
        buttons.add(btnScore);
        buttons.add(btnGuide);
        buttons.add(btnExit);
        int timeBetween = 130;
        int timeStart = 10;
        for(Button button: buttons){
            TranslateAnimation AniShowInLeft = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_left_in);
            AniShowInLeft.setStartOffset(timeStart);
            button.setVisibility(View.VISIBLE);
            button.startAnimation(AniShowInLeft);
            timeStart += timeBetween;
        }
    }
    private void setVisibleAllBtn(boolean isVisible){
        btnPlay.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        btnScore.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        btnGuide.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        btnExit.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
    @Override
    public void onClick(View v) {
        if(v == btnPlay){
            replayFr(new PlayFragment());
            mediaPlayer.start();
        }else if (v == btnScore){
            mediaPlayer.start();
            replayFr(new ScoreFragment());
        }else if (v == btnGuide){
            mediaPlayer.start();
            replayFr(new GuideFragment());
        }else if(v == btnExit){
            mediaPlayer.start();
            xacNhanThoat();
        }
    }

    private void xacNhanThoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_baseline_dangerous_24);
        builder.setTitle("Chú ý!");
        builder.setMessage("Bạn có chắc chắn muốn thoát?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void replayFr(Fragment fragment) {
        frHome.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frHome , fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
    @Override
    public void onChoiceLevel(Level level) {
        Intent intent = new Intent(HomeActivity.this, PlayActivity.class);
        intent.putExtra("LEVEL", level.getValue());
        openActivity(intent, OPEN);
    }
}