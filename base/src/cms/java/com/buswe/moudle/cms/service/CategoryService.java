package com.buswe.moudle.cms.service;

import java.util.List;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Category;

public abstract interface CategoryService
  extends BaseService<Category>
{
  public abstract List<Category> findBySiteId(String paramString);
  
  public abstract Long getCatTopic(String paramString);
  
  public abstract List<Article> getCatArticle(String paramString);
}
