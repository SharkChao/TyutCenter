package first.winning.com.tyutcenter.network;

import android.util.Log;


import org.reactivestreams.Subscriber;

import first.winning.com.tyutcenter.model.ResponseError;

/**
 * 定制返回方法
 * Created by yuzhijun on 2017/6/27.
 */
public abstract class RequestCallBack<T> implements Subscriber<T> {
    private static final String TAG = "RequestCallback";



    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof ResponseError) {
            onFailure((ResponseError) throwable);
        } else {
            Log.e(TAG, "throwable isn't instance of ResponseError");
        }
    }

    @Override
    public void onNext(T t) {
        onResponse(t);
    }



    public abstract void onResponse(T response);

    public abstract void onFailure(ResponseError error);
}
