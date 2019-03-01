package com.sy.spider.demo;

import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:peiliang
 * @Description: 测试demo
 * @Date:2018/11/16 11:51
 * @Modified By:
 */
@Service("demo")
public class Demo implements PageProcessor{

    private Site site = Site.me().setDomain("my.oschina.net").setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

        List<String> links =  page.getHtml().links().regex("http://www.oschina.net/project/tags").all();
        page.addTargetRequests(links);
//        page.putField("title",page.getHtml().xpath("//div[@class='twelve wide computer sixteen wide tablet column']//div[@class='categories-wrap']//div[@class='category-item']//div[@class='ui list']//div[@class='item']/h3/text()").toString());
//        page.putField("items",page.getHtml().xpath("//div[@class='twelve wide computer sixteen wide tablet column']//div[@class='categories-wrap']//div[@class='category-item']//div[@class='row ui grid four column doubling no-padding sub-categories-wrap']").all());

        List<String> categoriesList = page.getHtml().xpath("//div[@class='twelve wide computer sixteen wide tablet column']//div[@class='categories-wrap']//div[@class='category-item']").all();
        for (String categoryItem :categoriesList){
            Html ui_list = new Html(categoryItem);
            String title = ui_list.xpath("//div[@class='ui list']//div/h3/text()").toString();
            page.putField("title",title);
//            System.out.println("title:"+title);
            List<String> column_sub_category_item = ui_list.xpath("//div[@class='row ui grid four column doubling no-padding sub-categories-wrap']//div").all();
            List<String> hrefs = new ArrayList<String>();
            List<String> names = new ArrayList<String>();
            for(String item:column_sub_category_item){
                Html h = new Html(item);
                String href_value = h.xpath("//a/@href").toString();
                String href_name = h.xpath("//a//text()").toString();
//                System.out.println("    "+href_value);
//                System.out.println("    "+href_name);
                hrefs.add(href_value);
                names.add(href_name);
            }
            page.putField("href_name",names);
            page.putField("href_value",hrefs);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
