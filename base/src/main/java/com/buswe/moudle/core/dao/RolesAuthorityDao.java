package com.buswe.moudle.core.dao;

import java.util.List;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.RolesAuthority;

public abstract interface RolesAuthorityDao
  extends BaseRepository<RolesAuthority, String>
{
  public abstract List<RolesAuthority> findByRolesinfoId(String paramString);
}
