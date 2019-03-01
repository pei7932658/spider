package com.sy.spider.message;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: .
 *
 * @author luhaiyou
 * @version 1.0
 * @date Created in 下午5:41 2018/3/21
 */
@Slf4j
public abstract class AbstractCodes implements Codes{


  private Map<Integer, String> codeMap = new HashMap<>();

  public AbstractCodes() {
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.getType().equals(Integer.class)) {
        try {
          Integer value = (Integer) field.get(this);
          MsgCode msg = field.getAnnotation(MsgCode.class);
          if (msg == null) {
            continue;
          }
          codeMap.put(value, msg.value());
        } catch (IllegalAccessException e) {
          log.error("", e);
        }
      }
    }
  }

  @Override
  public String getMsg(int code) {
    return codeMap.get(code);
  }
}
