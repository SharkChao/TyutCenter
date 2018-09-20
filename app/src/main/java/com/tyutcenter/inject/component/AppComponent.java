package com.tyutcenter.inject.component;


import com.tyutcenter.base.MyLeanCloudApp;
import com.tyutcenter.inject.module.ApiServiceModule;
import com.tyutcenter.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuzhijun on 2017/6/15.
 */
@Singleton
@Component(modules = {
        ApiServiceModule.class
})
public interface AppComponent {
    MainPresenter getMainPresenter();
    void inject(MyLeanCloudApp myLeanCloudApp);
}
