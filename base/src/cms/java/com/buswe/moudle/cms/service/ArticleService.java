package com.buswe.moudle.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Tags;

public abstract interface ArticleService
  extends BaseService<Article>
{
  public abstract List<Article> queryCatArticle(String paramString, Date paramDate1, Date paramDate2);
  
  public abstract Article getArticle(String paramString);
  
  public abstract List<Tags> getTopTags(Integer paramInteger, String paramString);
  
  public abstract Page<Article> findCatArticle(String paramString, Pageable paramPageable);
  
  public abstract Page<Article> findByTags(String paramString, Pageable paramPageable);
  
  public abstract Tags getTagbyName(String tagName,String siteId);
}
