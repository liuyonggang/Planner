package com.lyg.planner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public MainViewPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles){
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public CharSequence getPageTitle(int postion){
        return mTitles.get(postion);
    };
}
