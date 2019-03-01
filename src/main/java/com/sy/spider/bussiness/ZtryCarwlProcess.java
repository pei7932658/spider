package com.sy.spider.bussiness;


import com.sy.spider.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.*;

/**
 * @Author peiliang
 * @Description : 全国在逃
 * @Date 2018/11/20 18:17
 * @Param
 * @Return
 */
@Component
@Slf4j
public class ZtryCarwlProcess implements PageProcessor {


    //断点数
    @Value("${dzh.process.breakPoint}")
    public int breakPoint;
    //页面大小
    @Value("${dzh.process.pageSize}")
    public int pageSize;
    //页码
    private int pageNum = 1;
    //总数
    private int pageCount = 0;

    //在逃人员列表翻页ajax
    private static String ZTRY_LIST_REGEX = "http://ztry\\.xz\\.ga/zyk_zt/xzztry/xzztryQueryResult\\.do.";

    //在逃人员细节页面
    public static String ZTRY_DETAIL_REGEX = "http://10\\.12\\.221\\.133/zxyw_zt/ztry/ztryxxzt\\.do.";

    //在逃人员搜索json
    public static String ZTRY_SEARCH_JSON;

    //在逃人员列表请求
    public static String ZTRY_QUERY_RESULT;

    //在逃人员详情请求
    private static String ZTRY_XXZT = "http://localhost:8080/carwl/test/ztryxxzt?ztrybh=";
    //下载地址
    private static String ZTRY_PHOTOURL_ROOT = "http://10.12.221.133";

    //配置
    @Override
    public Site getSite() {
        return Site.me()
                .setRetryTimes(3) //设置重试次数
                .setSleepTime(1000)
                .setTimeOut(30000) //超时时间
                .addHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    @Override
    public void process(Page page) {
        try {
            if (page.getUrl().regex(this.ZTRY_LIST_REGEX).match()) {
                ztryListHandle(page);
                //列表页面跳过pipeline
                page.setSkip(true);
            } else if (page.getUrl().regex(this.ZTRY_DETAIL_REGEX).match()) {
                ztryDetailHandle(page);
            }
        } catch (Exception e) {
            log.error("匹配页面URL异常", e);
        }
    }

    /**
     * 在逃人员列表翻页请求处理
     *
     * @param page
     */
    private void ztryListHandle(Page page) {

        if (pageCount == 0) {
            //解析在逃数据总数
            String num = new JsonPathSelector("$.total").select(page.getRawText());
            //生成分页参数
            pageCount = Integer.valueOf(num);
            if (pageCount % pageSize == 0) {
                pageNum = pageCount / pageSize;
            } else {
                pageNum = (pageCount / pageSize) + 1;
            }
            log.info("数据总数：<" + pageCount + "> 总页数：<" + pageNum + "> 页面大小：<" + pageSize + ">");
            int i = 1;
            //从断点页开始加载请求
            if (breakPoint > 0) {
                i = breakPoint / pageSize;
                log.info("断点数：<" + breakPoint + "> 从第<" + i + ">页开始继续爬取");
            }
            //组装翻页搜索请求
            for (; i <= pageNum; i++) {
                //模拟post ajax请求
                Map<String, Object> params = new HashMap<>();
                params.put("json", this.ZTRY_SEARCH_JSON);
                params.put("page", i);
                params.put("rows", pageSize);
                Request request = new Request();
                request.setMethod(HttpConstant.Method.POST);
                request.setUrl(this.ZTRY_QUERY_RESULT + new Date().getTime());
                request.setRequestBody(HttpRequestBody.form(params, Constants.ENCODING));
                //添加至目标队列
                page.addTargetRequest(request);
            }
        } else {
            //获取在逃人员编号，根据编号组装在逃详情页
            List<String> ztrybhList = new JsonPathSelector("$.rows[*].ZTRYBH")
                    .selectList(page.getRawText());
            List<String> requests = new ArrayList<String>();
            for (String ztryhb : ztrybhList) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.ZTRY_XXZT)
                        .append(ztryhb);
                requests.add(sb.toString());
            }
            page.addTargetRequests(requests);
        }
    }

    /**
     * 在逃详情页面处理
     *
     * @param page
     */
    private void ztryDetailHandle(Page page) {

        //得到照片url
        String photoURL = page.getHtml().xpath("//img[@id='ryxp']").css("img", "src").toString();

        String name = page.getHtml().xpath("//*[@id='mainTable']/tbody/tr[2]/td[2]/text()").toString().trim();
        String sfzh = page.getHtml().xpath("//*[@id='mainTable']/tbody/tr[4]/td[2]/text()").toString().trim();
        String ztbh = page.getHtml().xpath("//*[@id='mainTable']/tbody/tr[1]/td[2]/text()").toString().trim();

        page.putField("photoURL", this.ZTRY_PHOTOURL_ROOT + photoURL);
        name = name.replace("&nbsp", "");
        sfzh = sfzh.replace("&nbsp", "");
        ztbh = ztbh.replace("&nbsp", "");

        name = name.substring(1, name.length());
        ztbh = ztbh.substring(1, ztbh.length());

        page.putField("name", name);
        page.putField("sfzh", sfzh);
        page.putField("ztbh", ztbh);

    }

}
