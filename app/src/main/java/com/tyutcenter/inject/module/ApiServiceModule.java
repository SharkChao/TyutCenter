package com.tyutcenter.inject.module;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tyutcenter.constants.Constants;
import com.tyutcenter.inject.StringConverterFactory;
import com.tyutcenter.network.ApiService;
import com.tyutcenter.network.ResponseErrorProxy;
import com.tyutcenter.presenter.MainPresenter;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络相关module
 * Created by yuzhijun on 2016/4/28.
 */
@Module
public class ApiServiceModule {
    private static final int DEFAULT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 3;

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClientBuilder(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())  //添加拦截器
                //缓存cookie
//                .cookieJar(new CookieManager(MyLeanCloudApp.getInstance()))
//                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
//                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();
//        OkHttpClient httpClientBuilder = new OkHttpClient();
//        httpClientBuilder.newBuilder()
//                .addInterceptor(interceptor)
//                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
//                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true);
        return httpClientBuilder;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient OkHttpClientBuilder){
        return  new Retrofit.Builder()
                .baseUrl(Constants.BASE_SERVER_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClientBuilder)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    ApiService getByProxy(Class<? extends ApiService> apiService, Retrofit retrofit){
        ApiService api = retrofit.create(apiService);
        return (ApiService) Proxy.newProxyInstance(apiService.getClassLoader(),new Class<?>[] { apiService },new ResponseErrorProxy(api));
    }
    @Provides
    @Singleton
    MainPresenter provideMainPresenter(ApiService apiService){
        return MainPresenter.getInstance(apiService);
    }
}
