package first.winning.com.tyutcenter.network.map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import first.winning.com.tyutcenter.constants.Constants;
import first.winning.com.tyutcenter.inject.module.ApiServiceModule;
import first.winning.com.tyutcenter.model.HttpResult;
import first.winning.com.tyutcenter.model.News;
import first.winning.com.tyutcenter.model.ResponseError;
import io.reactivex.functions.Function;


/**
 * 处理服务器返回结果，直接对html进行操作
 * Created by yuzhijun on 2017/6/27.
 */
public class HttpResultFuncNews implements Function<String,List<News>> {


    @Override
    public List<News> apply(String s) throws Exception {
        List<News>list = new ArrayList<>();
        Document parse = Jsoup.parse(s);
        Element right_pic = parse.getElementsByClass("right_pic").first();
        Elements allNews = right_pic.getElementsByTag("li");
        for (Element element:allNews){
            News news = new News();
            //获取新闻的picUrl
            String picUrl = element.getElementsByTag("img").first().attr("src");
            news.setPicUrl(Constants.base_url + picUrl);
            //获取新闻的详细地址
            Element detailElement = element.getElementsByTag("a").first();
            String href = detailElement.attr("href");
            String detailUrl = href.substring(href.indexOf("/"), href.length());
            news.setDetailUrl(Constants.base_url + detailUrl);
            //获取新闻标题
            String title = detailElement.text();
            news.setTitle(title);

            //获取新闻来源
            Elements spans = element.getElementsByTag("span");
            for (int i = 0;i < spans.size();i++){
                Element item = spans.get(i);
                String text = item.text();
                String from = text.substring(text.indexOf("：") + 1, text.length());
                if (i == 0){
                    news.setFrom(from);
                }else if (i == 2){
                    news.setAuthor(from);
                }else if (i == 3){
                    news.setTime(from);
                }

            }
            list.add(news);
        }

        return list;
    }
}
