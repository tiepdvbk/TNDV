package com.example.dovui.ui.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dovui.R;
import com.example.dovui.common.objects.SharedPreference;
import com.example.dovui.model.object.TopScore;
import com.example.dovui.ui.intalizes.BaseFragment;

import java.util.List;

public class ScoreFragment extends BaseFragment {
    TextView mTvEmptyScore, mTvBestScore;
    View mData;
    ListView mListScore;
    @Override
    protected int getLayout() {
        return R.layout.fragment_score;
    }
    @Override
    protected void initViews() {
        mTvEmptyScore = view.findViewById(R.id.emptyScore);
        mTvBestScore = view.findViewById(R.id.tvBestScore);
        mData = view.findViewById(R.id.data);
        mListScore = view.findViewById(R.id.listScore);
    }

    @Override
    protected void main() {
        if(!SharedPreference.getInstance(getActivity()).isPlayed()){
            mTvEmptyScore.setVisibility(View.VISIBLE);
            mData.setVisibility(View.GONE);
        }else{
            mTvEmptyScore.setVisibility(View.GONE);
            mData.setVisibility(View.VISIBLE);
            mTvBestScore.setText(String.valueOf(SharedPreference.getInstance(getActivity()).getBestScore()));
            mListScore.setAdapter(new TopScoreAdapter(SharedPreference.getInstance(getActivity()).getTopScore()));
        }
    }
    public class TopScoreAdapter extends BaseAdapter{
        private List<TopScore> mTopScoreList;
        public TopScoreAdapter(List<TopScore> topScoreList){
            mTopScoreList = topScoreList;
        }
        @Override
        public int getCount() {
            return mTopScoreList == null ? 0 : mTopScoreList.size();
        }

        @Override
        public TopScore getItem(int position) {
            return mTopScoreList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            View newView = view;
            if (newView == null) {
                newView = LayoutInflater.from(getContext()).inflate(R.layout.item_top_score, viewGroup , false);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) newView.findViewById(R.id.tvTextView);
                newView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) newView.getTag();
            }
            String level = getItem(position).getLevel() == 1? "Dễ" : getItem(position).getLevel()==2? "Trung bình" : "Khó";
            viewHolder.textView.setText("Điểm: "+getItem(position).getScore()+ " --- Mức độ chơi: "+level);
            return newView;
        }
        private class ViewHolder{
            private TextView textView;
        }
    }
}
