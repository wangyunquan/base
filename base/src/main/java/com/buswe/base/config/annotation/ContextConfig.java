package com.buswe.base.config.annotation;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.buswe.base.config.ContextHolder;

@Configuration
@PropertySource({"classpath:application.properties"})
@Import({CacheConfig.class, ServiceConfig.class, DaoConfig.class, EventConfig.class,SecurityConfig.class})
public class ContextConfig
{
  @Bean
  public ContextHolder contextHolder()
  {
    return new  ContextHolder();
  }
  
  @Bean(name={"conversionService"})
  public FormattingConversionServiceFactoryBean conversionService()
  {
    return new FormattingConversionServiceFactoryBean();
  }
  
  @Bean(name={"messageSource"})
  public MessageSource messageSource()
  {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setAlwaysUseMessageFormat(true);
    messageSource.setCacheSeconds(60);
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setFallbackToSystemLocale(false);
    messageSource.setBasename("classpath:/messages/message");
    return messageSource;
  }
  
  @Bean
  FreeMarkerConfigurer freeMarkerConfigurer()
  {
    FreeMarkerConfigurer config = new FreeMarkerConfigurer();
    Properties pro = new Properties();
    pro.put("template_update_delay", "5");
    pro.put("default_encoding", "UTF-8");
    pro.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
    pro.put("time_format", "HH:mm:ss");
    pro.put("number_format", "0.####");
    pro.put("boolean_format", "true,false");
    pro.put("whitespace_stripping", "true");
    pro.put("tag_syntax", "auto_detect");
    pro.put("url_escaping_charset", "UTF-8");
    











    config.setFreemarkerSettings(pro);
    config.setTemplateLoaderPath("/WEB-INF/view/freemarker/");
    return config;
  }
}
