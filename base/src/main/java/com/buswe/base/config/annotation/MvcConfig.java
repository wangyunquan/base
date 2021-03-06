package com.buswe.base.config.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages={"com.buswe.**.controller**"}, includeFilters={@org.springframework.context.annotation.ComponentScan.Filter({org.springframework.stereotype.Controller.class})})
public class MvcConfig
  extends WebMvcConfigurerAdapter
{
  @Bean
  FreeMarkerViewResolver freeMarkerViewResolver()
  {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setOrder(1);
    resolver.setViewNames(new String[] { "*blog*", "*html*" });
    resolver.setSuffix(".html");
    resolver.setContentType("text/html;charset=UTF-8");
    resolver.setViewClass(FreeMarkerView.class);
    return resolver;
  }
  
  @Bean
  public InternalResourceViewResolver jstlViewResolver()
  {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/view/jsp/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    return resolver;
  }
  
//  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
//  {
 //已开启  @EnableSpringDataWebSupport
//    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
//  }
  
  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
	 registry.addResourceHandler(new String[] { "/assets/**" }).addResourceLocations(new String[] { "/assets/" });
    registry.addResourceHandler(new String[] { "/static/**" }).addResourceLocations(new String[] { "/static/" });
    registry.addResourceHandler(new String[] { "/upload/**" }).addResourceLocations(new String[] { "/upload/" });

    
  }
  
//  @Bean 测试环境不需要
//  public HandlerExceptionResolver simpleMappingExceptionResolver()
//  {
//    SimpleMappingExceptionResolver b = new SimpleMappingExceptionResolver();
//    Properties mappings = new Properties();
//    mappings.put("org.springframework.dao.DataAccessException", "error");
//    b.setExceptionMappings(mappings);
//    return b;
//  }
  
  @Bean(name={"multipartResolver"})
  public MultipartResolver multipartResolver()
  {
    StandardServletMultipartResolver mr = new StandardServletMultipartResolver();
    return mr;
  }
}
