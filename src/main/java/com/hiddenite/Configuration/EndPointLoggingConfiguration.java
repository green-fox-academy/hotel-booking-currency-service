package com.hiddenite.Configuration;

import com.hiddenite.service.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class EndPointLoggingConfiguration extends WebMvcConfigurerAdapter {

  LoggerInterceptor loggerInterceptor;

  @Autowired
  public EndPointLoggingConfiguration() {
    loggerInterceptor = new LoggerInterceptor();
  }

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggerInterceptor)
            .addPathPatterns("/*");
  }
}
