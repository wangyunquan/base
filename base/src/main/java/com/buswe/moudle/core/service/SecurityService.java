package com.buswe.moudle.core.service;

import java.util.Map;
import java.util.Set;

import com.buswe.moudle.core.entity.RulesInfo;

public abstract interface SecurityService
{
  public abstract Map<String, Set<RulesInfo>> getRoleAuthority(String paramString);
}
