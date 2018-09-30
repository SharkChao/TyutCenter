package com.tyutcenter.activity;

import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.databinding.ActivityNewsDatailBinding;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.MyWebView;

/**
 * Created by Admin on 2018/4/4.
 */

@Route(path = "/center/BannerDetailActivity")
@ContentView(R.layout.activity_news_datail)
public class BannerDetailActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    private ActivityNewsDatailBinding mBinding;
    private MyWebView mMyWebView;

    @Override
    public void initTitle() {
        isShowToolBar(true);
        setCenterTitle("详情");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityNewsDatailBinding) viewDataBinding;
        mMyWebView = mBinding.myWebView;
    }

    @Override

    public void initData() {
        String url = getIntent().getStringExtra("url");
        mMyWebView.setOpenUrl(url);
    }

    @Override
    protected void initEvent() {

    }
}
