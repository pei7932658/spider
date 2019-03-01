package com.sy.spider.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ApiModel("spider响应结果")
public class Message<T> {


    @ApiModelProperty(name = "code", notes = "请求响应码", dataType = "int")
    private int code;

    @ApiModelProperty(name = "msg", notes = "请求结果描述", dataType = "string")
    private String msg;

    @ApiModelProperty(name = "records", notes = "请求结果的返回数据", dataType = "json object")
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private T data;

}

