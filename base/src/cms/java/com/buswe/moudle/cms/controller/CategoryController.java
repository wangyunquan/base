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
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.service.CategoryService;
import com.buswe.moudle.cms.service.SiteService;

@Controller
@RequestMapping({"/cms/back/cat"})
public class CategoryController
{
  @Resource
  CategoryService service;
  @Resource
  SiteService siteService;
  
  @RequestMapping
  public String list(String siteId, Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    filters.add(new PropertyFilter("site.id", siteId));
    model.addAttribute("siteId", siteId);
    model.addAttribute("page", this.service.findPage(page, filters));
    return "cms/back/catList";
  }
  
  @RequestMapping({"/input"})
  public String input(String siteId, String id, Model model)
  {
    Category entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = (Category)this.service.get(id);
    } else {
      entity = new Category();
    }
    model.addAttribute("siteId", siteId);
    List<Category> list = this.service.findBySiteId(siteId);
    model.addAttribute("catList", list);
    model.addAttribute("entity", entity);
    return "cms/back/catInput";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid Category entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity.setSite((Site)this.siteService.get(entity.getSite().getId()));
        entity.setParent((Category)this.service.get(entity.getParent().getId()));
        entity = (Category)this.service.save(entity);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return "redirect:";
  }
  
  @RequestMapping({"/tree"})
  public String tree(String siteId, Model model)
  {
    List<Category> list = this.service.findBySiteId(siteId);
    model.addAttribute("catList", list);
    return "cms/back/catTree";
  }
}
