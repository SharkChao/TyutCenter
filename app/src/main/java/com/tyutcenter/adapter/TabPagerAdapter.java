package com.tyutcenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.fragment.OneFragment.NewsFragment1;
import com.tyutcenter.fragment.OneFragment.NewsFragment2;
import com.tyutcenter.fragment.OneFragment.NewsFragment3;
import com.tyutcenter.fragment.OneFragment.NewsFragment4;
import com.tyutcenter.fragment.OneFragment.NewsFragment5;
import com.tyutcenter.fragment.OneFragment.NewsFragment6;

/**
 * Created by Admin on 2018/4/4.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{

    private String[] titles = {"通知公告","校园活动","理工要文","视频理工","学术活动","媒体理工"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position){
            case 0:
                NewsFragment3 newsFragment3 = NewsFragment3.getInstance();
                baseFragment = newsFragment3;
                break;
            case 1:
                NewsFragment2 newsFragment2 = NewsFragment2.getInstance();
                baseFragment = newsFragment2;
                break;
            case 2:
                NewsFragment1 newsFragment1 =  NewsFragment1.getInstance();
                baseFragment = newsFragment1;
                break;
            case 3:
                NewsFragment4 newsFragment4 = NewsFragment4.getInstance();
                baseFragment = newsFragment4;
                break;
            case 4:
                NewsFragment5 newsFragment5 = NewsFragment5.getInstance();
                baseFragment = newsFragment5;
                break;
            case 5:
                NewsFragment6 newsFragment6 =  NewsFragment6.getInstance();
                baseFragment = newsFragment6;
                break;
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
