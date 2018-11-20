package com.tyutcenter.fragment.TwoFragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tyutcenter.R;
import com.tyutcenter.activity.ExpressDetailActivity;
import com.tyutcenter.adapter.ExpressAdapter;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.databinding.FragmentExpressPageBinding;
import com.tyutcenter.model.Message;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.utils.GlideImageLoader;
import com.tyutcenter.views.EmptyView;
import com.tyutcenter.views.RecycleViewDivider;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Iterator;
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
        mEmptyView = new EmptyView(mRvList.getContext());
        mEmptyView.setType(EmptyView.TYPE_LOADING);
        mEmptyView.setRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                getCallbacks().getExpressMessage(index,mMessageType.getId());
            }
        });
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new ExpressAdapter(mMessageList);
        mAdapter.setEmptyView(mEmptyView);
        //第一次不需要进入加载更多的回调中
        mRvList.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
       getCallbacks().getExpressMessage(index,mMessageType.getId());
    }

    @Override
    protected void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 0;
                getCallbacks().getExpressMessage(index,mMessageType.getId());
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getCallbacks().getExpressMessage(++index,mMessageType.getId());
            }
        },mRvList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ExpressDetailActivity.startExpressDetailActivity(getActivity(),mAdapter.getData().get(position));
            }
        });

    }

    public void setBanner(List<Message>list){
        final List<Message>temp = new ArrayList<>();
        List<String>urls = new ArrayList<>();
        List<String>titles = new ArrayList<>();
        Iterator<Message> iterator = list.iterator();
        while (iterator.hasNext()){
            Message message = iterator.next();
            if (message.getMsg_type().equals("14")&&urls.size()<4){
                temp.add(message);
                urls.add(message.getMsg_imgs().substring(1,message.getMsg_imgs().length()-1).toString());
                titles.add(message.getMsg_title());
                iterator.remove();
            }
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_express_type_banner,null);
        Banner banner = view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(urls);
        banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        if(mAdapter.getHeaderLayoutCount() != 0){
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(view);
        }else {
            mAdapter.addHeaderView(view);
        }
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ARouter.getInstance()
                        .build("/center/BannerDetailActivity")
                        .withString("url",temp.get(position).getMsg_content())
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
                setBanner(list);
                mMessageList.clear();
                mMessageList.addAll(list);
            }
            mAdapter.setNewData(mMessageList);
            mAdapter.loadMoreComplete();
        }else {
            if (index == 0){
                mAdapter.getData().clear();
                mAdapter.setNewData(list);
            }
            mAdapter.loadMoreEnd(true);
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.setMessage("暂无数据");
        mEmptyView.setType(EmptyView.TYPE_NO_DATA);
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.loadMoreEnd();
        mEmptyView.setMessage(error.getMessage());
        mEmptyView.setType(EmptyView.TYPE_ERROR);
    }
}
