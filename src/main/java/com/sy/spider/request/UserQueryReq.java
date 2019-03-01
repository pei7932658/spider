package com.sy.spider.request;

import lombok.Data;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/19 13:59
 * @Modified By:
 */
@Data
public class UserQueryReq {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String phone;
}
