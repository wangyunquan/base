package com.buswe.moudle.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buswe.base.dao.PropertyFilter;
import com.buswe.moudle.core.entity.CodeType;
import com.buswe.moudle.core.entity.CodeValue;

public abstract interface AppCodeService
{
  public abstract Set<CodeValue> getAllCodeValue(String paramString);
  
  public abstract CodeType getCodeType(String paramString);
  
  public abstract CodeType saveCodeType(CodeType paramCodeType);
  
  public abstract void deleteCodeType(String paramString);
  
  public abstract Page<CodeType> findPage(Pageable paramPageable, Collection<PropertyFilter> paramCollection);
  
  public abstract List<CodeValue> getCodeValues(String paramString);
  
  public abstract CodeValue saveCodeValue(CodeValue paramCodeValue);
  
  public abstract void deleteCodeValue(String paramString);
  
  public abstract CodeValue getCodeValue(String paramString);
}
