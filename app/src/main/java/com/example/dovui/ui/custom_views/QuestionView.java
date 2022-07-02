package com.example.dovui.ui.custom_views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dovui.R;

public class QuestionView extends LinearLayout {
    private TextView mTvTitleQuestion, mTvQuestion;
    public QuestionView(Context context) {
        this(context, null);
    }

    public QuestionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuestionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_question_view, null);
        initView(view);
        addView(view);
    }

    private void initView(View view) {
        mTvQuestion = view.findViewById(R.id.tvQuestion);
        mTvTitleQuestion = view.findViewById(R.id.tvTitleQuestion);
    }
    @SuppressLint("SetTextI18n")
    public void setData(int titleQuestion, String question){
        mTvTitleQuestion.setText("Câu "+titleQuestion+":");
        mTvQuestion.setText(Html.fromHtml(question));
    }
}
