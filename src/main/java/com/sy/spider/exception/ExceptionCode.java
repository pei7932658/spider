package com.sy.spider.exception;


 /**   
  * @Author peiliang  
  * @Description :
  * @Date 2018/11/19 15:58  
  * @Param   
  * @Return   
  */ 
public enum ExceptionCode {
  SUCCESS(0, "success"),
  UNKNOWN_ERROR(100000, "未知错误"),
  PARAM_ERROR(201001,"参数格式异常"),
  SERVICE_ERROR(300002,"服务异常");

  private int code;
  private String msg;

  ExceptionCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
