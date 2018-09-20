package com.tyutcenter.fragment.OneFragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import com.tyutcenter.R;
import com.tyutcenter.adapter.FragmentNewsNormalAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.constants.Constants;
import com.tyutcenter.databinding.FragmentNews1Binding;
import com.tyutcenter.model.News;
import com.tyutcenter.model.NewsCount;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.utils.CommonUtil;
import com.tyutcenter.views.RecycleViewDivider;

/**
 * Created by Admin on 2018/4/4.
 * 媒体理工
 */

@ContentView(R.layout.fragment_news1)
public class NewsFragment6 extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.NewsUi{

    private String url = "xyxw/mtlg";
    private static NewsFragment6 fragment_news1;
    FragmentNews1Binding mBinding;
    private RecyclerView mRvList;
    private FragmentNewsNormalAdapter mAdapter;
    private int mAllCount;
    private int mCurrent;
    private int mPage;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Gson mGson;

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        mBinding = (FragmentNews1Binding) viewDataBinding;
        mRvList = mBinding.rvList;
        mSwipeRefreshLayout = mBinding.swipeRefreshLayout;
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new FragmentNewsNormalAdapter(R.layout.fragment_news1_item);
        //第一次不需要进入加载更多的回调中
        mRvList.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mGson = new Gson();
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
                            mAdapter.loadMoreEnd();
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
                                getCallbacks().getNews1(Constants.base_url+"/"+url+".htm",false);
                            }else {
                                getCallbacks().getNews1(Constants.base_url+"/"+url+"/"+(mPage-currentIndex+1)+".htm",false);
                            }

                        }
                    }

                }, 1000);

            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance()
                        .build("/center/NewsDetailActivity")
                        .withString("url",mAdapter.getData().get(position).getDetailUrl())
                        .navigation();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCallbacks().getNews1(Constants.base_url+"/"+url+".htm",true);
            }
        });
    }

    @Override
    protected void handleArguments(Bundle arguments) {

    }

    @Override
    public void getNewsCallback(List<News> newsList,boolean isRefresh) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.getData().addAll(newsList);
        mAdapter.notifyDataSetChanged();
        mCurrent = mCurrent + newsList.size();
        mAdapter.loadMoreComplete();
        if (isRefresh){
            mCurrent = newsList.size();
            mAdapter.getData().clear();
            mAdapter.setNewData(newsList);
            Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
        }
        CommonUtil.setShardPString("news6", mGson.toJson(mAdapter.getData()));
    }

    @Override
    public void getNewsCountCallback(NewsCount newsCount) {
        if (newsCount != null){
            mAllCount = newsCount.getCount();
            mPage = newsCount.getPage();
            CommonUtil.setShardPString("news6", mGson.toJson(mAdapter.getData()));
        }
    }

    public static NewsFragment6 getInstance(){
        if (fragment_news1 == null){
            fragment_news1 = new NewsFragment6();
        }
        return fragment_news1;
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mAdapter.loadMoreFail();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void lazyLoad() {
        String news1 = CommonUtil.getShardPStringByKey("news6");
        String news1_count = CommonUtil.getShardPStringByKey("news6_count");

        if (CommonUtil.isStrEmpty(news1)){
            getCallbacks().getNews1(Constants.base_url+"/"+url+".htm",false);
        }else {
            List<News> newsList = mGson.fromJson(news1, new TypeToken<List<News>>() {}.getType());
            getNewsCallback(newsList,false);
        }
        if (CommonUtil.isStrEmpty(news1_count)){
            String url = Constants.base_url+"/xyxw/mtlg.htm";
            getCallbacks().getNewsCount(url);
        }else {
            NewsCount newsCount = mGson.fromJson(news1_count, NewsCount.class);
            getNewsCountCallback(newsCount);
        }
    }
}
