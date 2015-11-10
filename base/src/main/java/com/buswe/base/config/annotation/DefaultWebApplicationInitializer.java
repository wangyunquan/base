package com.buswe.base.config.annotation;
/*
 *
//写字楼里写字间，写字间里程序员；    
//程序人员写程序，又拿程序换酒钱。    
//酒醒只在网上坐，酒醉还来网下眠；    
//酒醉酒醒日复日，网上网下年复年。    
//但愿老死电脑间，不愿鞠躬老板前；    
//奔驰宝马贵者趣，公交地铁程序员。    
//别人笑我忒疯癫，我笑自己命太贱；    
//不见满街漂亮妹，哪个归得程序员？
 * 
 * *
 * 
 */
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
   // servletContext.addListener(IntrospectorCleanupListener.class);
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
