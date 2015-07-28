package com.buswe.moudle.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.buswe.moudle.core.entity.Rolesinfo;
import com.buswe.moudle.core.service.AuthorityService;
import com.buswe.moudle.core.service.RolesinfoService;
import com.buswe.moudle.core.service.RulesInfoService;
import com.buswe.moudle.core.service.SecurityService;

@Controller
@RequestMapping({"/core/system/role"})
public class RolesInfoController
{
  @Resource
  private RolesinfoService service;
  @Resource
  private AuthorityService authService;
  @Resource
  private SecurityService securityService;
  @Resource
  private RulesInfoService rulseService;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/rolesList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid Rolesinfo entity, BindingResult bindingResult, HttpServletRequest request, String[] authId)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        Map<String, String[]> authAndRules = new HashMap();
        for (String auth : authId) {
          authAndRules.put(auth, request.getParameterValues(auth));
        }
        this.service.saveAuthAndRules(entity, authAndRules);
      }
      catch (Exception localException) {}
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    Rolesinfo entity = null;
    if (StringUtils.isNotBlank(id))
    {
      model.addAttribute("roleAuth", this.securityService.getRoleAuthority(id));
      entity = (Rolesinfo)this.service.get(id);
    }
    else
    {
      entity = new Rolesinfo();
    }
    model.addAttribute("allRules", this.rulseService.findAll());
    model.addAttribute("authMap", this.authService.authorityTree());
    model.addAttribute("entity", entity);
    return "core/system/rolesInput";
  }
  
  @RequestMapping({"/delete"})
  public String delete(String[] id)
  {
    for (String ids : id) {
      this.service.delete(ids);
    }
    return "redirect:list";
  }
}
