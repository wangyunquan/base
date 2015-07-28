package com.buswe.moudle.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.RulesInfoDao;
import com.buswe.moudle.core.entity.RulesInfo;
import com.buswe.moudle.core.service.RulesInfoService;

@Service("rulesInfoService")
@Transactional
public class RulesInfoServiceImpl
  extends BaseServiceImpl<RulesInfo>
  implements RulesInfoService
{
  private RulesInfoDao rulesInfoDao;
  
  @Resource(name="rulesInfoDao")
  public void setDao(RulesInfoDao rulesInfoDao)
  {
    this.rulesInfoDao = rulesInfoDao;
  }
  
  public BaseRepository<RulesInfo, String> getDao()
  {
    return this.rulesInfoDao;
  }
}
