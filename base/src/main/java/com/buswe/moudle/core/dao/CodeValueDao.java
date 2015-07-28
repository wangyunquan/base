package com.buswe.moudle.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.CodeValue;

public abstract interface CodeValueDao
  extends BaseRepository<CodeValue, String>
{
  @Query("select entity from CodeValue entity where entity.codeType.id=:codeType")
  public abstract List<CodeValue> findByCodeType(@Param("codeType") String paramString);
}
