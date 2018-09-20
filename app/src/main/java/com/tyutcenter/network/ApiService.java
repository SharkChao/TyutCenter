package com.tyutcenter.network;


import com.tyutcenter.model.HttpResult;
import com.tyutcenter.model.Message;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.model.ReSearchInfo;
import com.tyutcenter.model.test.CpuBean;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 网络接口类
 * Created by yuzhijun on 2016/3/29.
 */
@Singleton
public interface ApiService {
    //获取简评表商机情况
    @GET("ZXM/getOneZXM")
    Observable<HttpResult<List<ReSearchInfo>>> getResearchInfo(@Query("ID") String id);

    // 获取新闻信息 index需要手动加/
    @GET("")
    Observable<String> getNews1(@Url String url);
    @GET("")
    Observable<String> getNewsTZGG(@Url String url);

    //获取新闻总条数
    @GET("")
    Observable<String> getNewsCount(@Url String url);

    //获取通知公告新闻总条数
    @GET("")
    Observable<String> getNewsCountTZGG(@Url String url);

    @GET("Cpu")
    Observable<HttpResult<List<CpuBean>>>getCpuData();

    @GET("message/getMessageType")
    Observable<HttpResult<List<MessageType>>>getMessageTitle();
    @GET("message/getMessage")
    Observable<HttpResult<List<Message>>>getMessage(@Query("index")int index);

}
