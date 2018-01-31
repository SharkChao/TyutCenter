package first.winning.com.tyutcenter.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Admin on 2018/1/31.
 */

public class MyLeanCloudApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"At9RCD2NqDP7jR0891BwS6LL-gzGzoHsz","6EEcBDtuKAGNTwKYGJ0gh1QS");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }
}
