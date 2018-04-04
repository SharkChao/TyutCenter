package first.winning.com.tyutcenter.fragment.OneFragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.adapter.FragmentNews1Adapter;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseFragment;
import first.winning.com.tyutcenter.constants.Constants;
import first.winning.com.tyutcenter.databinding.FragmentNews1Binding;
import first.winning.com.tyutcenter.model.News;
import first.winning.com.tyutcenter.model.NewsCount;
import first.winning.com.tyutcenter.model.ResponseError;
import first.winning.com.tyutcenter.presenter.MainPresenter;
import first.winning.com.tyutcenter.utils.CommonUtil;
import first.winning.com.tyutcenter.views.RecycleViewDivider;

/**
 * Created by Admin on 2018/4/4.
 * 视频理工
 */

@ContentView(R.layout.fragment_news1)
public class NewsFragment4 extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.NewsUi{

    private String url = "lgsp";
    private static NewsFragment4 fragment_news1;
    FragmentNews1Binding mBinding;
    private RecyclerView mRvList;
    private FragmentNews1Adapter mAdapter;
    private int mAllCount;
    private int mCurrent;
    private int mPage;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        mBinding = (FragmentNews1Binding) viewDataBinding;
        mRvList = mBinding.rvList;
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new FragmentNews1Adapter(R.layout.fragment_news1_item);
        //第一次不需要进入加载更多的回调中
        mRvList.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        mRvList.setAdapter(mAdapter);
//        mAdapter.disableLoadMoreIfNotFullPage();


    }

    @Override
    protected void initData() {
//        getCallbacks().getNewsCount(url);
//        getCallbacks().getNews1(url,"",false);
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRvList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAllCount == 0){
                            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mCurrent >= mAllCount) {
                            //数据全部加载完毕
                            mAdapter.loadMoreEnd();
                        } else {
                            if (mCurrent < 8){
                                mCurrent = Constants.NEWS_ONE_PAGE;
                            }
                            int currentIndex = mCurrent/ Constants.NEWS_ONE_PAGE;
                            currentIndex++;
                            if (currentIndex == 1){
//                                getCallbacks().getNews1(url,"",false);
                            }else {
//                                getCallbacks().getNews1(url,"/"+(mPage-currentIndex+1),false);
                            }

                        }
                    }

                }, 1000);

            }
        });
    }
    @Override
    protected void handleArguments(Bundle arguments) {

    }

    @Override
    public void getNewsCallback(List<News> newsList,boolean isRefresh) {
        mAdapter.getData().addAll(newsList);
//        mAdapter.setNewData(newsList);
        mCurrent = mCurrent + newsList.size();
        if (isRefresh){
            mCurrent = newsList.size();
            mAdapter.getData().clear();
            mAdapter.setNewData(newsList);
        }

    }

    @Override
    public void getNewsCountCallback(NewsCount newsCount) {
        if (newsCount != null){
            mAllCount = newsCount.getCount();
            mPage = newsCount.getPage();
            mAdapter.loadMoreComplete();
        }
    }

    public static NewsFragment4 getInstance(){
        if (fragment_news1 == null){
            fragment_news1 = new NewsFragment4();
        }
        return fragment_news1;
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mAdapter.loadMoreFail();
    }
}
