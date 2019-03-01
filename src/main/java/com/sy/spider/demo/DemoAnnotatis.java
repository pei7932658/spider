package com.sy.spider.demo;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/16 13:41
 * @Modified By:
 */
@TargetUrl("http://www.oschina.net/project/tags")
public class DemoAnnotatis {

    @ExtractBy("//div[@class='twelve wide computer sixteen wide tablet column']//div[@class='categories-wrap']//div[@class='category-item']")
    private List<String> items;

    public void analysisItems(){
        for (String categoryItem :items){
            Html ui_list = new Html(categoryItem);
            String title = ui_list.xpath("//div[@class='ui list']//div/h3/text()").toString();
            System.out.println("title:"+title);
            List<String> column_sub_category_item = ui_list.xpath("//div[@class='row ui grid four column doubling no-padding sub-categories-wrap']//div").all();
            for(String item:column_sub_category_item){
                Html h = new Html(item);
                String href_value = h.xpath("//a/@href").toString();
                String href_name = h.xpath("//a//text()").toString();
                System.out.println("    "+href_value);
                System.out.println("    "+href_name);
            }
        }
    }
}
