package com.buswe.moudle.cms.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.buswe.base.config.AppConfig;
import com.buswe.base.utils.DateTimeUtils;
import com.buswe.moudle.cms.CmsConfig;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Category;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generate
{
  public static void generateInfo(Configuration config, Article info, Category cat)
    throws Exception
  {
    Template template = null;
    String infoTemplate = info.getTemplates();
    if (StringUtils.isNotBlank(infoTemplate)) {
      template = config.getTemplate(infoTemplate);
    } else {
      template = config.getTemplate(cat.getTemplates());
    }
    Map<String, Object> rootMap = new HashMap();
    
    List<TitleText> textList = PageHelper.getContent(info);
    int total = textList.size();
    String path = null;
    for (int page = 1; page <= total; page++)
    {
      rootMap.put("content", ((TitleText)textList.get(page - 1)).getText());
      rootMap.put("title", ((TitleText)textList.get(page - 1)).getTitle());
      rootMap.put("info", info);
      rootMap.put("cat", cat);
      if (path == null) {
        path = getInfoStaticUrl(cat);
      }
      path = path + page;
      File file = new File(path + "." + CmsConfig.getStaticExtention());
      file.getParentFile().mkdirs();
      FileOutputStream fos = null;
      Writer out = null;
      try
      {
        fos = new FileOutputStream(file);
        out = new OutputStreamWriter(fos, "UTF-8");
        template.process(rootMap, out);
      }
      finally
      {
        if (out != null)
        {
          out.flush();
          out.close();
        }
        if (fos != null) {
          fos.close();
        }
      }
    }
  }
  
  public static void generateCat(Configuration config, Category cat, Long page)
    throws Exception
  {
    Template template = config.getTemplate(cat.getTemplates());
    
    Map<String, Object> rootMap = new HashMap();
    
    String path = null;
    rootMap.put("cat", cat);
    for (int i = 0; i < page.longValue(); i++)
    {
      if (path == null) {
        path = getChanelStaticUrl(cat);
      }
      path = path + page;
      File file = new File(path + "." + CmsConfig.getStaticExtention());
      file.getParentFile().mkdirs();
      FileOutputStream fos = null;
      Writer out = null;
      try
      {
        fos = new FileOutputStream(file);
        out = new OutputStreamWriter(fos, "UTF-8");
        template.process(rootMap, out);
      }
      finally
      {
        if (out != null)
        {
          out.flush();
          out.close();
        }
        if (fos != null) {
          fos.close();
        }
      }
    }
  }
  
  public static boolean generateIndex(Template template, Map<String, Object> map)
  {
    return true;
  }
  
  private static String getInfoStaticUrl(Category cat)
  {
    String path = AppConfig.getWebContainerPath() + File.separator + "file";
    path = path + File.separator + cat.getName() + File.separator + 
      DateTimeUtils.getCurrentDateString("yyyyMMddHHmmss");
    return path;
  }
  
  private static String getChanelStaticUrl(Category cat)
  {
    String path = AppConfig.getWebContainerPath() + File.separator + "file";
    path = path + File.separator + cat.getName() + File.separator + 
      DateTimeUtils.getCurrentDateString("yyyyMMddHHmmss");
    return path;
  }
}
