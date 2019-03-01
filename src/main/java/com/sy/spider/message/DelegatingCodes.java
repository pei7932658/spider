package com.sy.spider.message;

import java.util.ArrayList;
import java.util.List;


public class DelegatingCodes implements Codes{

  private List<Codes> codesList = new ArrayList<>();

  public DelegatingCodes addCodes(Codes codes) {
    if (codes instanceof DelegatingCodes) {
      throw new RuntimeException();
    }
    codesList.add(codes);
    return this;
  }

  @Override
  public String getMsg(int code) {
    String msg = null;
    for (Codes codes : codesList) {
      msg = codes.getMsg(code);
      if (msg != null) {
        break;
      }
    }
    return msg == null ? "" : msg;
  }
}
