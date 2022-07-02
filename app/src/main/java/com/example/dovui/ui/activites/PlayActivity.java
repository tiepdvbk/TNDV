package com.example.dovui.ui.activites;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dovui.R;
import com.example.dovui.common.objects.Level;
import com.example.dovui.common.objects.SharedPreference;
import com.example.dovui.model.database.DatabaseAccsess;
import com.example.dovui.model.object.Question;
import com.example.dovui.model.object.TopScore;
import com.example.dovui.ui.custom_views.AnswerView;
import com.example.dovui.ui.custom_views.BackgroundView;
import com.example.dovui.ui.custom_views.QuestionView;
import com.example.dovui.ui.intalizes.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity implements BackgroundView.BackgroundListening, AnswerView.AnswerCallBack {
    private TextView mTvTime;
    private BackgroundView mBackgroundView;
    private QuestionView mQuestionView;
    private AnswerView mAnswerViewA;
    private AnswerView mAnswerViewB;
    private AnswerView mAnswerViewC;
    private AnswerView mAnswerViewD;

    private List<Question> questionList;
    private int score = 0;
    private int scoreForAnswer = 10;
    private int currentQuestion = 0;
    private DatabaseAccsess mDatabaseAccsess;
    private CountDownTimer mCountDownTimer;
    private Level mLevel;
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayerTrue;
    MediaPlayer mediaPlayerFail;

    @Override
    protected int getLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initViews() {
        View mImgBack = findViewById(R.id.imgBack);
        mTvTime = findViewById(R.id.tvTime);
        mBackgroundView = findViewById(R.id.backgroundView);
        mQuestionView = findViewById(R.id.questionView);
        mAnswerViewA = findViewById(R.id.answerViewA);
        mAnswerViewB = findViewById(R.id.answerViewB);
        mAnswerViewC = findViewById(R.id.answerViewC);
        mAnswerViewD = findViewById(R.id.answerViewD);

        mBackgroundView.setListening(this);
        mAnswerViewA.setListening(this);
        mAnswerViewB.setListening(this);
        mAnswerViewC.setListening(this);
        mAnswerViewD.setListening(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.clickcut);
        mediaPlayerTrue = MediaPlayer.create(this, R.raw.ding1cut);
        mediaPlayerFail = MediaPlayer.create(this, R.raw.fail1cut);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                onBackPressed();
            }
        });
    }

    @Override
    protected void main() {
        mDatabaseAccsess  = DatabaseAccsess.getInstance(this);
//        Level level = Level.getType(getIntent().getExtras().getInt("LEVEL"));
        if (getIntent().getExtras() != null) {
            mLevel = Level.getType(getIntent().getExtras().getInt("LEVEL"));
            if (mLevel == Level.easy) {
                getQuestionListLevel1();
            } else if (mLevel == Level.medium) {
                getQuestionListLevel2();
            } else {
                getQuestionListLevel3();
            }
        }
        setupTimer();
        setVisibleQuestion(false);
        mBackgroundView.onStartGame();

    }

    private void setupTimer() {
        mLevel = Level.getType(getIntent().getExtras().getInt("LEVEL"));
        int time = mLevel.getValue() == 1?300 : mLevel.getValue() == 2?250 : 200;
        mTvTime.setText(String.valueOf(time));
        mCountDownTimer = new CountDownTimer(time*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvTime.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                hideQuestion();
                saveScore();
                mTvTime.setText("0");
                mBackgroundView.onFinishGame(score, currentQuestion, questionList.size());
            }
        };
    }
    private void resetTimer() {
        int time = Integer.parseInt(mTvTime.getText().toString());
        mTvTime.setText(String.valueOf(time));
        mCountDownTimer = new CountDownTimer(time*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvTime.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                hideQuestion();
                saveScore();
                mTvTime.setText("0");
                mBackgroundView.onFinishGame(score, currentQuestion, questionList.size());
            }
        };
        mCountDownTimer.start();
    }
    private void saveScore() {
        if(SharedPreference.getInstance(this).isPlayed()){
            int bestScore = SharedPreference.getInstance(this).getBestScore();
            if(bestScore < score){
                SharedPreference.getInstance(this).setBestScore(score);
            }
        }else {
            SharedPreference.getInstance(this).setBestScore(score);
        }
        TopScore topScore = new TopScore(score, mLevel.getValue());
        SharedPreference.getInstance(this).setTopScore(topScore);
        SharedPreference.getInstance(this).setPlayed();
    }
    private void showQuestion(){
        Question question = questionList.get(currentQuestion-1);
        mAnswerViewA.setAnswer("A", question.getAnswerA(), question.getTrueAnser().equals("A"));
        mAnswerViewB.setAnswer("B", question.getAnswerB(), question.getTrueAnser().equals("B"));
        mAnswerViewC.setAnswer("C", question.getAnswerC(), question.getTrueAnser().equals("C"));
        mAnswerViewD.setAnswer("D", question.getAnswerD(), question.getTrueAnser().equals("D"));
        mQuestionView.setData(currentQuestion, question.getQuestion());
        setVisibleQuestion(true);
        TranslateAnimation animShowQuestion = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_top_in);
        mQuestionView.startAnimation(animShowQuestion);

        TranslateAnimation animShowAnswerLeft = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_left_in);
        mAnswerViewA.startAnimation(animShowAnswerLeft);
        mAnswerViewC.startAnimation(animShowAnswerLeft);

        TranslateAnimation animShowAnswerRight = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_right_in);
        mAnswerViewB.startAnimation(animShowAnswerRight);
        mAnswerViewD.startAnimation(animShowAnswerRight);
    }
    private void hideQuestion(){
        setVisibleQuestion(true);
        TranslateAnimation animShowQuestion = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_top_out);
        mQuestionView.startAnimation(animShowQuestion);

        TranslateAnimation animShowAnswerLeft = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_left_out);
        mAnswerViewA.startAnimation(animShowAnswerLeft);
        mAnswerViewC.startAnimation(animShowAnswerLeft);

        TranslateAnimation animShowAnswerRight = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_right_out);
        mAnswerViewB.startAnimation(animShowAnswerRight);
        mAnswerViewD.startAnimation(animShowAnswerRight);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibleQuestion(false);
            }
        },400);
    }
    private void setVisibleQuestion(boolean isVisible){
        mQuestionView.setVisibility(isVisible? View.VISIBLE : View.GONE);
        mAnswerViewA.setVisibility(isVisible? View.VISIBLE : View.GONE);
        mAnswerViewB.setVisibility(isVisible? View.VISIBLE : View.GONE);
        mAnswerViewC.setVisibility(isVisible? View.VISIBLE : View.GONE);
        mAnswerViewD.setVisibility(isVisible? View.VISIBLE : View.GONE);
    }
    @Override
    public void onStartGame() {
        currentQuestion += 1;
        mCountDownTimer.start();
        showQuestion();
    }
    @Override
    public void onContinuedGame() {
        resetTimer();
        showQuestion();
    }
    @Override
    public void onExitGame() {
        finish();
    }
    public void getQuestionListLevel1(){
        mDatabaseAccsess.open();
        questionList = new ArrayList<>();
        questionList.addAll(mDatabaseAccsess.getGetQuestionLevel1());
    }
    public void getQuestionListLevel2(){
        mDatabaseAccsess.open();
        questionList = new ArrayList<>();
        questionList.addAll(mDatabaseAccsess.getGetQuestionLevel2());
    }
    public void getQuestionListLevel3(){
        mDatabaseAccsess.open();
        questionList = new ArrayList<>();
        questionList.addAll(mDatabaseAccsess.getGetQuestionLevel3());
    }
    @Override
    public void onAnswer(boolean isTrueAnswer) {
        if(isTrueAnswer){
            mediaPlayerTrue.start();
            score += scoreForAnswer;
        }else mediaPlayerFail.start();
        hideQuestion();
        if(currentQuestion == questionList.size()){
            saveScore();
            mCountDownTimer.cancel();
            mBackgroundView.onFinishGame(score, currentQuestion, questionList.size());
        }else {
            currentQuestion++;
            mBackgroundView.onDetailGame(score, currentQuestion, questionList.size());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showQuestion();
                }
            },400);
        }
    }
    @Override
    public void onBackPressed() {
        if(mBackgroundView.getType()==BackgroundView.ONPENDING){
            mBackgroundView.onPauseGame(score,currentQuestion, questionList.size());
            hideQuestion();
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }else if(mBackgroundView.getType()==BackgroundView.ONSTART){
            finish();
        }
    }
}
