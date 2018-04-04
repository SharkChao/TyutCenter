package first.winning.com.tyutcenter.network;


import first.winning.com.tyutcenter.constants.Constants;
import first.winning.com.tyutcenter.model.HttpResult;
import first.winning.com.tyutcenter.model.ResponseError;
import io.reactivex.functions.Function;


/**
 * 处理服务器返回结果
 * Created by yuzhijun on 2017/6/27.
 */
public class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> tHttpResult) throws Exception {
        if (tHttpResult.getErrorCode() != 1){
            throw new ResponseError(Constants.HttpCode.HTTP_SERVER_ERROR, tHttpResult.getErrorMessage());
        }
        return tHttpResult.getData();
    }
}
