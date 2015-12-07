package com.buswe.moudle.cms.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.buswe.base.config.ContextHolder;
import com.buswe.base.service.CodeHolder;
import com.buswe.base.web.WebHelper;
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.entity.Tags;
import com.buswe.moudle.cms.service.ArticleService;
import com.buswe.moudle.cms.service.CategoryService;
import com.buswe.moudle.cms.service.SiteService;

public class CmsUtil
{
  public static Map<String, Object> CmsPublic()
  {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("ctx", WebHelper.request().getContextPath());
    model.put("site", getContextSite());
    CategoryService catService = (CategoryService)ContextHolder.getBean(CategoryService.class);
    List<Category> catList = catService.findBySiteId(getContextSite().getId());
    model.put("catList", catList);
    ArticleService artiCleservice = (ArticleService)ContextHolder.getBean(ArticleService.class);
    List<Tags> tagsList = artiCleservice.getTopTags(Integer.valueOf(20), getContextSite().getId());
    model.put("tagsList", tagsList);
    return model;
  }
  
  public static String getOutLine(String text)
  {
    Integer length = (Integer)CodeHolder.getCodeValue("cms.blog.config", "outline");
    if (text.length() > length.intValue()) {
      return text.substring(0, length.intValue());
    }
    return text;
  }
  
  public static Site getContextSite()
  {
    HttpServletRequest request = WebHelper.request();
    String url = request.getRequestURI();
    String context = request.getContextPath();
    String path = url.substring(context.length() + 1);
   
  
  path = path.substring(0, path.indexOf("/"));
    SiteService service = (SiteService)ContextHolder.getBean(SiteService.class);
    List<Site> allSite = service.allSite();
    Iterator localIterator = allSite.iterator();
    if (localIterator.hasNext())
    {
      Site site = (Site)localIterator.next();
      if (site.getPath().equals(path)) {
        return site;
      }
      return null;
    }
    return null;
  }
}
