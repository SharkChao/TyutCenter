package com.tyutcenter.network;

import com.google.gson.JsonParseException;
import com.tyutcenter.R;
import com.tyutcenter.model.ResponseError;
import com.tyutcenter.utils.GsonHelper;
import com.tyutcenter.utils.StringFetcher;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

import static com.tyutcenter.constants.Constants.HttpCode.HTTP_NETWORK_ERROR;
import static com.tyutcenter.constants.Constants.HttpCode.HTTP_SERVER_ERROR;
import static com.tyutcenter.constants.Constants.HttpCode.HTTP_UNAUTHORIZED;
import static com.tyutcenter.constants.Constants.HttpCode.HTTP_UNKNOWN_ERROR;


public class ResponseErrorProxy implements InvocationHandler {

    public static final String TAG = ResponseErrorProxy.class.getSimpleName();

    private Object mProxyObject;

    public ResponseErrorProxy(Object proxyObject) {
        mProxyObject = proxyObject;
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) {

            return Observable.just(null)
                    .flatMap(new Function<Object, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Object o) throws Exception {
                            try {
                                return (Observable<?>) method.invoke(mProxyObject, args);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    })
                    .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                            return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                                @Override
                                public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                    ResponseError error = null;
                                    if (throwable instanceof ConnectTimeoutException
                                            || throwable instanceof SocketTimeoutException
                                            || throwable instanceof UnknownHostException
                                            || throwable instanceof ConnectException) {
                                        error = new ResponseError(HTTP_NETWORK_ERROR,
                                                StringFetcher.getString(R.string.toast_error_network));
                                    } else if (throwable instanceof HttpException) {
                                        HttpException exception = (HttpException) throwable;
                                        try {
                                            error = GsonHelper.builderGson().fromJson(
                                                    exception.response().errorBody().string(), ResponseError.class);
                                        } catch (Exception e) {
                                            if (e instanceof JsonParseException) {
                                                error = new ResponseError(HTTP_SERVER_ERROR,
                                                        StringFetcher.getString(R.string.toast_error_server));
                                            } else {
                                                error = new ResponseError(HTTP_UNKNOWN_ERROR,
                                                        StringFetcher.getString(R.string.toast_error_unknown));
                                            }
                                        }
                                    } else if (throwable instanceof JsonParseException) {
                                        error = new ResponseError(HTTP_SERVER_ERROR,
                                                StringFetcher.getString(R.string.toast_error_server));
                                    } else {
                                        error = new ResponseError(HTTP_UNKNOWN_ERROR,
                                                StringFetcher.getString(R.string.toast_error_unknown));
                                    }

                                    if (error.getStatus() == HTTP_UNAUTHORIZED) {
                                        return refreshTokenWhenTokenInvalid();
                                    } else {
                                        return Observable.error(error);
                                    }
                                }
                            });

                        }



                    });

    }

    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ResponseErrorProxy.class) {
            //TODO
            return Observable.error(new ResponseError(HTTP_SERVER_ERROR,
                    StringFetcher.getString(R.string.toast_error_server)));
        }
    }
}
