package com.buswe.moudle.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.LoginfoDao;
import com.buswe.moudle.core.entity.Loginfo;
import com.buswe.moudle.core.service.LoginfoService;

@Service("loginfoService")
@Transactional
public class LoginfoServiceImpl
  extends BaseServiceImpl<Loginfo>
  implements LoginfoService
{
  private LoginfoDao loginfoDao;
  
  @Resource(name="loginfoDao")
  public void setDao(LoginfoDao loginfoDao)
  {
    this.loginfoDao = loginfoDao;
  }
  
  public BaseRepository<Loginfo, String> getDao()
  {
    return this.loginfoDao;
  }
}
