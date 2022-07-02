package com.example.dovui.ui.custom_views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.dovui.R;
import com.example.dovui.ui.activites.PlayActivity;

public class BackgroundView extends LinearLayout implements View.OnClickListener {
    private View mLnStartGame;
    private View mLnStartDetailPlay;
    private Button mBtnStartGame;
    private Button mBtnContinuedGame;
    private Button mBtnExitGame;
    private TextView mTvDetail;
    private BackgroundListening mBackgroundListening;
    public static final int ONSTART = 0;
    public static final int ONPAUSE = 1;
    public static final int ONFINISH = 2;
    public static final int ONPENDING = 3;
    private int type;
    MediaPlayer mediaPlayer;
    public BackgroundView(Context context) {
        this(context, null);
    }

    public BackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_background_view, null);
        initView(view);
        addView(view);
    }

    private void initView(View view) {
        mLnStartGame = view.findViewById(R.id.lnStartGame);
        mLnStartDetailPlay = view.findViewById(R.id.lnStartDetailPlay);

        mBtnContinuedGame = view.findViewById(R.id.btnContinuedGame);
        mBtnStartGame = view.findViewById(R.id.btnStartGame);
        mBtnExitGame = view.findViewById(R.id.btnExitGame);
        mTvDetail = view.findViewById(R.id.tvDetail);

        mBtnStartGame.setOnClickListener(this);
        mBtnContinuedGame.setOnClickListener(this);
        mBtnExitGame.setOnClickListener(this);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.clickcut);
    }
    public void setListening(BackgroundListening backgroundListening){
        mBackgroundListening = backgroundListening;
    }
    public void onStartGame(){
        type = ONSTART;
        mLnStartGame.setVisibility(View.VISIBLE);
        mLnStartDetailPlay.setVisibility(View.GONE);
    }
    @SuppressLint("SetTextI18n")
    public void onDetailGame(int score, int question, int sumQues){
        type = ONPENDING;
        mLnStartGame.setVisibility(View.GONE);
        mLnStartDetailPlay.setVisibility(View.VISIBLE);
        mBtnContinuedGame.setVisibility(View.GONE);
        mBtnExitGame.setVisibility(View.GONE);
        mTvDetail.setText("Điểm: "+score+"\nCâu: "+question+"/"+sumQues);
    }
    @SuppressLint("SetTextI18n")
    public void onPauseGame(int score, int question, int sumQues){
        type = ONPAUSE;
        mLnStartGame.setVisibility(View.GONE);
        mLnStartDetailPlay.setVisibility(View.VISIBLE);
        mBtnContinuedGame.setVisibility(View.VISIBLE);
        mBtnExitGame.setVisibility(View.VISIBLE);
        mTvDetail.setText("Điểm: "+score+"\nCâu: "+question+"/"+sumQues);
    }
    @SuppressLint("SetTextI18n")
    public void onFinishGame(int score, int question, int sumQues){
        type = ONFINISH;
        mLnStartGame.setVisibility(View.GONE);
        mLnStartDetailPlay.setVisibility(View.VISIBLE);
        mBtnContinuedGame.setVisibility(View.GONE);
        mBtnExitGame.setVisibility(View.VISIBLE);
        mTvDetail.setText("Điểm: "+score+"\nCâu: "+question+"/"+sumQues);
    }
    public int getType(){
        return type;
    }
    @Override
    public void onClick(View v) {
        if(v == mBtnStartGame){
            mediaPlayer.start();
            mBackgroundListening.onStartGame();
        }else if(v == mBtnContinuedGame){
            mediaPlayer.start();
            type = ONPENDING;
            mBackgroundListening.onContinuedGame();
        }else {
            mediaPlayer.start();
            if(type == ONFINISH){
                mBackgroundListening.onExitGame();
            }else xacNhanThoat();
        }
    }

    private void xacNhanThoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.ic_baseline_dangerous_24);
        builder.setTitle("Chú ý!");
        builder.setMessage("Nếu thoát giữa chừng điểm sẽ không được lưu, bạn có muốn thoát?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBackgroundListening.onExitGame();
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

    public interface BackgroundListening{
        void onStartGame();
        void onExitGame();
        void onContinuedGame();
    }

}
