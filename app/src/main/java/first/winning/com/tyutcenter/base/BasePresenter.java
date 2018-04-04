package first.winning.com.tyutcenter.base;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import first.winning.com.tyutcenter.model.ResponseError;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Admin on 2018/4/2.
 * 需要ui和uc两个参数
 * 两个uc作用不同
 */

public abstract class BasePresenter<UI extends BasePresenter.BaseUi<UC>,UC>{
    private final Set<UI> mUis;
    private final Set<UI> mUnmodifiableUis;
    private boolean mInited;//controller是否被初始化

    public BasePresenter() {
        //初始化页面集合
        mUis = new CopyOnWriteArraySet<>();
        mUnmodifiableUis = Collections.unmodifiableSet(mUis);
    }

    public interface BaseUi<UC> {
        void setCallbacks(UC callbacks);
        UC getCallbacks();
        void onResponseError(ResponseError error);
    }
    //初始化
    public final void init() {
        onInited();
        mInited = true;
    }

    public final void suspend() {
        onSuspended();
        mInited = false;
    }

    protected abstract UC createUiCallbacks(UI ui);
    protected synchronized void populateUi(UI ui) {}

    protected void onInited() {}

    protected void onSuspended() {}

    public final boolean isInited() {
        return mInited;
    }

    public synchronized final void  attachUi(UI ui) {
        mUis.add(ui);
        ui.setCallbacks(createUiCallbacks(ui));
    }

    public synchronized final void startUi(UI ui) {
        populateUi(ui);
    }

    public synchronized final void detachUi(UI ui) {
        ui.setCallbacks(null);
        mUis.remove(ui);
    }

    protected synchronized final void populateUis() {
        for (UI ui : mUis) {
            populateUi(ui);
        }
    }

    protected final Set<UI> getUis() {
        return mUnmodifiableUis;
    }

    protected int getId(UI ui) {
        return ui.hashCode();
    }

    protected synchronized UI findUi(final int id) {
        for (UI ui : mUis) {
            if (getId(ui) == id) {
                return ui;
            }
        }
        return null;
    }

    final ObservableTransformer schedulersTransformer = new ObservableTransformer(){
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    protected  <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }
}
