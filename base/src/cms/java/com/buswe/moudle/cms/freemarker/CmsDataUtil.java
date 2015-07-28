package com.buswe.moudle.cms.freemarker;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.buswe.base.config.AppConfig;
import com.buswe.base.config.ContextHolder;
import com.buswe.base.web.WebHelper;

import freemarker.template.Template;

public class CmsDataUtil
{
  public static void cmsHeadData(Map<String, Object> data)
  {
    data.put("blogTitle", "blogTitle");
    data.put("blogSubtitle", "blogSubtitle");
    data.put("year", Integer.valueOf(Calendar.getInstance().get(1)));
    data.put("staticServePath", AppConfig.getWebContainerPath());
    
    data.put("servePath", "http://localhost:8080");
    data.put("skinDirName", "andrea");
    data.put("miniPostfix", ".min");
    data.put("staticResourceVersion", "201506281200");
    data.put("metaKeywords", "metaKeywords");
    data.put("metaDescription", "metaDescription");
    data.put("topBarReplacement", getTopBarReplacement());
  }
  
  public static String getTopBarReplacement()
  {
    StringWriter writer = new StringWriter();
    FreeMarkerConfigurer config = (FreeMarkerConfigurer)ContextHolder.getBean(FreeMarkerConfigurer.class);
    try
    {
      Template tempdate = config.getConfiguration().getTemplate("top-bar.ftl");
      
      Map<String, Object> data = new HashMap();
      
      data.put("onlineVisitor1Label", ContextHolder.getMessage("onlineVisitor1Label"));
      data.put("onlineVisitorCnt", ContextHolder.getMessage("onlineVisitorCnt"));
      data.put("isLoggedIn", Boolean.valueOf(false));
      data.put("userName", "userName");
      data.put("isVisitor", Boolean.valueOf(true));
      data.put("logoutURL", "logoutURL");
      data.put("loginURL", "loginURL");
      data.put("isMobileRequest", Boolean.valueOf(WebHelper.mobileRequest(WebHelper.request())));
      data.put("adminLabel", ContextHolder.getMessage("adminLabel"));
      data.put("logoutLabel", ContextHolder.getMessage("logoutLabel"));
      data.put("loginLabel", ContextHolder.getMessage("loginLabel"));
      data.put("registerLabel", ContextHolder.getMessage("registerLabel"));
      data.put("mobileLabel", ContextHolder.getMessage("mobileLabel"));
      data.put("adminLabel", ContextHolder.getMessage("adminLabel"));
      data.put("adminLabel", ContextHolder.getMessage("adminLabel"));
      tempdate.process(data, writer);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return writer.toString();
  }
}
