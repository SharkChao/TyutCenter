package com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tyutcenter.R;
import com.tyutcenter.adapter.ExpressPagerAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.databinding.FragmentExpressBinding;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.presenter.MainPresenter;

import java.util.List;

/**
 * Created by Admin on 2018/2/1.
 * 表白fragment
 */
@ContentView(R.layout.fragment_express)
public class ExpressFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.ExpressUi{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ExpressPagerAdapter mExpressPagerAdapter;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        FragmentExpressBinding binding = (FragmentExpressBinding) viewDataBinding;
        mTabLayout = binding.tabLayoutExpress;
        mViewPager = binding.viewPager;
    }

    @Override
    protected void initData() {
        mExpressPagerAdapter = new ExpressPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mExpressPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager,true);
        getCallbacks().getExpressPageTitle();
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

    @Override
    public void getMessageType(List<MessageType> list) {
        mExpressPagerAdapter.setTitles(list);
    }
}
