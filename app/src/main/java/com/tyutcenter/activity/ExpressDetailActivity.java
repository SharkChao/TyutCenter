package com.tyutcenter.activity;

import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.presenter.MainPresenter;

@ContentView(R.layout.activity_express_detail)
@Route(path = "/center/ExpressDetailActivity")
public class ExpressDetailActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.ExpressDetailUi{
    @Override
    public void initTitle() {
        isShowToolBar(true);
        setCenterTitle("表白墙");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
