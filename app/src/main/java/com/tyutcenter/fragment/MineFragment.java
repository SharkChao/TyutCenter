package com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tyutcenter.databinding.FragmentMineBinding;
import com.mob.ums.gui.UMSGUI;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.presenter.MainPresenter;

/**
 * Created by Admin on 2018/2/1.
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    private Button mClick;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState ) {
        FragmentMineBinding binding = (FragmentMineBinding) viewDataBinding;
        mClick = binding.click;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
       mClick.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               UMSGUI.showProfilePage();
           }
       });
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
