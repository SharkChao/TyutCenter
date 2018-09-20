package com.tyutcenter.fragment.TwoFragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tyutcenter.R;
import com.tyutcenter.adapter.ExpressAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.databinding.FragmentExpressPageBinding;
import com.tyutcenter.model.Message;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.EmptyView;
import com.tyutcenter.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_express_page)
public class ExpressPageFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.ExpressFragmentUi{

    private MessageType mMessageType;
    private RecyclerView mRvList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ExpressAdapter mAdapter;
    private int index;
    private EmptyView mEmptyView;
    private List<Message>mMessageList = new ArrayList<>();

    public static ExpressPageFragment getInstance(){
        return new ExpressPageFragment();
    }
    @Override
    protected void handleArguments(Bundle arguments) {
        mMessageType = (MessageType) arguments.get("message_type");
    }

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState ) {
        FragmentExpressPageBinding binding = (FragmentExpressPageBinding) viewDataBinding;
        mRvList = binding.rvList;
        mSwipeRefreshLayout = binding.swipeRefreshLayout;
        mEmptyView = new EmptyView(mRvList.getContext(), (ViewGroup) mRvList.getParent());
        mEmptyView.setType(EmptyView.TYPE_LOADING);
        mEmptyView.setRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                getCallbacks().getExpressMessage(index);
            }
        });
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new ExpressAdapter(mMessageList);
        mAdapter.setEmptyView(mEmptyView.getView());
        //第一次不需要进入加载更多的回调中
        mRvList.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
       getCallbacks().getExpressMessage(index);
    }

    @Override
    protected void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 0;
                getCallbacks().getExpressMessage(index);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getCallbacks().getExpressMessage(++index);
            }
        },mRvList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance()
                        .build("/center/ExpressDetailActivity")
                        .navigation();
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }



    @Override
    public void getMessage(List<Message> list) {
        if (list  != null && list.size() > 0){
            if (index > 0){
                mMessageList.addAll(mAdapter.getData().size(),list);
            }else {
                mMessageList.clear();
                mMessageList.addAll(list);
            }
            mAdapter.setNewData(mMessageList);
            mAdapter.loadMoreComplete();
            mSwipeRefreshLayout.setRefreshing(false);
        }else {
            if (index == 0){
                mAdapter.getData().clear();
                mAdapter.setNewData(list);
            }
            mAdapter.loadMoreEnd(true);
        }

    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.loadMoreEnd();
    }
}
