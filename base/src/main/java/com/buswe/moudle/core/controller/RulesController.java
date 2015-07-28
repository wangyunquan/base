package com.buswe.moudle.core.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.moudle.core.entity.RulesInfo;
import com.buswe.moudle.core.service.AuthorityService;

@Controller
@RequestMapping({"/core/system/rules"})
public class RulesController
{
  @Resource
  private AuthorityService service;
  
  @RequestMapping
  public String ruleInfo(Model model)
  {
    model.addAttribute("rulesList", this.service.getAllRules());
    return "core/system/rulesList";
  }
  
  @RequestMapping({"/save"})
  public String saveRules(@Valid RulesInfo entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = this.service.saveRules(entity);
      }
      catch (Exception localException) {}
    }
    return "redirect:listRules";
  }
  
  @RequestMapping({"/inputRules"})
  public String inputRules(String id, Model model)
  {
    RulesInfo entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = this.service.getRulesInfo(id);
    } else {
      entity = new RulesInfo();
    }
    model.addAttribute("entity", entity);
    return "core/system/rulesInput";
  }
  
  @RequestMapping({"/deleteRules"})
  public String deleteRules(String[] id)
  {
    for (String ids : id) {
      this.service.deleteAuthority(ids);
    }
    return "redirect:listRules";
  }
}
