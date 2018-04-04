package first.winning.com.tyutcenter.inject.component;


import javax.inject.Singleton;

import dagger.Component;
import first.winning.com.tyutcenter.base.MyLeanCloudApp;
import first.winning.com.tyutcenter.inject.module.ApiServiceModule;
import first.winning.com.tyutcenter.presenter.MainPresenter;

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
