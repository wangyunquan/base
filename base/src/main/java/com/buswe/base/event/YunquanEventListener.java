package com.buswe.base.event;

import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

public abstract class YunquanEventListener
  implements SmartApplicationListener
{
  protected Log logger = LogFactory.getLog(getClass());
  private Set<Class<? extends ApplicationEvent>> supportedEvents;
  private Set<Class<?>> sourceTypes;
  private boolean explicitMatching = false;
  
  public void setSupportedEvents(Set<Class<? extends ApplicationEvent>> supportedEvents)
  {
    this.supportedEvents = supportedEvents;
  }
  
  public void setSourceTypes(Set<Class<?>> sourceTypes)
  {
    this.sourceTypes = sourceTypes;
  }
  
  public void setExplicitMatching(boolean explicitMatching)
  {
    this.explicitMatching = explicitMatching;
  }
  
  public abstract void onApplicationEvent(ApplicationEvent paramApplicationEvent);
  
  public int getOrder()
  {
    return 0;
  }
  
  public boolean supportsEventType(Class<? extends ApplicationEvent> eventType)
  {
    if (this.supportedEvents == null) {
      return true;
    }
    if (this.explicitMatching) {
      return this.supportedEvents.contains(eventType);
    }
    for (Class<? extends ApplicationEvent> includedType : this.supportedEvents) {
      if (includedType.isAssignableFrom(eventType)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean supportsSourceType(Class<?> sourceType)
  {
    if (this.sourceTypes == null) {
      return true;
    }
    if (this.explicitMatching) {
      return this.sourceTypes.contains(sourceType);
    }
    for (Class<?> includedType : this.sourceTypes) {
      if (includedType.isAssignableFrom(sourceType)) {
        return true;
      }
    }
    return false;
  }
}
