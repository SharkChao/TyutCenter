package com.tyutcenter.network;


import com.tyutcenter.model.AppVersion;
import com.tyutcenter.model.Collect;
import com.tyutcenter.model.Comment;
import com.tyutcenter.model.CommentPraise;
import com.tyutcenter.model.Floor;
import com.tyutcenter.model.HttpResult;
import com.tyutcenter.model.Message;
import com.tyutcenter.model.MessageType;
import com.tyutcenter.model.Result;
import com.tyutcenter.model.Test;
import com.tyutcenter.model.TestResult;
import com.tyutcenter.model.User;
import com.tyutcenter.model.test.CpuBean;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 网络接口类
 * Created by yuzhijun on 2016/3/29.
 */
@Singleton
public interface ApiService {
    //登录
    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> Login(@Url String url,@Field("zjh") String account, @Field("mm") String password, @Field("v_yzm") String yzm);
    //获取验证码
    @GET("")
    Observable<ResponseBody>getImage(@Url String url);

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
    Observable<HttpResult<List<Message>>>getMessage(@Query("index")int index,@Query("msg_type_id")int msg_type_id);
    @POST("user/loginAndroid")
    Observable<HttpResult<Result>>loginAndroid(@Body User user);
    @POST("API/Label/LabelBinding")
    Observable<TestResult>test(@Body Test test);
    @POST("comment/createComment")
    Observable<HttpResult<Result>>createComment(@Body Comment comment);
    @GET("comment/getCommentCount")
    Observable<HttpResult<Result>>getCommentCount(@Query("message_id")String message_id);
    @GET("comment/getAllComment")
    Observable<HttpResult<List<Floor>>>getAllComment(@Query("message_id")String message_id,@Query("user_id")String user_id);
    @GET("comment/getHotComment")
    Observable<HttpResult<List<Floor>>>getHotComment(@Query("message_id")String message_id,@Query("user_id")String user_id);
    @POST("comment/createPraise")
    Observable<HttpResult<Result>>createPraise(@Body CommentPraise commentPraise);
    @GET("message/getCollectByUserId")
    Observable<HttpResult<Collect>>getCollectByUserId(@Query("user_id")String user_id, @Query("message_id")String message_id);
    @GET("message/setCollectByUserId")
    Observable<HttpResult<Result>>setCollectByUserId(@Query("user_id")String user_id,@Query("message_id")String message_id,@Query("collect")int collect);
    @GET("app/getAppVersion")
    Observable<HttpResult<AppVersion>>getAppVersion();

}
