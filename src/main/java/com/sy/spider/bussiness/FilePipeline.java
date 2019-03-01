package com.sy.spider.bussiness;

import com.sy.spider.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:peiliang
 * @Description: 文件处理通道
 * @Date:2018/11/19 18:43
 * @Modified By:
 */
@Component(value = "filePipeline")
@Slf4j
public class FilePipeline implements Pipeline {

    private AtomicLong total = new AtomicLong();

    /**
     * 下载文件保存地址
     */
    @Value("${dzh.pipeline.file.savePath}")
    private String savePath;

    @Override
    public void process(ResultItems resultItems, Task task) {

        String urlStr = resultItems.get("photoURL");
        String idCard = resultItems.get("sfzh").toString().split("：")[1].trim();

        StringBuffer fileName = new StringBuffer();
        fileName.append(resultItems.get("name").toString().trim())
                .append("_")
                .append(idCard)
                .append("_")
                .append(resultItems.get("ztbh").toString().trim())
                .append(".jpg");

        InputStream in = null;
        OutputStream out = null;
        try {
            URL url = new URL(urlStr);
            //打开url连接
            URLConnection connection = url.openConnection();
            //请求超时时间
            connection.setConnectTimeout(5000);
            //输入流
            in = connection.getInputStream();
            //缓冲数据
            byte[] bytes = new byte[1024];
            //数据长度
            int len;

            //文件
            File file = new File(savePath);
            if (!file.exists())
                file.mkdirs();

            File file2 = new File(savePath + File.separator + CommonUtil.getBirthDayFromIdCard(idCard));

            if (!file2.exists())
                file2.mkdirs();

            out = new FileOutputStream(file.getPath() + File.separator + CommonUtil.getBirthDayFromIdCard(idCard) + fileName.toString());
            //先读到bytes中
            while ((len = in.read(bytes)) != -1) {
                //再从bytes中写入文件
                out.write(bytes, 0, len);
            }

            out.flush();

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("inputStream close error",e);
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("outputStream close error",e);
                }
            }
        }

        total.incrementAndGet();

        log.info("文件已下载数量:" + total + "张 ; 本次下载是：" + fileName);
    }
}
