package first.winning.com.tyutcenter.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseFragment;
import first.winning.com.tyutcenter.presenter.MainPresenter;

/**
 * Created by Admin on 2018/2/1.
 * 交流fragment
 */
@ContentView(R.layout.fragment_mine)
public class ConnectFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {

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
}
