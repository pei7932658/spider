package com.sy.spider;

import com.sy.spider.demo.Demo;
import com.sy.spider.demo.DemoAnnotatis;
import com.sy.spider.demo.GithubRepoPageProcessor;
import org.junit.Test;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;


/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/16 12:24
 * @Modified By:
 */

public class DemoTest {
    @Test
    public void demo1(){
        Spider.create(new Demo()).addUrl("http://www.oschina.net/project/tags")
                .addPipeline(new JsonFilePipeline("d:\\webmagic")).run();
    }

    @Test
    public void demo2(){
        OOSpider.create(Site.me(),new ConsolePageModelPipeline(),DemoAnnotatis.class).addUrl("http://www.oschina.net/project/tags").run();
    }

    @Test
    public void githubRepoPageProcessor(){
        Spider.create(new GithubRepoPageProcessor()).addUrl(new String[]{"http://github.com/code4craft"}).thread(1).run();
    }


}
