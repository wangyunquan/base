package com.buswe.moudle.cms.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.cms.entity.Tags;

public abstract interface TagsDao
  extends BaseRepository<Tags, String>
{
  public abstract Tags findByTagNameAndSiteId(String tagName,String siteId);
  
  public abstract List<Tags> findBySiteIdOrderByRefCountDesc(Pageable paramPageable, String paramString);
}
