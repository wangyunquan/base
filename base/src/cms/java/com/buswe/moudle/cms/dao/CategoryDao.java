package com.buswe.moudle.cms.dao;

import java.util.List;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.cms.entity.Category;

public abstract interface CategoryDao
  extends BaseRepository<Category, String>
{
  public abstract List<Category> findBySiteId(String paramString);
}
