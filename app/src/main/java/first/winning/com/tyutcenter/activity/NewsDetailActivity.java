package first.winning.com.tyutcenter.activity;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseActivity;
import first.winning.com.tyutcenter.databinding.ActivityNewsDatailBinding;
import first.winning.com.tyutcenter.presenter.MainPresenter;
import first.winning.com.tyutcenter.views.MyWebView;

/**
 * Created by Admin on 2018/4/4.
 */

@Route(path = "/center/NewsDetailActivity")
@ContentView(R.layout.activity_news_datail)
public class NewsDetailActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

    private ActivityNewsDatailBinding mBinding;
    private MyWebView mMyWebView;

    @Override
    public void initTitle() {
        isShowToolBar(true);
        setCenterTitle("新闻详情");
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityNewsDatailBinding) viewDataBinding;
        mMyWebView = mBinding.myWebView;
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra("url");
        mMyWebView.setOpenUrl(url);
    }

    @Override
    protected void initEvent() {

    }
}
