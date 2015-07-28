package com.buswe.moudle.core.controller;

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
import com.buswe.moudle.core.entity.Menu;
import com.buswe.moudle.core.service.MenuService;

@Controller
@RequestMapping({"/core/system/menu"})
public class MenuController
{
  @Resource
  private MenuService service;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/menuList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid Menu entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity.setParent((Menu)this.service.get(entity.getParent().getId()));
        entity = (Menu)this.service.save(entity);
      }
      catch (Exception localException) {}
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    Menu entity = null;
    if (StringUtils.isNotBlank(id))
    {
      entity = this.service.getMenu(id);
      if (entity.getParent() != null) {
        model.addAttribute("parentId", entity.getParent().getId());
      }
    }
    else
    {
      entity = new Menu();
    }
    model.addAttribute("allMenu", this.service.findAll());
    model.addAttribute("entity", entity);
    return "core/system/menuInput";
  }
  
  @RequestMapping({"/delete"})
  public String delete(String[] id)
  {
    for (String ids : id) {
      this.service.delete(ids);
    }
    return "redirect:";
  }
}
