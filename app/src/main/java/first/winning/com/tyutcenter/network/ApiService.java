package first.winning.com.tyutcenter.network;


import java.util.List;

import javax.inject.Singleton;

import first.winning.com.tyutcenter.model.HttpResult;
import first.winning.com.tyutcenter.model.ReSearchInfo;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 网络接口类
 * Created by yuzhijun on 2016/3/29.
 */
@Singleton
public interface ApiService {
    //获取简评表商机情况
    @GET("ZXM/getOneZXM")
    Observable<HttpResult<List<ReSearchInfo>>> getResearchInfo(@Query("ID") String id);
}
