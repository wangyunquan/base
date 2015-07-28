package com.buswe.moudle.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.PositionDao;
import com.buswe.moudle.core.entity.Position;
import com.buswe.moudle.core.service.PositionService;

@Service("positionService")
@Transactional
public class PositionServiceImpl
  extends BaseServiceImpl<Position>
  implements PositionService
{
  private PositionDao positionDao;
  
  @Resource(name="positionDao")
  public void setDao(PositionDao positionDao)
  {
    this.positionDao = positionDao;
  }
  
  public BaseRepository<Position, String> getDao()
  {
    return this.positionDao;
  }
}
