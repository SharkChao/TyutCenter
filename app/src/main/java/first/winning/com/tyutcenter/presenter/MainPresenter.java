package first.winning.com.tyutcenter.presenter;

import java.util.ArrayList;
import java.util.List;

import first.winning.com.tyutcenter.base.BasePresenter;
import first.winning.com.tyutcenter.model.HttpResult;
import first.winning.com.tyutcenter.model.ReSearchInfo;
import first.winning.com.tyutcenter.network.ApiService;
import first.winning.com.tyutcenter.network.HttpResultFunc;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        void login();
    }

    @Override
    protected MainUiCallback createUiCallbacks(final MainUi ui) {
        return new MainUiCallback() {
            @Override
            public void login() {
                mApiService.getResearchInfo("5").subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(new HttpResultFunc<List<ReSearchInfo>>())
                                .compose(MainPresenter.this.<List<ReSearchInfo>>applySchedulers())
                                .subscribe(new Consumer<List<ReSearchInfo>>() {
                                    @Override
                                    public void accept(List<ReSearchInfo> reSearchInfos) throws Exception {
                                        ((MainHomeUi)ui).LoginCallback(reSearchInfos);
                                    }
                                });

            }
        };
    }

    //给具体ui调用
    public interface MainUi extends BasePresenter.BaseUi<MainUiCallback>{

    }
    public interface MainHomeUi extends MainUi{
        void LoginCallback(List<ReSearchInfo>reSearchInfos);
    }

}
