package com.tyutcenter.network.map;


import com.tyutcenter.model.NewsCount;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.reactivex.functions.Function;


/**
 * 处理服务器返回结果，直接对html进行操作
 * Created by yuzhijun on 2017/6/27.
 */
public class HttpResultFuncNewsCountTZGG implements Function<String,NewsCount> {


    @Override
    public NewsCount apply(String s) throws Exception {
        Document parse = Jsoup.parse(s);
        Element divs = parse.getElementsByClass("right_time").first();
        Element style = divs.getElementsByTag("style").first();
        String text = style.html();
        String substring = text.substring(text.indexOf(".") + 1, text.indexOf(","));
        Element first = parse.getElementsByClass(substring).first();
        Element td = first.getElementsByTag("td").first();
        String text1 = td.text();

        String count = text1.substring(text1.indexOf("共") + 1, text1.indexOf("条"));
        String page = text1.substring(text1.lastIndexOf("/") + 1, text1.length());
        NewsCount newsCount = new NewsCount();
        newsCount.setCount(Integer.parseInt(count));
        newsCount.setPage(Integer.parseInt(page));
        return newsCount;
    }
}
