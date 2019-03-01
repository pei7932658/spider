package com.sy.spider.bussiness;
import com.sy.spider.bussiness.ZtryCarwlProcess;
import com.sy.spider.constants.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/carwl/app")
public class APPController {

    @Value("${dzh.execute.thread:10}")
    private int executeThread;

    @Lazy
    @Resource(name="${dzh.execute.pipiline}")
    private Pipeline pipeline;

    @Autowired
    private ZtryCarwlProcess ztryCarwlProcess;

    //在逃人员搜索json
    private static String ZTRY_SEARCH_JSON = "{\"appId\":\"xzztry\",\"async\":true,\"resourceId\":\"RESOURCE201509240000682\",\"YWBZ\":\"zt\",\"searchTerm\":{\"SEARCH\":\"*\"}}";

    //在逃人员列表请求
    private static String ZTRY_QUERY_RESULT = "http://localhost:8080/carwl/test/xzztryQueryResult?rnd=";

    @GetMapping("/start")
    public String StartTheCrawl(){

        ztryCarwlProcess.ZTRY_SEARCH_JSON=this.ZTRY_SEARCH_JSON;
        ztryCarwlProcess.ZTRY_QUERY_RESULT = this.ZTRY_QUERY_RESULT;

        //初始发送一遍翻页请求，只为查询数据总量
        Map<String, Object> params = new HashMap<>();
        params.put("json", ZTRY_SEARCH_JSON);
        params.put("page", 1);
        params.put("rows", 1);
        Request request = new Request();
        request.setMethod(HttpConstant.Method.POST);
        request.setUrl(ZTRY_QUERY_RESULT + new Date().getTime());
        request.setRequestBody(HttpRequestBody.form(params, Constants.ENCODING));
        //起始URL
        Spider.create(ztryCarwlProcess)
                .addRequest(request)
                .addPipeline(pipeline)
                .thread(executeThread)
                .run();
        return "success";
    }
}
