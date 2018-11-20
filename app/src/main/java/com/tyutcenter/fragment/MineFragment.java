package com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mob.tools.FakeActivity;
import com.mob.ums.gui.UMSGUI;
import com.mob.ums.gui.pages.MainPage;
import com.mob.ums.gui.themes.defaultt.DefaultTheme;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.databinding.FragmentMineBinding;
import com.tyutcenter.presenter.MainPresenter;

/**
 * Created by Admin on 2018/2/1.
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    private LinearLayout llRoot;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState ) {
        FragmentMineBinding binding = (FragmentMineBinding) viewDataBinding;
        llRoot = binding.llRoot;
    }

    @Override
    protected void initData() {

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
    public void click(View view){
        UMSGUI.showProfilePage();
    }
}
