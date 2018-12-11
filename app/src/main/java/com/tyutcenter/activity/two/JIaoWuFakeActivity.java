package com.tyutcenter.activity.two;

import android.app.AlertDialog;
import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.constants.Constants;
import com.tyutcenter.databinding.ActivityJiaowuFakeBinding;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.MyWebView;

/**
 * Created by Administrator on 2017/3/13.
 * 直接跳转到教务处模块
 */
@Route(path = "/center/JiaoWuFakeActivity")
@ContentView(R.layout.activity_jiaowu_fake)
public class JIaoWuFakeActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainUi {

    private ActivityJiaowuFakeBinding mBinding;
    private MyWebView mMyWebView;
    @Override
    public void initTitle() {
        isShowToolBar(true);
        setCenterTitle("教务处");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityJiaowuFakeBinding) viewDataBinding;
        mMyWebView = mBinding.myWebView;
    }

    @Override
    public void initData() {
        mMyWebView.setOpenUrl(Constants.LoginUrl+Constants.MainUrl);
    }

    @Override
    protected void initEvent() {
        new AlertDialog.Builder(this)
                .setTitle("帮助")
                .setMessage("亲爱的太理助手用户们,由于没有学校内网vpn账号及密码,所以此功能还是以学校官网为主,如果大家希望在app内部查看成绩学分等相关信息,可以联系我qq:827623353")
                .setPositiveButton("确定",null)
                .create().show();
    }
}
