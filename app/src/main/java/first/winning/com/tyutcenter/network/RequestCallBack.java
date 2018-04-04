package first.winning.com.tyutcenter.network;

import android.databinding.Observable;
import android.util.Log;


import org.reactivestreams.Subscriber;

import first.winning.com.tyutcenter.model.ResponseError;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 定制返回方法
 * Created by yuzhijun on 2017/6/27.
 */
public abstract class RequestCallBack<T> implements Observer<T> {
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
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onResponse(t);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onResponse(T response);

    public abstract void onFailure(ResponseError error);
}
