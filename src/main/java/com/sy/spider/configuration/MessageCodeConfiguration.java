package com.sy.spider.configuration;

import com.sy.spider.message.Codes;
import com.sy.spider.message.DelegatingCodes;
import com.sy.spider.message.MessageCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageCodeConfiguration {

  @Bean
  public Codes getCodes(){
    //多个code类先增加的优先级高.
    return new DelegatingCodes().addCodes(new MessageCode());

  }
}
