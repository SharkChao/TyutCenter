package com.tyutcenter.presenter;

import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.base.BasePresenter;
import com.tyutcenter.model.Comment;
import com.tyutcenter.model.Message;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.model.News;
import com.tyutcenter.model.NewsCount;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.model.Result;
import com.tyutcenter.model.Test;
import com.tyutcenter.model.TestResult;
import com.tyutcenter.model.User;
import com.tyutcenter.model.test.CpuBean;
import com.tyutcenter.network.ApiService;
import com.tyutcenter.network.RequestCallBack;
import com.tyutcenter.network.map.HttpResultFunc;
import com.tyutcenter.network.map.HttpResultFuncNews;
import com.tyutcenter.network.map.HttpResultFuncNewsCount;
import com.tyutcenter.network.map.HttpResultFuncNewsCountTZGG;
import com.tyutcenter.network.map.HttpResultFuncNewsTZGG;

import java.util.List;

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
        void getCommentCount(String message_id);
        void createComment(Comment comment);
        void getExpressPageTitle();
        void getExpressMessage(int index,int msg_type_id);
        void login();
        void getNews1(String url,boolean isrefresh);
        void  getNewsCount(String url);
        void getTestData();
        void getNewsTZGG(String url,boolean isrefresh);
        void  getNewsCountTZGG(String url);
        void getLoginAndroid(User user);
        void test(Test test);
    }

    @Override
    protected MainUiCallback createUiCallbacks(final MainUi ui) {
        return new MainUiCallback() {
            @Override
            public void getCommentCount(String message_id) {
                if (ui instanceof ExpressDetailUi){
                    mApiService.getCommentCount(message_id)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    ((ExpressDetailUi) ui).getCommentCount(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void createComment(Comment comment) {
                if (ui instanceof ExpressDetailUi){
                    mApiService.createComment(comment)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    ((ExpressDetailUi) ui).createComment(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void getExpressPageTitle() {
                if (ui instanceof ExpressUi)
                mApiService.getMessageTitle()
                        .map(new HttpResultFunc<List<MessageType>>())
                        .compose(MainPresenter.this.<List<MessageType>>applySchedulers())
                        .subscribe(new RequestCallBack<List<MessageType>>() {
                            @Override
                            public void onResponse(List<MessageType> response) {
                                ((ExpressUi) ui).getMessageType(response);
                            }

                            @Override
                            public void onFailure(ResponseError error) {
                                ui.onResponseError(error);
                            }
                        });

            }

            @Override
            public void getExpressMessage(int index,int msg_type_id) {
                if (ui instanceof ExpressFragmentUi)
                    mApiService.getMessage(index,msg_type_id)
                            .map(new HttpResultFunc<List<Message>>())
                            .compose(MainPresenter.this.<List<Message>>applySchedulers())
                            .subscribe(new RequestCallBack<List<Message>>() {
                                @Override
                                public void onResponse(List<Message> response) {
                                    ((ExpressFragmentUi) ui).getMessage(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
            }

            @Override
            public void login() {


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

            @Override
            public void getTestData() {
                if (ui instanceof TestUi) {
                    mApiService.getCpuData()
                            .map(new HttpResultFunc<List<CpuBean>>())
                            .compose(MainPresenter.this.<List<CpuBean>>applySchedulers())
                            .subscribe(new RequestCallBack<List<CpuBean>>() {

                                @Override
                                public void onResponse(List<CpuBean> response) {
                                    ((TestUi) ui).getTestDataCallback(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void getNewsTZGG(String url, final boolean isrefresh) {
                if (ui instanceof  NewsUi){
//                    BaseActivity.currentActivity.showProgressDialog();
                    mApiService.getNewsTZGG(url)
                            .map(new HttpResultFuncNewsTZGG())
                            .compose(MainPresenter.this.<List<News>>applySchedulers())
                            .subscribe(new RequestCallBack<List<News>>() {
                                @Override
                                public void onResponse(List<News> response) {
//                                    BaseActivity.currentActivity.hideProgressDialog();
                                    ((NewsUi) ui).getNewsCallback(response,isrefresh);
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
            public void getNewsCountTZGG(String url) {
                if (ui instanceof  NewsUi)
                    mApiService.getNewsCountTZGG(url)
                            .map(new HttpResultFuncNewsCountTZGG())
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

            @Override
            public void getLoginAndroid(User user) {
                if (ui instanceof MainHomeUi){
                    mApiService.loginAndroid(user)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    ((MainHomeUi) ui).getLoginResult(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    BaseActivity.currentActivity.hideProgressDialog();
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void test(Test test) {
                if (ui instanceof ExpressUi)
                    mApiService.test(test)
                            .compose(MainPresenter.this.<TestResult>applySchedulers())
                           .subscribe(new RequestCallBack<TestResult>() {
                               @Override
                               public void onResponse(TestResult response) {
                                   System.out.println(response);
                               }

                               @Override
                               public void onFailure(ResponseError error) {

                               }
                           });
            }
        };
    }

    //给具体ui调用
    public interface MainUi extends BasePresenter.BaseUi<MainUiCallback>{

    }
    public interface MainHomeUi extends MainUi{
        void getLoginResult(Result result);
    }
    public interface  NewsUi extends MainUi{
        void getNewsCallback(List<News>newsList,boolean string);
        void getNewsCountCallback(NewsCount newsCount);
    }
    public interface TestUi extends MainUi{
        void getTestDataCallback(List<CpuBean> string);
    }
    public interface ExpressUi extends MainUi{
        void getMessageType(List<MessageType>list);
    }
    public interface ExpressFragmentUi extends MainUi{
        void getMessage(List<Message>list);
    }
    public interface ExpressDetailUi extends MainUi{
        void createComment(Result result);
        void getCommentCount(Result result);
    }

}
