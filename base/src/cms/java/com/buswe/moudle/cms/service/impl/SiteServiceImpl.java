package com.buswe.moudle.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.MatchType;
import com.buswe.base.dao.QueryHelper;
import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.cms.dao.ArticleDao;
import com.buswe.moudle.cms.dao.SiteDao;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.service.SiteService;

@Service("siteService")
@Transactional("jpaTransaction")
public class SiteServiceImpl
  extends BaseServiceImpl<Site>
  implements SiteService
{
  @Resource
  SiteDao siteDao;
  @Resource
  ArticleDao articleDao;
  
  public BaseRepository<Site, String> getDao()
  {
    return this.siteDao;
  }
  
  public Page<Article> getArtiClePage(String pageNo, String siteId)
  {
    Specification<Article> spec = QueryHelper.filter("category.site.id", MatchType.EQ, siteId);
    return this.articleDao.findAll(spec, new PageRequest(new Integer(pageNo).intValue(), 20, Sort.Direction.DESC, new String[] { "updateDate" }));
  }
  
  @Cacheable({"CMC_CACHE_PUBLIC"})
  public List<Site> allSite()
  {
    return this.siteDao.findAll();
  }
}
