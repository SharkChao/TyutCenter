package first.winning.com.tyutcenter.activity;

import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.List;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseActivity;
import first.winning.com.tyutcenter.databinding.ActivityHomeBinding;
import first.winning.com.tyutcenter.fragment.ConnectFragment;
import first.winning.com.tyutcenter.fragment.JWFragment;
import first.winning.com.tyutcenter.fragment.MineFragment;
import first.winning.com.tyutcenter.fragment.NewsFragment;
import first.winning.com.tyutcenter.model.ReSearchInfo;
import first.winning.com.tyutcenter.presenter.MainPresenter;

@ContentView(R.layout.activity_home)
@Route(path = "/center/HomeActivity")
public class HomeActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainHomeUi {

    private FragmentManager mFragmentManager;
    private ActivityHomeBinding mBinding;
    private FrameLayout mFrameLayout;
    private NewsFragment mNewsFragment;
    private JWFragment mJWFragment;
    private ConnectFragment mConnectFragment;
    private MineFragment mMineFragment;

    @Override
    public void initTitle() {
        setCenterTitle("首页");
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
        if (mNewsFragment == null){
            mNewsFragment = new NewsFragment();
        }
        if (mNewsFragment == null){
            mNewsFragment = new NewsFragment();
        }
        if (mJWFragment == null){
            mJWFragment = new JWFragment();
        }
        if (mConnectFragment == null){
            mConnectFragment = new ConnectFragment();
        }
        if (mMineFragment == null){
            mMineFragment = new MineFragment();
        }
        mFragmentManager.beginTransaction().add(R.id.frameLayout,mNewsFragment).add(R.id.frameLayout,mJWFragment).add(R.id.frameLayout,mConnectFragment).add(R.id.frameLayout,mMineFragment).commit();
        mFragmentManager.beginTransaction().show(mNewsFragment).hide(mJWFragment).hide(mConnectFragment).hide(mMineFragment).commit();
        mBinding.bottomBar
                .addItem(new BottomNavigationItem(R.mipmap.iv_news, "新闻").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_jiaowu, "教务").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_group, "交流").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.iv_mine, "我的").setActiveColorResource(R.color.colorPrimary))//依次添加item,分别icon和名称
                .setFirstSelectedPosition(0)//设置默认选择item
                .initialise();//初始化

    }

    @Override
    protected void initEvent() {
        mBinding.bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 0){
                    mFragmentManager.beginTransaction().show(mNewsFragment).hide(mJWFragment).hide(mConnectFragment).hide(mMineFragment).commit();
                }else if (position == 1){
                    mFragmentManager.beginTransaction().show(mJWFragment).hide(mNewsFragment).hide(mConnectFragment).hide(mMineFragment).commit();
                }else if (position == 2){
                    mFragmentManager.beginTransaction().show(mConnectFragment).hide(mJWFragment).hide(mNewsFragment).hide(mMineFragment).commit();
                }else if (position == 3){
                    mFragmentManager.beginTransaction().show(mMineFragment).hide(mJWFragment).hide(mNewsFragment).hide(mConnectFragment).commit();
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
    public void LoginCallback(List<ReSearchInfo> reSearchInfos) {
//        Toast.makeText(this, reSearchInfos.size(), Toast.LENGTH_SHORT).show();
    }
}
