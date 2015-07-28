package com.buswe.moudle.core.dao;

import org.springframework.data.jpa.repository.Query;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.UserBasic;

public abstract interface UserBasicDao
  extends BaseRepository<UserBasic, String>
{
  @Query("select u from UserBasic u left JOIN FETCH u.rolesinfos where u.loginName=?1")
  public abstract UserBasic findByLoginName(String paramString);
  
  @Query("select u from UserBasic u left JOIN FETCH u.rolesinfos where u.id=?1")
  public abstract UserBasic getUser(String paramString);
}
