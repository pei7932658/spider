package com.sy.spider.exception;

import java.io.Serializable;

 /**   
  * @Author peiliang  
  * @Description :
  * @Date 2018/11/19 16:26
  * @Param   
  * @Return   
  */ 
public class BizException extends RuntimeException implements Serializable {

  private static final long serialVersionUID = -1L;

  private int code;
  private String msg;

  public BizException() {
    this(ExceptionCode.UNKNOWN_ERROR);
  }

  public BizException(ExceptionCode exceptionCode) {
    this.code = exceptionCode.getCode();
    this.msg = exceptionCode.getMsg();
  }

  public BizException(ExceptionCode code, Exception ex) {
    this.code = code.getCode();
    this.msg = code.getMsg() + " " + ex.toString();
  }

  public BizException(int code,String ... msg){
    this.code = code;
    this.msg = String.join(" " , msg);
  }

  public BizException(ExceptionCode code, String appendMsg) {
    this.code = code.getCode();
    this.msg = code.getMsg() + ": " + appendMsg;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
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

  @Override
  public String toString() {
    return "BaseException{" +
        "code=" + code +
        ", msg='" + msg + '\'' +
        '}';
  }
}
