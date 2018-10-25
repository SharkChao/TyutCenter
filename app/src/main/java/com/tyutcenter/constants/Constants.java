package com.tyutcenter.constants;

/**
 * Created by Admin on 2018/4/2.
 */

public class Constants {
    public class HttpCode {
        public static final int HTTP_UNAUTHORIZED = 401;
        public static final int HTTP_SERVER_ERROR = 500;
        public static final int HTTP_NOT_HAVE_NETWORK = 600;
        public static final int HTTP_NETWORK_ERROR = 700;
        public static final int HTTP_UNKNOWN_ERROR = 800;
    }
    public static final String base_url = "http://www2017.tyut.edu.cn";
//    private static final String BASE_SERVER_URL =  "http://106.12.84.31:9090";
    public static final String BASE_SERVER_URL =  "http://172.16.30.125:9090";
    //新闻一页有八条
    public static final int NEWS_ONE_PAGE = 8;
}
