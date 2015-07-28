package com.buswe.moudle.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.Position;

public abstract interface PositionDao
  extends BaseRepository<Position, String>
{
  @Query("select p from Position p where p.adminRank.name=?")
  public abstract List<Position> findByAdminRank(String paramString);
}
