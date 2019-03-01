package com.sy.spider.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

  @Autowired
  private Codes codes;


  public void setCodes(Codes codes) {
    this.codes = codes;
  }

  public Message getInstance(Integer code) {

    if (code == null) {
      throw new RuntimeException("msg code is null");
    }

    return getInstance(code, codes.getMsg(code));

  }

  public Message getInstance(Integer code, String msg) {

    Message message = createBaseMessage();
    message.setCode(code);
    message.setMsg(msg);
    return message;

  }

  public <T> Message<T> getInstance(Integer code, String msg, T t) {
    Message message = createBaseMessage();
    message.setCode(code);
    message.setMsg(msg);
    message.setData(t);
    return message;
  }

  public <T> Message<T> getInstance(Integer code, T t) {

    if (code == null) {
      throw new RuntimeException("msg code is null");
    }
    return getInstance(code, codes.getMsg(code), t);

  }

  /**
   * 创建包含基本属性的Message.
   */
  private Message createBaseMessage() {

    Message message = new Message();
    //这里可以返回统一值
    return message;

  }

}
