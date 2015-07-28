package com.buswe.moudle.cms.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.base.utils.DateTimeUtils;
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.service.CategoryService;
import com.buswe.moudle.cms.service.GenerateService;

@Controller
@RequestMapping({"/cms/back/generate"})
public class GenerateController
{
  @Resource
  CategoryService service;
  @Resource
  GenerateService generateService;
  
  @RequestMapping
  public String input(String siteId, Model model)
  {
    model.addAttribute("siteId", siteId);
    List<Category> list = this.service.findBySiteId(siteId);
    model.addAttribute("catList", list);
    return "cms/back/generate";
  }
  
  @RequestMapping({"/generate"})
  public String generate(String siteId, String type, String catId, String beginDate, String endDate)
  {
    if (type.equalsIgnoreCase("cat"))
    {
      if (catId != null) {
        this.generateService.generateCat((Category)this.service.get(catId), DateTimeUtils.formatDate(beginDate), DateTimeUtils.formatDate(endDate));
      } else {
        this.generateService.generateAllcat(siteId);
      }
    }
    else {
      this.generateService.generateIndex(siteId);
    }
    return "cms/back/generate";
  }
}
