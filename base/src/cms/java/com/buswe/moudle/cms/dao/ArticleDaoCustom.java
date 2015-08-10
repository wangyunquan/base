package com.buswe.moudle.cms.dao;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buswe.moudle.cms.entity.Article;

public interface ArticleDaoCustom {
	
	public  Page<Article> search( EntityManager em,String keyWords, Pageable pageable);
	
	 public void reindex(EntityManager em);
}
