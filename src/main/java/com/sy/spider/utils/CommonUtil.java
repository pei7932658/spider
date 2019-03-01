package com.sy.spider.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;


public class CommonUtil {

    /**
     * 构建文件保存路径 取身份证6-10位（年份）
     * @param idCard
     * @return
     */
    public static String getBirthDayFromIdCard(String idCard){

        StringBuilder filePath = new StringBuilder();
        if(null==idCard|| StringUtils.isEmpty(idCard)){
            long randomNum6 = Math.round(Math.random() * 100);
            idCard ="42112719"+randomNum6+"02232215";
        }
        filePath.append(idCard,6, 10).append(File.separator);
        return filePath.toString();
    }
}
