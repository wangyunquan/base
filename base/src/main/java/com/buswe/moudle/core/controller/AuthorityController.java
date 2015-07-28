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
import com.buswe.moudle.core.entity.Authority;
import com.buswe.moudle.core.service.AuthorityService;

@Controller
@RequestMapping({"/core/system/authority"})
public class AuthorityController
{
  @Resource
  private AuthorityService service;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/authorityList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid Authority entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = this.service.saveAuthority(entity);
      }
      catch (Exception localException) {}
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    Authority entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = this.service.getAuthority(id);
    } else {
      entity = new Authority();
    }
    model.addAttribute("entity", entity);
    return "core/system/authorityInput";
  }
  
  @RequestMapping({"/delete"})
  public String delete(String[] id)
  {
    for (String ids : id) {
      this.service.deleteAuthority(ids);
    }
    return "redirect:list";
  }
}
