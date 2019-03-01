package com.sy.spider.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author:peiliang
 * @Description:
 * @Date:2018/11/19 13:59
 * @Modified By:
 */
@Data
@ApiModel(value = "添加用户请求Model",description = "添加用户请求Model描述")
public class UserAddReq {

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username", value = "用户名", dataType = "string", required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", dataType = "string", required = true)
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone", value = "手机号", dataType = "string", required = true)
    private String phone;


}
