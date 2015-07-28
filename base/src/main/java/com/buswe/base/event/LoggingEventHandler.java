package com.buswe.base.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;

public class LoggingEventHandler
  extends YunquanEventListener
{
  private Log eventLogger = this.logger;
  private String logCategory;
  
  public String getLogCategory()
  {
    return this.logCategory;
  }
  
  public void setLogCategory(String logCategory)
  {
    this.logCategory = logCategory;
  }
  
  public void onApplicationEvent(ApplicationEvent event)
  {
    if (this.logCategory != null) {
      this.eventLogger = LogFactory.getLog(this.logCategory);
    }
    if (this.eventLogger.isInfoEnabled()) {
      this.eventLogger.info(event.toString());
    }
  }
}
