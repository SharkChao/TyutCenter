package first.winning.com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseFragment;
import first.winning.com.tyutcenter.databinding.FragmentNewsBinding;

/**
 * Created by Admin on 2018/4/2.
 */
@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseFragment{

    private FragmentNewsBinding mBinding;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        mBinding = (FragmentNewsBinding) viewDataBinding;
        mTabLayout = mBinding.tabLayout;
        mViewPager = mBinding.viewPager;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void handleArguments(Bundle arguments) {

    }
}
