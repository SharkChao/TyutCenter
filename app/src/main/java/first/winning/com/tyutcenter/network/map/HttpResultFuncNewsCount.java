package first.winning.com.tyutcenter.network.map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import first.winning.com.tyutcenter.constants.Constants;
import first.winning.com.tyutcenter.model.News;
import first.winning.com.tyutcenter.model.NewsCount;
import io.reactivex.functions.Function;


/**
 * 处理服务器返回结果，直接对html进行操作
 * Created by yuzhijun on 2017/6/27.
 */
public class HttpResultFuncNewsCount implements Function<String,NewsCount> {


    @Override
    public NewsCount apply(String s) throws Exception {
        Document parse = Jsoup.parse(s);
        Element divs = parse.getElementsByClass("right_pic").first();
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
