package com.buswe.moudle.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.AdminRankDao;
import com.buswe.moudle.core.entity.AdminRank;
import com.buswe.moudle.core.service.AdminRankService;

@Service("adminRankService")
@Transactional
public class AdminRankServiceImpl
  extends BaseServiceImpl<AdminRank>
  implements AdminRankService
{
  @Resource
  private AdminRankDao adminRankDao;
  
  public BaseRepository<AdminRank, String> getDao()
  {
    return this.adminRankDao;
  }
}
