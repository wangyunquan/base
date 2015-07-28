package com.buswe.moudle.core.service;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.core.entity.UserBasic;

public abstract interface UserBasicService
  extends BaseService<UserBasic>
{
  public abstract UserBasic save(UserBasic paramUserBasic, String[] paramArrayOfString);
  
  public abstract UserBasic findByLoginName(String paramString);
  
  public abstract UserBasic getUser(String paramString);
}
