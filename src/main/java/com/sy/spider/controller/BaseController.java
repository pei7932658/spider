package com.sy.spider.controller;

import com.sy.spider.message.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {

  @Autowired
  protected MessageFactory messageFactory;

  @Autowired
  protected HttpServletRequest request;

  @Autowired
  protected HttpServletResponse response;


}
