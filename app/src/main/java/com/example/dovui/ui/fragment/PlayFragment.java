package com.example.dovui.ui.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dovui.R;
import com.example.dovui.common.interfaces.LevelListening;
import com.example.dovui.common.objects.Level;
import com.example.dovui.ui.activites.HomeActivity;
import com.example.dovui.ui.activites.PlayActivity;
import com.example.dovui.ui.intalizes.BaseFragment;

public class PlayFragment extends BaseFragment implements View.OnClickListener{
    private Button btnDe, btnTrungbinh, btnKho;
    MediaPlayer mediaPlayer;
    @Override
    protected int getLayout() {
        return R.layout.fragment_play;
    }

    @Override
    protected void initViews() {
        btnDe = view.findViewById(R.id.btnDe);
        btnTrungbinh = view.findViewById(R.id.btnTrungbinh);
        btnKho = view.findViewById(R.id.btnKho);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.clickcut);
    }

    @Override
    protected void main() {
        btnDe.setOnClickListener(this);
        btnTrungbinh.setOnClickListener(this);
        btnKho.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (getActivity() instanceof LevelListening) {
            Level level = Level.easy;
            if (v == btnDe) {
                mediaPlayer.start();
                level = Level.easy;
            } else if (v == btnTrungbinh) {
                mediaPlayer.start();
                level = Level.medium;
            } else if (v == btnKho) {
                mediaPlayer.start();
                level = Level.difficult;
            }
            ((LevelListening) getActivity()).onChoiceLevel(level);
        }
    }
}
