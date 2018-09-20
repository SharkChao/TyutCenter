package com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tyutcenter.R;
import com.tyutcenter.adapter.TabPagerAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.presenter.MainPresenter;

import com.tyutcenter.databinding.FragmentNewsBinding;

/**
 * Created by Admin on 2018/4/2.
 */
@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.MainUi {

    private FragmentNewsBinding mBinding;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState ) {
        mBinding = (FragmentNewsBinding) viewDataBinding;
        mTabLayout = mBinding.tabLayoutNews;
        mViewPager = mBinding.viewPager;
    }

    @Override
    protected void initData() {
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager,true);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void handleArguments(Bundle arguments) {

    }
}
