package com.sy.spider.demo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/16 14:23
 * @Modified By:
 */
public class GithubRepoPageProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("https.protocols","TLSv1.2");

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("http://github\\.com/(\\w+)/.*").toString());
        page.putField("items", page.getHtml().xpath("//ol[@class='pinned-repos-list mb-4']/li").all());
        if (page.getResultItems().get("items") == null) {
            //skip this page
            page.setSkip(true);
        }
        List<String> items = page.getResultItems().get("items");
        for(String item :items){
            Html h = new Html(item);
            page.putField("href",h.xpath("//span//span/a/@href"));
            page.putField("name",h.xpath("//span//span//a//span/text()"));
            page.putField("remark",h.xpath("//span//span//p/text()"));
        }

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

}
