package com.example.dovui.ui.fragment;

import androidx.viewpager.widget.ViewPager;

import com.example.dovui.R;
import com.example.dovui.adapter.GuideAdapter;
import com.example.dovui.ui.intalizes.BaseFragment;
import com.google.android.material.tabs.TabLayout;

public class GuideFragment extends BaseFragment {
    protected ViewPager mViewPager;
    protected TabLayout tabGuide;
    protected GuideAdapter mGuideAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initViews() {
//        mViewPager = view.findViewById(R.id.vpgGuide);
        mViewPager = view.findViewById(R.id.vpg);
        mViewPager.setAdapter(new GuideAdapter(getFragmentManager()));
        tabGuide = view.findViewById(R.id.tabGuide);
        tabGuide.setupWithViewPager(mViewPager);
    }

    @Override
    protected void main() {
//        assert getFragmentManager() != null;
//        mGuideAdapter = new GuideAdapter(getFragmentManager());
//        mViewPager.setAdapter(mGuideAdapter);
    }
}
