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
import com.buswe.moudle.core.entity.UserBasic;
import com.buswe.moudle.core.service.RolesinfoService;
import com.buswe.moudle.core.service.UserBasicService;

@Controller
@RequestMapping({"/core/system/user"})
public class UserController
{
  @Resource
  UserBasicService service;
  @Resource
  RolesinfoService roleService;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/userList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid UserBasic entity, String[] rolesIds, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = this.service.save(entity, rolesIds);
      }
      catch (Exception localException) {}
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    UserBasic entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = this.service.getUser(id);
    } else {
      entity = new UserBasic();
    }
    model.addAttribute("allRoles", this.roleService.findAll());
    model.addAttribute("entity", entity);
    return "core/system/userInput";
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
