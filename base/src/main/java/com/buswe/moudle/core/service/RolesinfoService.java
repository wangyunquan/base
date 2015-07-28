package com.buswe.moudle.core.service;

import java.util.Map;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.core.entity.Rolesinfo;

public abstract interface RolesinfoService
  extends BaseService<Rolesinfo>
{
  public abstract void saveAuthAndRules(Rolesinfo paramRolesinfo, Map<String, String[]> paramMap);
}
