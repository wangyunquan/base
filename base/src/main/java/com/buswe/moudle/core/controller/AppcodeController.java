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
import com.buswe.moudle.core.entity.CodeType;
import com.buswe.moudle.core.entity.CodeValue;
import com.buswe.moudle.core.service.AppCodeService;

@Controller
@RequestMapping({"/core/system/appcode"})
public class AppcodeController
{
  @Resource
  private AppCodeService service;
  
  @RequestMapping
  public String list(Pageable page, HttpServletRequest request, Model model)
  {
    List<PropertyFilter> filters = WebHelper.filterRequest(request);
    model.addAttribute("page", 
      this.service.findPage(page, filters));
    return "core/system/codeTypeList";
  }
  
  @RequestMapping({"/save"})
  public String save(@Valid CodeType entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = this.service.saveCodeType(entity);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return "redirect:list";
  }
  
  @RequestMapping({"/input"})
  public String input(String id, Model model)
  {
    CodeType entity = null;
    if (StringUtils.isNotBlank(id))
    {
      entity = this.service.getCodeType(id);
      List<CodeValue> listCode = this.service.getCodeValues(id);
      model.addAttribute("listCode", listCode);
    }
    else
    {
      entity = new CodeType();
    }
    model.addAttribute("entity", entity);
    return "core/system/typeInput";
  }
  
  @RequestMapping({"/delete"})
  public String delete(String[] id)
  {
    for (String ids : id) {
      this.service.deleteCodeType(ids);
    }
    return "";
  }
  
  @RequestMapping({"/codelist"})
  public String codeList(Model model, String id)
  {
    List<CodeValue> listCode = this.service.getCodeValues(id);
    model.addAttribute("listCode", listCode);
    model.addAttribute("typeId", id);
    return "core/system/codelist";
  }
  
  @RequestMapping({"/codeinput"})
  public String editCode(String codeTypeId, String id, Model model)
  {
    CodeValue entity;
    if (StringUtils.isNotBlank(id))
    {
      entity = this.service.getCodeValue(id);
    }
    else
    {
      entity = new CodeValue();
      entity.setEnable(Boolean.valueOf(true));
    }
    CodeType codetype = this.service.getCodeType(codeTypeId);
    entity.setCodeType(codetype);
    model.addAttribute("entity", entity);
    return "core/system/codeInput";
  }
  
  @RequestMapping({"/codesave"})
  public String saveCode(CodeValue entity, BindingResult bindingResult)
  {
    if (!bindingResult.hasErrors()) {
      try
      {
        entity = this.service.saveCodeValue(entity);
      }
      catch (Exception localException) {}
    }
    return "redirect:input?id=" + entity.getCodeType().getId();
  }
  
  @RequestMapping({"/codedelete"})
  public String deletecode(String[] id)
  {
    for (String ids : id) {
      this.service.deleteCodeValue(ids);
    }
    return "";
  }
}
