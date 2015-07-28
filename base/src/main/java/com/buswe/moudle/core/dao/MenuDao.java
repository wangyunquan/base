package com.buswe.moudle.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.Menu;

public abstract interface MenuDao
  extends BaseRepository<Menu, String>
{
  public abstract List<Menu> findByLevel(int paramInt);
  
  @Query("select m from Menu m left join fetch m.parent  where m.id=:id")
  public abstract Menu getInitParent(@Param("id") String paramString);
}
