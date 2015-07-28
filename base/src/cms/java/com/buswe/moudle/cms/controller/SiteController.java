package com.buswe.moudle.cms.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.service.SiteService;

@Controller
@RequestMapping({"/cms/back/site"})
public class SiteController
{
  @Resource
  SiteService service;
  
  @RequestMapping
  public String list(Model model)
  {
    model.addAttribute("siteList", this.service.findAll());
    return "cms/back/siteList";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    Site entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = (Site)this.service.get(id);
    } else {
      entity = new Site();
    }
    model.addAttribute("entity", entity);
    return "cms/siteInput";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid Site entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = (Site)this.service.save(entity);
      }
      catch (Exception localException) {}
    }
    return "redirect:list";
  }
}
