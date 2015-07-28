package com.buswe.moudle.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.buswe.base.service.BaseService;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Site;

public abstract interface SiteService
  extends BaseService<Site>
{
  public abstract Page<Article> getArtiClePage(String paramString1, String paramString2);
  
  public abstract List<Site> allSite();
}
