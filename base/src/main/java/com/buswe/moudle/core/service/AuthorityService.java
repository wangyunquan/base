package com.buswe.moudle.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buswe.base.dao.PropertyFilter;
import com.buswe.moudle.core.entity.Authority;
import com.buswe.moudle.core.entity.RulesInfo;

public abstract interface AuthorityService
{
  public abstract Map<String, List<Authority>> authorityTree();
  
  public abstract Page<Authority> findPage(Pageable paramPageable, List<PropertyFilter> paramList);
  
  public abstract Authority saveAuthority(Authority paramAuthority);
  
  public abstract Authority getAuthority(String paramString);
  
  public abstract void deleteAuthority(String paramString);
  
  public abstract RulesInfo saveRules(RulesInfo paramRulesInfo);
  
  public abstract List<RulesInfo> getAllRules();
  
  public abstract RulesInfo getRulesInfo(String paramString);
  
  public abstract void deleteRulesInfo(String paramString);
}
