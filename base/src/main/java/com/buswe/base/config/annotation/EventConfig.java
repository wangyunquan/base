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
import com.buswe.moudle.cms.statics.ContentVistEvent;
import com.buswe.moudle.cms.statics.VistEventHandler;

@Configuration
public class EventConfig
{
  @Bean(name={"applicationEventMulticaster"})
  ApplicationEventMulticaster applicationEventMulticaster(ThreadPoolTaskExecutor taskExecutor)
  {
    SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
    simpleApplicationEventMulticaster.setTaskExecutor(taskExecutor);
    return simpleApplicationEventMulticaster;
  }
  
  @Bean(name={"taskExecutor"})
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
    Set<Class<? extends ApplicationEvent>> events = new HashSet<Class<? extends ApplicationEvent>>();
    events.add(ApplicationEvent.class);
    loggingEventHandler.setSupportedEvents(events);
    return loggingEventHandler;
  }
  @Bean
  VistEventHandler vistEventHandler()
  {
	  VistEventHandler eventHandler = new VistEventHandler();
	    eventHandler.setExplicitMatching(false);
	    Set<Class<? extends ApplicationEvent>> events = new HashSet<Class<? extends ApplicationEvent>>();
	    events.add(ContentVistEvent.class);
	    eventHandler.setSupportedEvents(events);
	    return eventHandler;
  }
 
}
