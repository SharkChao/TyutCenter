package first.winning.com.tyutcenter.fragment.OneFragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseFragment;
import first.winning.com.tyutcenter.databinding.FragmentTest1Binding;
import first.winning.com.tyutcenter.model.test.CpuBean;
import first.winning.com.tyutcenter.model.ResponseError;
import first.winning.com.tyutcenter.presenter.MainPresenter;

/**
 * Created by Admin on 2018/4/4.
 * 媒体理工
 */

@ContentView(R.layout.fragment_test1)
public class NewsFragmentCPU extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.TestUi{

    private static NewsFragmentCPU fragment_news1;
    FragmentTest1Binding mBinding;
    private TextView mTvTest;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        mBinding = (FragmentTest1Binding) viewDataBinding;
        mTvTest = mBinding.tvTest;
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

    public static NewsFragmentCPU getInstance(){
        if (fragment_news1 == null){
            fragment_news1 = new NewsFragmentCPU();
        }
        return fragment_news1;
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
    }

    @Override
    protected void lazyLoad() {
        getCallbacks().getTestData();
    }

    @Override
    public void getTestDataCallback(List<CpuBean> cpuBean) {
        mTvTest.setText(cpuBean.get(0).toString());
    }
}
