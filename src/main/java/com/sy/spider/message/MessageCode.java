package com.sy.spider.message;


public class MessageCode extends AbstractCodes{

  @MsgCode("请求超时")
  public static final Integer REQUEST_TIME_OUT = -1;

  @MsgCode("success")
  public static final Integer SUCCESS = 0;


  @MsgCode("非法用户")
  public static final Integer API_USER_INVALID = 101008;

  @MsgCode("非法ip")
  public static final Integer USER_IP_INVALID = 101009;

  @MsgCode("ip格式不正确")
  public static final Integer API_IP_INVALID = 101010;

  @MsgCode("参数不能为空")
  public static final Integer PARAM_NOT_FOUND = 201000;

  @MsgCode("参数格式异常")
  public static final Integer PARAM_FORMAT_INVALID = 201001;

  @MsgCode("参数值非法")
  public static final Integer PARAM_NOT_AVAILABLE = 201002;

  @MsgCode("系统繁忙")
  public static final Integer SERVER_BUSY = 300001;

  @MsgCode("服务异常")
  public static final Integer SERVER_ERROR = 300002;


}
