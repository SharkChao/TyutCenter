package first.winning.com.tyutcenter.utils;


import first.winning.com.tyutcenter.base.MyLeanCloudApp;

/**
 * Created by yuzhijun on 2017/6/27.
 */
public class StringFetcher {
    public static String getString(int id) {
        return MyLeanCloudApp.getInstance().getString(id);
    }
}
