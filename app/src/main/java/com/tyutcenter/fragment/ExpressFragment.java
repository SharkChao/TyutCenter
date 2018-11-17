package com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.tyutcenter.R;
import com.tyutcenter.adapter.ExpressPagerAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.databinding.FragmentExpressBinding;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.EmptyView;
import com.tyutcenter.views.lazyviewpager.LazyViewPager;

import java.util.List;

/**
 * Created by Admin on 2018/2/1.
 * 表白fragment
 */
@ContentView(R.layout.fragment_express)
public class ExpressFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.ExpressUi{

    private TabLayout mTabLayout;
    private LazyViewPager mViewPager;
    private ExpressPagerAdapter mExpressPagerAdapter;
    private EmptyView mEmptyView;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        FragmentExpressBinding binding = (FragmentExpressBinding) viewDataBinding;
        mTabLayout = binding.tabLayoutExpress;
        mViewPager = binding.viewPager;
        mEmptyView = binding.emptyView;
        mEmptyView.setType(EmptyView.TYPE_LOADING);
        mEmptyView.setRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmptyView.TYPE_LOADING.equals(mEmptyView.getType())){
                    mEmptyView.setType(EmptyView.TYPE_LOADING);
                    getCallbacks().getExpressPageTitle();
                }
            }
        });
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
        mEmptyView.setType(EmptyView.TYPE_NO_DATA);
        mEmptyView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mEmptyView.setType(EmptyView.TYPE_NO_DATA);
        mEmptyView.setMessage(error.getMessage());
        mEmptyView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
    }
}
