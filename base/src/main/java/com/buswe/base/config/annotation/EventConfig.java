package com.buswe.base.config.annotation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.buswe.base.event.LoggingEventHandler;

@Configuration
public class EventConfig
{
  @Bean(name={"applicationEventMulticaster"})
  ApplicationEventMulticaster applicationEventMulticaster()
  {
    SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
    simpleApplicationEventMulticaster.setTaskExecutor(taskExecutor());
    return simpleApplicationEventMulticaster;
  }
  
  @Bean
  ThreadPoolTaskExecutor taskExecutor()
  {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setThreadNamePrefix("YQ-THREAD");
    return taskExecutor;
  }
  
  @Bean
  LoggingEventHandler loggingEventHandler()
  {
    LoggingEventHandler loggingEventHandler = new LoggingEventHandler();
    loggingEventHandler.setExplicitMatching(false);
    Set<Class<? extends ApplicationEvent>> events = new HashSet();
    events.add(ApplicationEvent.class);
    events.add(ApplicationEvent.class);
    loggingEventHandler.setSupportedEvents(events);
    return loggingEventHandler;
  }
}
