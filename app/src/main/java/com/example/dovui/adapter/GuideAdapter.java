package com.example.dovui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dovui.ui.fragment.FragmentGuide1;
import com.example.dovui.ui.fragment.FragmentGuide2;

public class GuideAdapter extends FragmentStatePagerAdapter {
    private final String[] lisTab = {"Cách chơi", "Điểm"};
    FragmentGuide1 fragmentGuide1;
    FragmentGuide2 fragmentGuide2;
    public GuideAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragmentGuide1 = new FragmentGuide1();
        fragmentGuide2 = new FragmentGuide2();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return fragmentGuide1;
        }else if(position==1){
            return fragmentGuide2;
        }
        else {
            //TODO nothing
        }
        return null;
    }

    @Override
    public int getCount() {
        return lisTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lisTab[position];
    }
}
