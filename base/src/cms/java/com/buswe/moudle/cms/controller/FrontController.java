package com.buswe.moudle.cms.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.moudle.cms.CmsConstants;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.entity.Comment;
import com.buswe.moudle.cms.entity.Tags;
import com.buswe.moudle.cms.helper.CmsUtil;
import com.buswe.moudle.cms.service.ArticleService;
import com.buswe.moudle.cms.service.CategoryService;
import com.buswe.moudle.cms.service.SiteService;

@Controller
@RequestMapping({"/blog"})
public class FrontController
{
  @Resource
  private ArticleService articleService;
  @Resource
  private CategoryService categoryService;
  @Resource
  private SiteService siteService;
  
  @RequestMapping({"/"})
  public String index(Model model)
  {
    model.addAttribute("page", this.siteService.getArtiClePage("0", CmsConstants.BLOG_SITE_ID));
    model.addAllAttributes(CmsUtil.CmsPublic());
    return "blogindex";
  }
  
  @RequestMapping({"/search"})
  public String search(String q,Pageable page,Model model)
  {
	    model.addAllAttributes(CmsUtil.CmsPublic());
	   model.addAttribute("page" ,articleService.search(q, page));
	   model.addAttribute("seachkey" ,q);
	  return "blogsearch";
  }
  @RequestMapping({"/cat/{catName}"})
  public String cat(Model model, @PathVariable String catName, Pageable page)
  {
    model.addAllAttributes(CmsUtil.CmsPublic());
    List<Category> catList = this.categoryService.findBySiteId(CmsUtil.getContextSite().getId());
    String catID = "";
    for (Category cat : catList) {
      if (cat.getName().equals(catName)) {
        catID = cat.getId();
        model.addAttribute("cat",cat);
      }
    }
    model.addAttribute("page",  this.articleService.findCatArticle(catID, page));
    return "blogcat";
  }
  
  @RequestMapping({"/tag/{tagName}"})
  public String tag(Model model, @PathVariable String tagName, Pageable page)
  {
    model.addAllAttributes(CmsUtil.CmsPublic());
    Tags tagsData = articleService.getTagbyName(tagName,CmsUtil.getContextSite().getId());
    model.addAttribute("page",this.articleService.findByTags(tagsData, page));
    model.addAttribute("tags",tagsData);
    return "blogtag";
  }
  
  @RequestMapping({"/comment/save"})
  public String comment(Model model, Comment comment)
  {
    model.addAllAttributes(CmsUtil.CmsPublic());
    return "blogtag";
  }
  
  @RequestMapping({"/{artId}.html"})
  public String post(Model model, @PathVariable String artId)
  {
    Article entity = this.articleService.getArticle(artId);
    model.addAttribute("entity", entity);
    model.addAllAttributes(CmsUtil.CmsPublic());
    return "blogpost";
  }
}
