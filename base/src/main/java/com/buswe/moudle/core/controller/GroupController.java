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
import com.buswe.moudle.core.entity.GroupInfo;
import com.buswe.moudle.core.service.GroupInfoService;

@Controller
@RequestMapping({"/core/system/group"})
public class GroupController
{
  @Resource
  private GroupInfoService service;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/groupList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid GroupInfo entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = (GroupInfo)this.service.save(entity);
      }
      catch (Exception localException) {}
    }
    return "redirect:";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model, String groupType)
  {
    GroupInfo entity = null;
    if (StringUtils.isNotBlank(id)) {
      entity = (GroupInfo)this.service.get(id);
    } else {
      entity = new GroupInfo();
    }
    entity.setGroupType(Integer.valueOf(groupType));
    model.addAttribute("entity", entity);
    return "core/system/groupInput";
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
