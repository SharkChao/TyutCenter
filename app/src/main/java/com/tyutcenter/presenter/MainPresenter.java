package com.tyutcenter.presenter;

import com.tyutcenter.activity.ExpressDetailActivity;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.base.BasePresenter;
import com.tyutcenter.model.AppVersion;
import com.tyutcenter.model.Collect;
import com.tyutcenter.model.Comment;
import com.tyutcenter.model.CommentPraise;
import com.tyutcenter.model.Floor;
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

import okhttp3.ResponseBody;

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
        void getCollectByUserId(String user_id,String message_id);
        void setCollectByUserId(String user_id,String message_id,int collect);
        void createPraise(CommentPraise commentPraise);
        void getHotComment(String message_id,String user_id);
        void getAllComment(String message_id,String user_id);
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
        void getImage(String url);
        void login(String url,String name,String password,String yzm);
        void getAppVersion();
    }

    @Override
    protected MainUiCallback createUiCallbacks(final MainUi ui) {
        return new MainUiCallback() {
            @Override
            public void getCollectByUserId(String user_id, String message_id) {
                if (ui instanceof ExpressDetailActivity){
                    mApiService.getCollectByUserId(user_id,message_id)
                            .map(new HttpResultFunc<Collect>())
                            .compose(MainPresenter.this.<Collect>applySchedulers())
                            .subscribe(new RequestCallBack<Collect>() {
                                @Override
                                public void onResponse(Collect response) {
                                    ((ExpressDetailActivity) ui).getCollectByUserId(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void setCollectByUserId(String user_id, String message_id, int collect) {
                if (ui instanceof ExpressDetailActivity){
                    mApiService.setCollectByUserId(user_id,message_id,collect)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    ((ExpressDetailActivity) ui).setCollectByUserId(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void createPraise(CommentPraise commentPraise) {
                if (ui instanceof CommentUi){
                    mApiService.createPraise(commentPraise)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    ((CommentUi) ui).createPraise(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void getHotComment(String message_id,String user_id) {
                if (ui instanceof CommentUi){
                mApiService.getHotComment(message_id,user_id)
                         .map(new HttpResultFunc<List<Floor>>())
                        .compose(MainPresenter.this.<List<Floor>>applySchedulers())
                        .subscribe(new RequestCallBack<List<Floor>>() {
                            @Override
                            public void onResponse(List<Floor> response) {
                                ((CommentUi) ui).getHotComment(response);
                            }

                            @Override
                            public void onFailure(ResponseError error) {
                                ui.onResponseError(error);
                            }
                        });

                }
            }

            @Override
            public void getAllComment(String message_id,String user_id) {
                if (ui instanceof CommentUi){
                    mApiService.getAllComment(message_id,user_id)
                            .map(new HttpResultFunc<List<Floor>>())
                            .compose(MainPresenter.this.<List<Floor>>applySchedulers())
                            .subscribe(new RequestCallBack<List<Floor>>() {
                                @Override
                                public void onResponse(List<Floor> response) {
                                    ((CommentUi) ui).getAllComment(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });

                }
            }

            @Override
            public void getCommentCount(String message_id) {
                if (ui instanceof ExpressDetailUi || ui instanceof CommentUi){
                    mApiService.getCommentCount(message_id)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    if (ui instanceof ExpressDetailUi){
                                        ((ExpressDetailUi) ui).getCommentCount(response);
                                    }else if (ui instanceof CommentUi){
                                        ((CommentUi) ui).getCommentCount(response);
                                    }
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
                if (ui instanceof ExpressDetailUi || ui instanceof CommentUi){
                    mApiService.createComment(comment)
                            .map(new HttpResultFunc<Result>())
                            .compose(MainPresenter.this.<Result>applySchedulers())
                            .subscribe(new RequestCallBack<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    if (ui instanceof ExpressDetailUi){
                                        ((ExpressDetailUi)ui).createComment(response);
                                    }else if (ui instanceof CommentUi){
                                        ((CommentUi)ui).createComment(response);
                                    }
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

            @Override
            public void getImage(String url) {
                if (ui instanceof JiaoWuUi){
                    mApiService.getImage(url)
                            .compose(MainPresenter.this.<ResponseBody>applySchedulers())
                            .subscribe(new RequestCallBack<ResponseBody>() {
                                @Override
                                public void onResponse(ResponseBody response) {
                                    ((JiaoWuUi) ui).getImage(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void login(String url,String name, String password, String yzm) {
                if (ui instanceof JiaoWuUi){
                    mApiService.Login(url,name,password,yzm)
                            .compose(MainPresenter.this.<ResponseBody>applySchedulers())
                            .subscribe(new RequestCallBack<ResponseBody>() {
                                @Override
                                public void onResponse(ResponseBody response) {
                                    ((JiaoWuUi) ui).Login(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }

            @Override
            public void getAppVersion() {
                if (ui instanceof MainHomeUi) {
                    mApiService.getAppVersion()
                            .map(new HttpResultFunc<AppVersion>())
                            .compose(MainPresenter.this.<AppVersion>applySchedulers())
                            .subscribe(new RequestCallBack<AppVersion>() {

                                @Override
                                public void onResponse(AppVersion response) {
                                    ((MainHomeUi) ui).getAppVersion(response);
                                }

                                @Override
                                public void onFailure(ResponseError error) {
                                    ui.onResponseError(error);
                                }
                            });
                }
            }
        };
    }

    //给具体ui调用
    public interface MainUi extends BasePresenter.BaseUi<MainUiCallback>{

    }
    public interface  JiaoWuUi extends MainUi{
        void getImage(ResponseBody responseBody);
        void Login(ResponseBody responseBody);
    }
    public interface MainHomeUi extends MainUi{
        void getLoginResult(Result result);
        void getAppVersion(AppVersion appVersion);
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
        void getCollectByUserId(Collect collect);
        void setCollectByUserId(Result result);
    }
    public interface CommentUi extends MainUi{
        void getAllComment(List<Floor>list);
        void getHotComment(List<Floor>list);
        void createComment(Result result);
        void createPraise(Result result);
        void getCommentCount(Result result);
    }

}
