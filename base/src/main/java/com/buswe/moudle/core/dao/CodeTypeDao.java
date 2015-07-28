package com.buswe.moudle.core.dao;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.CodeType;

public abstract interface CodeTypeDao
  extends BaseRepository<CodeType, String>
{
  public abstract String findNameByType(String paramString);
}
