package first.winning.com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.adapter.TabPagerAdapter;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseFragment;
import first.winning.com.tyutcenter.databinding.FragmentNewsBinding;
import first.winning.com.tyutcenter.presenter.MainPresenter;
import first.winning.com.tyutcenter.presenter.MainPresenter.MainUi;

/**
 * Created by Admin on 2018/4/2.
 */
@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainUi{

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
        mViewPager.setAdapter(new TabPagerAdapter(getFragmentManager()));
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
