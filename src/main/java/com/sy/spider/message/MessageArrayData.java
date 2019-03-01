package com.sy.spider.message;

import lombok.Data;

import java.util.List;


@Data
public class MessageArrayData<T> {

  private int total;

  private List<T> results;

}
