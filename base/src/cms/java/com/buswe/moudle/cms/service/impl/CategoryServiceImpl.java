package com.buswe.moudle.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.MatchType;
import com.buswe.base.dao.QueryHelper;
import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.cms.dao.ArticleDao;
import com.buswe.moudle.cms.dao.CategoryDao;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.service.CategoryService;

@Service("categoryService")
@Transactional("jpaTransaction")
public class CategoryServiceImpl
  extends BaseServiceImpl<Category>
  implements CategoryService
{
  @Resource
  CategoryDao categoryDao;
  @Resource
  ArticleDao articleDao;
  
  public BaseRepository<Category, String> getDao()
  {
    return this.categoryDao;
  }
  
  @Cacheable(value={"CMC_CACHE_PUBLIC"}, key="#siteId + 'catCache'")
  public List<Category> findBySiteId(String siteId)
  {
    Specification<Category> spec = QueryHelper.filter("site.id", 
      MatchType.EQ, siteId);
    Sort sort = new Sort(Sort.Direction.ASC, new String[] { "sort" });
    return this.categoryDao.findAll(spec, sort);
  }
  
  public Long getCatTopic(String catId)
  {
    Specification<Article> spe = QueryHelper.filter("", MatchType.EQ, catId);
    return Long.valueOf(this.articleDao.count(spe));
  }
  
  public List<Article> getCatArticle(String catId)
  {
    return null;
  }
}
