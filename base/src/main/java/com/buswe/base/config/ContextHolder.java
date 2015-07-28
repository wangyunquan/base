package com.buswe.base.config;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ContextHolder
  implements ApplicationContextAware, DisposableBean
{
  private static ApplicationContext applicationContext = null;
  private static Logger logger = LoggerFactory.getLogger(ContextHolder.class);
  
  public static ApplicationContext getApplicationContext()
  {
    checkApplication();
    return applicationContext;
  }
  
  public static <T> T getBean(String name)
  {
    checkApplication();
    return (T) applicationContext.getBean(name);
  }
  public static <T> T getBean(Class<T> requiredType)
  {
    checkApplication();
    return applicationContext.getBean(requiredType);
  }
  public static void clearHolder()
  {
    logger.debug("清除SpringContextHolder中的ApplicationContext:" + 
      applicationContext);
    applicationContext = null;
  }
  
  public void setApplicationContext(ApplicationContext applicationContext)
  {
    logger.debug("注入ApplicationContext到SpringContextHolder:" + 
      applicationContext);
    if (applicationContext != null) {
      logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + 
        applicationContext);
    }
    ContextHolder.applicationContext = applicationContext;
  }
  
  public static String getMessage(String code)
  {
    checkApplication();
    return applicationContext.getMessage(code, null, 
      LocaleContextHolder.getLocale());
  }
  
  public static String getMessage(String code, Object[] args)
  {
    checkApplication();
    return applicationContext.getMessage(code, null, 
      LocaleContextHolder.getLocale());
  }
  
  public void destroy()
    throws Exception
  {
	   ContextHolder.applicationContext =null;
  }
  
  private static void checkApplication()
  {
    if (applicationContext == null) {
      applicationContext = new GenericXmlApplicationContext(new String[] { "classpath:/context/*.xml" });
    }
  }
}
