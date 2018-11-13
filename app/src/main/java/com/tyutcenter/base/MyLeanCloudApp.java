package com.tyutcenter.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.mob.MobSDK;
import com.tyutcenter.inject.component.AppComponent;
import com.tyutcenter.inject.component.DaggerAppComponent;
import com.tyutcenter.inject.module.ApiServiceModule;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.utils.AppUtils;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import javax.inject.Inject;

/**
 * Created by Admin on 2018/1/31.
 */

public class MyLeanCloudApp extends Application{
    private static MyLeanCloudApp mLeanCloudApp;
    @Inject
     MainPresenter mMainPresenter;
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        mLeanCloudApp = this;
//        AVOSCloud.initialize(this,"At9RCD2NqDP7jR0891BwS6LL-gzGzoHsz","6EEcBDtuKAGNTwKYGJ0gh1QS");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
//        AVOSCloud.setDebugLogEnabled(true);
        if (AppUtils.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        AppComponent build = DaggerAppComponent.builder().apiServiceModule(new ApiServiceModule())
                .build();
        Stetho.initializeWithDefaults(this);
        build.inject(this);
        Fresco.initialize(this);
        EmojiManager.install(new IosEmojiProvider());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static MyLeanCloudApp getInstance(){
        return mLeanCloudApp;
    }
    public MainPresenter getMainPresenter(){
        return mMainPresenter;
    }
}
