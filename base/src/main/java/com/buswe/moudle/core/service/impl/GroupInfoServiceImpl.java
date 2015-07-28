package com.buswe.moudle.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.GroupInfoDao;
import com.buswe.moudle.core.entity.GroupInfo;
import com.buswe.moudle.core.service.GroupInfoService;

@Service("groupInfoService")
@Transactional("jpaTransaction")
public class GroupInfoServiceImpl
  extends BaseServiceImpl<GroupInfo>
  implements GroupInfoService
{
  @Resource
  private GroupInfoDao groupInfoDao;
  
  public BaseRepository<GroupInfo, String> getDao()
  {
    return this.groupInfoDao;
  }
  
  public List<GroupInfo> findAll(Specifications where)
  {
    return this.groupInfoDao.findAll(where);
  }
}
