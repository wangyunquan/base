package com.buswe.base.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.buswe.base.config.ContextHolder;
import com.buswe.moudle.core.entity.CodeValue;
import com.buswe.moudle.core.service.AppCodeService;

public class CodeHolder
{
  private static Map<String, Set<CodeValue>> codeCache = new HashMap();
  private static Boolean init = Boolean.valueOf(false);
  private static AppCodeService appCodeService;
  
  public static <T> T getCodeValue(String type, String code)
  {
    Set<CodeValue> typeCode = getAllCodeValue(type);
    for (CodeValue single : typeCode) {
      if (single.getCode().equalsIgnoreCase(code)) {
        return (T) single.getValue();
      }
    }
    if (StringUtils.isBlank(code)) {
      return (T) "";
    }
    return (T) "";
  }
  
  public static Set<CodeValue> getAllCodeValue(String type)
  {
    init();
    Set<CodeValue> typeCode = (Set)codeCache.get(type);
    if (typeCode == null)
    {
      typeCode = appCodeService.getAllCodeValue(type);
      codeCache.put(type, typeCode);
    }
    return typeCode;
  }
  
  private static void init()
  {
    if (!init.booleanValue()) {
      appCodeService = (AppCodeService)ContextHolder.getBean(AppCodeService.class);
    }
  }
}
