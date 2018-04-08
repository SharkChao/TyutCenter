package first.winning.com.tyutcenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import first.winning.com.tyutcenter.base.BaseFragment;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragment1;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragment2;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragment3;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragment4;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragment5;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragment6;
import first.winning.com.tyutcenter.fragment.OneFragment.NewsFragmentCPU;

/**
 * Created by Admin on 2018/4/4.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{

    private String[] titles = {"理工要闻","校园活动","通知公告","视频理工","学术活动","媒体理工"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position){
            case 0:
                NewsFragment1 newsFragment1 = NewsFragment1.getInstance();
                baseFragment = newsFragment1;
                break;
            case 1:
                NewsFragment2 newsFragment2 = NewsFragment2.getInstance();
                baseFragment = newsFragment2;
                break;
            case 2:
                NewsFragment3 newsFragment3 =  NewsFragment3.getInstance();
                baseFragment = newsFragment3;
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
            case 6:
                NewsFragmentCPU newsFragment7 =  NewsFragmentCPU.getInstance();
                baseFragment = newsFragment7;
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
