package first.winning.com.tyutcenter.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import first.winning.com.tyutcenter.base.BaseActivity;
import first.winning.com.tyutcenter.base.BasePresenter;
import first.winning.com.tyutcenter.model.News;
import first.winning.com.tyutcenter.model.NewsCount;
import first.winning.com.tyutcenter.model.ReSearchInfo;
import first.winning.com.tyutcenter.model.ResponseError;
import first.winning.com.tyutcenter.network.ApiService;
import first.winning.com.tyutcenter.network.RequestCallBack;
import first.winning.com.tyutcenter.network.map.HttpResultFunc;
import first.winning.com.tyutcenter.network.map.HttpResultFuncNews;
import first.winning.com.tyutcenter.network.map.HttpResultFuncNewsCount;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 2018/4/2.
 */

public class MainPresenter extends BasePresenter<MainPresenter.MainUi,MainPresenter.MainUiCallback>{

    private static MainPresenter mMainPresenter;
    public static MainPresenter getInstance(ApiService apiService){
        if (mMainPresenter == null){
            mMainPresenter = new MainPresenter(apiService);
        }
        return mMainPresenter;
    }
    private ApiService mApiService;
    private  MainPresenter(ApiService apiService){
        mApiService = apiService;
    }

    //获取数据之后回调
    public interface MainUiCallback{
        void login();
        void getNews1(String url,boolean isrefresh);
        void  getNewsCount(String url);
    }

    @Override
    protected MainUiCallback createUiCallbacks(final MainUi ui) {
        return new MainUiCallback() {
            @Override
            public void login() {
                mApiService.getResearchInfo("5").subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(new HttpResultFunc<List<ReSearchInfo>>())
                                .compose(MainPresenter.this.<List<ReSearchInfo>>applySchedulers())
                                .subscribe(new Consumer<List<ReSearchInfo>>() {
                                    @Override
                                    public void accept(List<ReSearchInfo> reSearchInfos) throws Exception {
                                        ((MainHomeUi)ui).LoginCallback(reSearchInfos);
                                    }
                                });

            }

            @Override
            public void getNews1(String url, final boolean isRefresh) {
                if (ui instanceof  NewsUi){
//                    BaseActivity.currentActivity.showProgressDialog();
                    mApiService.getNews1(url)
                            .map(new HttpResultFuncNews())
                            .compose(MainPresenter.this.<List<News>>applySchedulers())
                            .subscribe(new RequestCallBack<List<News>>() {
                                @Override
                                public void onResponse(List<News> response) {
//                                    BaseActivity.currentActivity.hideProgressDialog();
                                    ((NewsUi) ui).getNewsCallback(response,isRefresh);
                                }
                                @Override
                                public void onFailure(ResponseError error) {
//                                    BaseActivity.currentActivity.hideProgressDialog();
                                    ui.onResponseError(error);
                                }
                            });

                }
            }

            @Override
            public void getNewsCount(String url) {
                if (ui instanceof  NewsUi)
                mApiService.getNewsCount(url)
                        .map(new HttpResultFuncNewsCount())
                        .compose(MainPresenter.this.<NewsCount>applySchedulers())
                        .subscribe(new RequestCallBack<NewsCount>() {
                            @Override
                            public void onResponse(NewsCount response) {
                                ((NewsUi) ui).getNewsCountCallback(response);
                            }

                            @Override
                            public void onFailure(ResponseError error) {
                                BaseActivity.currentActivity.hideProgressDialog();
                                ui.onResponseError(error);
                            }
                        });
            }
        };
    }

    //给具体ui调用
    public interface MainUi extends BasePresenter.BaseUi<MainUiCallback>{

    }
    public interface MainHomeUi extends MainUi{
        void LoginCallback(List<ReSearchInfo>reSearchInfos);
    }
    public interface  NewsUi extends MainUi{
        void getNewsCallback(List<News>newsList,boolean string);
        void getNewsCountCallback(NewsCount newsCount);
    }
}
