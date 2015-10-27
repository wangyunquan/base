package com.buswe.moudle.cms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.base.dao.PropertyFilter;
import com.buswe.base.web.WebHelper;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.service.ArticleService;
import com.buswe.moudle.cms.service.CategoryService;

@Controller
@RequestMapping({"/cms/back/info"})
public class ArticleController
{
  @Resource
  ArticleService service;
  @Resource
  CategoryService catService;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    request.setAttribute("siteId", request.getParameter("siteId"));
    model.addAttribute("page", this.service.findPage(page, filters));
    return "cms/back/infoList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid Article entity, BindingResult bindingResult)
  {
    try
    {
      entity = (Article)this.service.save(entity);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    Article entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = (Article)this.service.get(id);
    } else {
      entity = new Article();
    }
    model.addAttribute("catList", this.catService.findBySiteId("1"));
    model.addAttribute("entity", entity);
    return "cms/back/infoInput";
  }
  @RequestMapping({"/delete"})
  public String delete(String id)
  {
	  service.delete(id);
	   return "redirect:";
  }
}
