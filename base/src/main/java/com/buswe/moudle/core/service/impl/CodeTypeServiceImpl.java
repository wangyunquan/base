package com.buswe.moudle.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.core.dao.CodeTypeDao;
import com.buswe.moudle.core.entity.CodeType;
import com.buswe.moudle.core.service.CodeTypeService;

@Service("codeTypeService")
@Transactional("jpaTransaction")
public class CodeTypeServiceImpl
  extends BaseServiceImpl<CodeType>
  implements CodeTypeService
{
  @Resource
  protected CodeTypeDao codeTypeDao;
  
  public BaseRepository<CodeType, String> getDao()
  {
    return this.codeTypeDao;
  }
}
