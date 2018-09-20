package com.tyutcenter.network.map;


import com.tyutcenter.constants.Constants;
import com.tyutcenter.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;


/**
 * 处理服务器返回结果，直接对html进行操作
 * Created by yuzhijun on 2017/6/27.
 */
public class HttpResultFuncNewsTZGG implements Function<String,List<News>> {


    @Override
    public List<News> apply(String s) throws Exception {
        List<News>list = new ArrayList<>();
        Document parse = Jsoup.parse(s);
        Element right_pic = parse.getElementsByClass("right_time").first();
        Elements allNews = right_pic.getElementsByTag("li");
        for (Element element:allNews){
            News news = new News();
            //获取新闻的时间 拼接年月日
            Element date = element.getElementsByClass("date").first();
            String day = date.text();
            Element time = element.getElementsByClass("yymm").first();
            String yearmonth = time.text();
            news.setTime(day +"|"+ yearmonth);

            //获取来源和作者
            Element tit_jj = element.getElementsByClass("tit_jj").first();
            Element tit = tit_jj.getElementsByClass("tit").first();
            //获取新闻的详细地址
            Element detailElement = tit.getElementsByTag("a").first();
            String href = detailElement.attr("href");
            String detailUrl = href.substring(href.indexOf("/"), href.length());

            String title = detailElement.text();
            news.setTitle(title);
            news.setDetailUrl(Constants.base_url + detailUrl);
            for (int i = 0; i < tit_jj.getElementsByTag("span").size();i++){
                Element item = tit_jj.getElementsByTag("span").get(i);
                if (i == 0){
                    String from = item.text();
                    news.setFrom(from);
                }else if (i ==1){

                }else if (i ==2){
                    String author = item.text();
                    news.setAuthor(author);
                }
            }
            list.add(news);
        }

        return list;
    }
}
