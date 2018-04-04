package first.winning.com.tyutcenter.activity;

import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import first.winning.com.tyutcenter.R;
import first.winning.com.tyutcenter.annotation.ContentView;
import first.winning.com.tyutcenter.base.BaseActivity;
import first.winning.com.tyutcenter.model.ReSearchInfo;
import first.winning.com.tyutcenter.presenter.MainPresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Admin on 2018/1/31.
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainHomeUi{

    @Override
    public void initTitle() {
        isShowToolBar(false);
    }


    @Override
    public void initView(ViewDataBinding viewDataBinding) {
    }

    @Override
    public void initData() {
        //先创建被观察者，以及被观察者被注册之后发送的事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(0);
            }
        }).delay(2, TimeUnit.SECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                if (integer == 0){
                    ARouter.getInstance()
                            .build("/center/HomeActivity")
                            .navigation();
                    finish();
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }


    @Override
    public void LoginCallback(List<ReSearchInfo> reSearchInfos) {

    }
}
