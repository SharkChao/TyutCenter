package com.tyutcenter.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mob.ums.gui.UMSGUI;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.data.UserData;
import com.tyutcenter.databinding.ActivityHomeBinding;
import com.tyutcenter.fragment.ExpressFragment;
import com.tyutcenter.fragment.JiaoWuFragment;
import com.tyutcenter.fragment.NewsFragment;
import com.tyutcenter.model.AppVersion;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.model.Result;
import com.tyutcenter.model.UpdateData;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.service.MobPushService;
import com.tyutcenter.utils.ActivityStack;
import com.tyutcenter.utils.CommonUtil;
import com.tyutcenter.utils.PermisionUtils;
import com.tyutcenter.utils.UpdateManager;

@ContentView(R.layout.activity_home)
@Route(path = "/center/HomeActivity")
public class HomeActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainHomeUi {

    private FragmentManager mFragmentManager;
    private ActivityHomeBinding mBinding;
    private FrameLayout mFrameLayout;
    private NewsFragment mNewsFragment;
    private ExpressFragment mExpressFragment;
    private JiaoWuFragment mJiaoWuFragment;
    private int mPosition;
    private AppVersion mAppVersion;

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

        initFragment();//初始化
        startPushService();

        //保存用户到服务端
        if (UserData.getUser() != null){
            getCallbacks().getLoginAndroid(UserData.getUser());
        }
        //判断软件是否需要更新
        getCallbacks().getAppVersion();
    }

    private void startPushService() {
        //启动服务
        Intent intent = new Intent(this, MobPushService.class);
        startService(intent);
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        if (mExpressFragment == null){
            mExpressFragment = new ExpressFragment();
        }
        if (mNewsFragment == null){
            mNewsFragment = new NewsFragment();
        }
        if (mJiaoWuFragment == null){
            mJiaoWuFragment = new JiaoWuFragment();
        }
        mFragmentManager.beginTransaction().add(R.id.frameLayout,mExpressFragment).add(R.id.frameLayout, mNewsFragment).add(R.id.frameLayout,mJiaoWuFragment).commit();
        mFragmentManager.beginTransaction().show(mExpressFragment).hide(mNewsFragment).hide(mJiaoWuFragment).commit();
        mBinding.bottomBar
                .addItem(new BottomNavigationItem(R.mipmap.iv_jiaowu, "表白墙").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_news, "新闻").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_group, "教务").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_mine, "我的").setActiveColorResource(R.color.colorPrimary))//依次添加item,分别icon和名称
                .setFirstSelectedPosition(0)//设置默认选择item
                .initialise();
    }

    @Override
    protected void initEvent() {
        mBinding.bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mPosition = position;
                if (position == 0){
                    setCenterTitle("表白墙");
                    mFragmentManager.beginTransaction().show(mExpressFragment).hide(mNewsFragment).hide(mJiaoWuFragment).commit();
                }else if (position == 1){
                    setCenterTitle("新闻");
                    mFragmentManager.beginTransaction().show(mNewsFragment).hide(mExpressFragment).hide(mJiaoWuFragment).commit();
                }else if (position == 2){
                    setCenterTitle("教务处");
//                    mJiaoWuFragment.showLoginJWDialog();
                    mFragmentManager.beginTransaction().show(mJiaoWuFragment).hide(mExpressFragment).hide(mNewsFragment).commit();
                }else if (position == 3){
                    setCenterTitle("我的");
//                    mFragmentManager.beginTransaction().show(mMineFragment).hide(mExpressFragment).hide(mNewsFragment).hide(mJiaoWuFragment).commit();
                    UMSGUI.showProfilePage();
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
//          Toast.makeText(this, "上传用户信息成功!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (mAppVersion != null) {
                UpdateData.setAppVersion(mAppVersion);
                UpdateManager manager = new UpdateManager();
                manager.checkUpdate();
            }
        } else {
            Toast.makeText(this, "您的软件将不能升级,请在[设置]-[授权管理]中打开", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getAppVersion(AppVersion appVersion) {
        if (appVersion != null) {
            UpdateData.setAppVersion(appVersion);
            if (Integer.parseInt(appVersion.getVersion_code()) > CommonUtil.getAppVersion(this)) {
                mAppVersion = appVersion;
                boolean per = PermisionUtils.verifyStoragePermissions(this);
                if (per) {
                    UpdateManager manager = new UpdateManager();
                    manager.checkUpdate();
                }
            }
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
    }
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
                ActivityStack.create().finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPosition == 3){
            mBinding.bottomBar.selectTab(2);
            setCenterTitle("教务处");
            mFragmentManager.beginTransaction().show(mJiaoWuFragment).hide(mExpressFragment).hide(mNewsFragment).commit();
        }
    }
}
