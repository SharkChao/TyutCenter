package com.tyutcenter.activity;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.mob.ums.gui.UMSGUI;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.model.ReSearchInfo;
import com.tyutcenter.presenter.MainPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        //先创建被观察者，以及被观察者被注册之后发送的事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(0);
            }
        }).delay(5, TimeUnit.SECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                if (integer == 0){
                    if (!UMSSDK.amILogin()){
                        UMSGUI.showLogin(new OperationCallback<User>(){
                            @Override
                            public void onSuccess(User user) {
                                super.onSuccess(user);
                                Toast.makeText(SplashActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                                ARouter.getInstance()
                                        .build("/center/HomeActivity")
                                        .navigation();
                            }
                        });
                    }else {
                        ARouter.getInstance()
                                .build("/center/HomeActivity")
                                .navigation();
                    }
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
