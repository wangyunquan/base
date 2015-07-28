package com.buswe.moudle.cms.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.buswe.base.config.AppConfig;
import com.buswe.base.utils.DateTimeUtils;
import com.buswe.moudle.cms.CmsConfig;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.freemarker.FreemarkerDataUtil;
import com.buswe.moudle.cms.helper.PageHelper;
import com.buswe.moudle.cms.helper.TitleText;
import com.buswe.moudle.cms.service.ArticleService;
import com.buswe.moudle.cms.service.CategoryService;
import com.buswe.moudle.cms.service.GenerateService;
import com.buswe.moudle.cms.service.SiteService;

import freemarker.template.Template;

@Service
@Transactional("jpaTransaction")
public class GenerateServiceImpl
  implements GenerateService
{
  @Resource
  SiteService siteService;
  @Resource
  CategoryService categoryService;
  @Resource
  ArticleService articleService;
  FreeMarkerConfigurer config;
  
  public boolean generateIndex(String siteId)
  {
    Site site = (Site)this.siteService.get(siteId);
    try
    {
      Template template = this.config.getConfiguration().getTemplate(site.getTheme() + File.separator + "index.html");
      Map<String, Object> map = new HashMap();
      FreemarkerDataUtil.appdendData(map, site);
      String path = AppConfig.getWebContainerPath() + File.separator + "html" + site.getTheme() + File.separator + "index" + CmsConfig.getStaticExtention();
      FreemarkerDataUtil.makeStaticFile(path, map, template);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return true;
  }
  
  public boolean GenerateContent(Article article)
  {
    try
    {
      Site site = (Site)this.siteService.get(article.getCategory().getSite().getId());
      Category cat = (Category)this.categoryService.get(article.getCategory().getId());
      Template template = this.config.getConfiguration().getTemplate(site.getTheme() + File.separator + article.getTemplates());
      Map<String, Object> map = new HashMap();
      String path = AppConfig.getWebContainerPath() + File.separator + "html" + site.getTheme() + 
        File.separator + cat.getDisplayName() + File.separator + article.getTitle();
      List<TitleText> textList = PageHelper.getContent(article);
      int total = textList.size();
      for (int page = 1; page <= total; page++)
      {
        map.put("content", ((TitleText)textList.get(page - 1)).getText());
        map.put("title", ((TitleText)textList.get(page - 1)).getTitle());
        map.put("info", article);
        map.put("cat", cat);
        String filepath = path + page + CmsConfig.getStaticExtention();
        FreemarkerDataUtil.appdendData(map, site);
        FreemarkerDataUtil.makeStaticFile(filepath, map, template);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return true;
  }
  
  public Integer generateCat(Category cat, Date begin, Date end)
  {
    Site site = (Site)this.siteService.get(cat.getSite().getId());
    try
    {
      Template template = this.config.getConfiguration().getTemplate(site.getTheme() + File.separator + cat.getTemplates());
      Map<String, Object> map = new HashMap();
      FreemarkerDataUtil.appdendData(map, site);
      map.put("cat", cat);
      map.put("title", cat.getDisplayName());
      List<Article> articleList = this.articleService.queryCatArticle(cat.getId(), begin, end);
      Integer pageSize = cat.getCatSize();
      int i = 0;
      while (i * pageSize.intValue() <= articleList.size())
      {
        if (pageSize.intValue() * (i + 1) < articleList.size()) {
          map.put("articleList", articleList.subList(pageSize.intValue() * i, pageSize.intValue()));
        } else {
          map.put("articleList", articleList.subList(pageSize.intValue() * i, articleList.size()));
        }
        String path = AppConfig.getWebContainerPath() + File.separator + "html" + File.separator + site.getTheme() + File.separator;
        File file = new File(path);
        if (!file.exists()) {
          file.mkdirs();
        }
        path = path + cat.getName();
        if (i != 0) {
          path = path + "_" + i;
        }
        path = path + CmsConfig.getStaticExtention();
        FreemarkerDataUtil.makeStaticFile(path, map, template);
        i++;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public Integer generateAllcat(String siteId)
  {
    List<Category> catList = this.categoryService.findBySiteId(siteId);
    for (Category cat : catList) {
      generateCat(cat, DateTimeUtils.formatDate("2014-01-01"), new Date());
    }
    return Integer.valueOf(0);
  }
}
