package com.tyutcenter.activity;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.mob.ums.gui.UMSGUI;
import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseActivity;
import com.tyutcenter.data.UserData;
import com.tyutcenter.presenter.MainPresenter;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Admin on 2018/1/31.
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{

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
//                    if (false){
                        UMSGUI.showLogin(new OperationCallback<User>(){
                            @Override
                            public void onSuccess(User user) {
                                super.onSuccess(user);
                                initUser(user);
                                //用户数据需要在百度云也保存一份

                                ARouter.getInstance()
                                        .build("/center/HomeActivity")
                                        .navigation();
                                finish();
                            }
                        });
                    }else {
                       UMSSDK.getLoginUser(new OperationCallback<User>(){
                           @Override
                           public void onSuccess(User user) {
                               super.onSuccess(user);
                               initUser(user);
                           }
                       });
                        ARouter.getInstance()
                                .build("/center/HomeActivity")
                                .navigation();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    public void initUser(User user){
        com.tyutcenter.model.User temp = new com.tyutcenter.model.User();
        temp.setId(user.id.get());
        temp.setAddress(user.address.get());
        temp.setAge(user.age.get());
        temp.setAvatar(user.avatar.get()[0]);
        SimpleDateFormat sf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        if (user.birthday.get() != null){
            temp.setBirthday(sf.format(user.birthday.get()));
        }
        temp.setCity(user.city.get()+"");
        temp.setConstellation(user.constellation.get()+"");
        temp.setCountry(user.country.get()+"");
        temp.setEmail(user.email.get()+"");
        temp.setFans(user.fans.get());
        temp.setFollowings(user.followings.get());
        temp.setFriends(user.friends.get());
        temp.setGender(user.gender.get()+"");
        temp.setNickname(user.nickname.get());
        temp.setPhone(user.phone.get());
        temp.setProvince(user.province.get()+"");
        temp.setResume(user.resume.get());
        temp.setrFriends(user.rFriends.get());
        temp.setSignature(user.address.get());
        temp.setZipCode(user.zipCode.get()+"");
        temp.setZodiac(user.zodiac.get()+"");
        temp.setSnsplat("mob注册");
        UserData.setUser(temp);
    }

}
