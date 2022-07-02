package com.example.dovui.ui.custom_views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dovui.R;

public class AnswerView extends RelativeLayout {

    private RelativeLayout mRltRoot;
    private TextView mTvTitle, mTvAnswer;
    private AnswerCallBack mAnswerCallBack;
    private boolean mIsTrueAnswer;

    public AnswerView(Context context) {
        this(context, null);
    }

    public AnswerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnswerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_answer_view, null);
        initView(view);
        addView(view);
    }

    private void initView(View view) {
        mTvAnswer = view.findViewById(R.id.tvAnswer);
        mTvTitle = view.findViewById(R.id.tvTitle);
        mRltRoot = view.findViewById(R.id.rltRoot);

        mRltRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerCallBack.onAnswer(mIsTrueAnswer);
            }
        });
    }
    public void setAnswer(String title, String answer, boolean isTrueAnswer){
        mIsTrueAnswer = isTrueAnswer;
        mTvTitle.setText(title);
        mTvAnswer.setText(Html.fromHtml(answer));
    }
    public void setListening(AnswerCallBack answerCallBack){
        mAnswerCallBack = answerCallBack;
    }
    public interface AnswerCallBack{
        void onAnswer(boolean isTrueAnswer);
    }
}
