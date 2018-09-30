package com.tyutcenter.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.data.UserData;
import com.tyutcenter.databinding.ActivityHomeBinding;
import com.tyutcenter.fragment.ConnectFragment;
import com.tyutcenter.fragment.ExpressFragment;
import com.tyutcenter.fragment.MineFragment;
import com.tyutcenter.fragment.NewsFragment;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.model.Result;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.service.MobPushService;

@ContentView(R.layout.activity_home)
@Route(path = "/center/HomeActivity")
public class HomeActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainHomeUi {

    private FragmentManager mFragmentManager;
    private ActivityHomeBinding mBinding;
    private FrameLayout mFrameLayout;
    private NewsFragment mNewsFragment;
    private ExpressFragment mExpressFragment;
    private ConnectFragment mConnectFragment;
    private MineFragment mMineFragment;

    @Override
    public void initTitle() {
        setCenterTitle("表白墙");
        isShowLeft(false);
    }
    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityHomeBinding) viewDataBinding;
        mFrameLayout = mBinding.frameLayout;
    }

    @Override
    public void initData() {
        mFragmentManager = getSupportFragmentManager();
        if (mExpressFragment == null){
            mExpressFragment = new ExpressFragment();
        }
        if (mNewsFragment == null){
            mNewsFragment = new NewsFragment();
        }
        if (mConnectFragment == null){
            mConnectFragment = new ConnectFragment();
        }
        if (mMineFragment == null){
            mMineFragment = new MineFragment();
        }
        mFragmentManager.beginTransaction().add(R.id.frameLayout,mExpressFragment).add(R.id.frameLayout, mNewsFragment).add(R.id.frameLayout,mConnectFragment).add(R.id.frameLayout,mMineFragment).commit();
        mFragmentManager.beginTransaction().show(mExpressFragment).hide(mNewsFragment).hide(mConnectFragment).hide(mMineFragment).commit();
        mBinding.bottomBar
                .addItem(new BottomNavigationItem(R.mipmap.iv_jiaowu, "表白墙").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_news, "新闻").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_group, "教务").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_mine, "我的").setActiveColorResource(R.color.colorPrimary))//依次添加item,分别icon和名称
                .setFirstSelectedPosition(0)//设置默认选择item
                .initialise();//初始化
        //启动服务
        Intent intent = new Intent(this, MobPushService.class);
        startService(intent);
        //保存用户到服务端
        if (UserData.getUser() != null){
            getCallbacks().getLoginAndroid(UserData.getUser());
        }
    }

    @Override
    protected void initEvent() {
        mBinding.bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 0){
                    setCenterTitle("表白墙");
                    mFragmentManager.beginTransaction().show(mExpressFragment).hide(mNewsFragment).hide(mConnectFragment).hide(mMineFragment).commit();
                }else if (position == 1){
                    setCenterTitle("新闻");
                    mFragmentManager.beginTransaction().show(mNewsFragment).hide(mExpressFragment).hide(mConnectFragment).hide(mMineFragment).commit();
                }else if (position == 2){
                    setCenterTitle("教务处");
                    mFragmentManager.beginTransaction().show(mConnectFragment).hide(mExpressFragment).hide(mNewsFragment).hide(mMineFragment).commit();
                }else if (position == 3){
                    setCenterTitle("我的");
                    mFragmentManager.beginTransaction().show(mMineFragment).hide(mExpressFragment).hide(mNewsFragment).hide(mConnectFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    @Override
    public void getLoginResult(Result result) {
        if (result != null && result.getCode() > 0){
            Toast.makeText(this, "上传用户信息成功!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
    }
}
