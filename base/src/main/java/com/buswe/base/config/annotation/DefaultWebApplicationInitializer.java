package com.buswe.base.config.annotation;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.IntrospectorCleanupListener;
import org.springframework.web.util.WebAppRootListener;

import com.alibaba.druid.support.http.StatViewServlet;
import com.buswe.core.web.servlet.CaptchaServlet;

public class DefaultWebApplicationInitializer
  extends AbstractAnnotationConfigDispatcherServletInitializer
{
  public void onStartup(ServletContext servletContext)
    throws ServletException
  {
    servletContext.addListener(IntrospectorCleanupListener.class);
  //  servletContext.addListener(HttpSessionEventPublisher.class);
    servletContext.addListener(WebAppRootListener.class);
    StatViewServlet DruidStatView = new StatViewServlet();
    ServletRegistration.Dynamic druidRegistration = 
      servletContext.addServlet("DruidStatView", DruidStatView);
    druidRegistration.addMapping(new String[] { "/druid/*" });
    CaptchaServlet captchaServlet=new CaptchaServlet();
    ServletRegistration.Dynamic captchaRegistration = 
    	      servletContext.addServlet("captchaServlet", captchaServlet);
    captchaRegistration.addMapping(new String[] { "/captcha/*" });
    super.onStartup(servletContext);
  }
  
  
  protected Class<?>[] getRootConfigClasses()
  {
    return new Class[] { ContextConfig.class };
  }
  
  protected Class<?>[] getServletConfigClasses()
  {
    return new Class[] { MvcConfig.class };
  }
  
  protected String[] getServletMappings()
  {
    return new String[] { "/" };
  }
  
  protected Filter[] getServletFilters()
  {
    OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    ConfigurableSiteMeshFilter siteMeshFilter = new ConfigurableSiteMeshFilter();
    DelegatingFilterProxy delegatingFilterProxy=new DelegatingFilterProxy();
    delegatingFilterProxy.setTargetFilterLifecycle(true);
    delegatingFilterProxy.setTargetBeanName("shiroFilter");
    return new Filter[] { encodingFilter, delegatingFilterProxy,openEntityManagerInViewFilter, siteMeshFilter };
  }
  
  protected void customizeRegistration(ServletRegistration.Dynamic registration)
  {
    registration.setMultipartConfig(new MultipartConfigElement(null, 5242880L, 26214400L, 1048576));
  }
}
