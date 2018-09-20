package com.tyutcenter.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tyutcenter.adapter.interfaces.ExpressPageTitleCallback;
import com.tyutcenter.fragment.TwoFragment.ExpressPageFragment;
import com.tyutcenter.model.MessageType;

import java.util.ArrayList;
import java.util.List;

public class ExpressPagerAdapter extends FragmentPagerAdapter implements ExpressPageTitleCallback {
    List<MessageType>mTitles = new ArrayList<>();
    public ExpressPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ExpressPageFragment fragment = ExpressPageFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("message_type",mTitles.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).getMsg_type();
    }

    @Override
    public void setTitles(List<MessageType> list) {
        mTitles.clear();
        mTitles.addAll(list);
        notifyDataSetChanged();
    }
}
