package com.buswe.moudle.cms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.buswe.moudle.cms.dao.ArticleDaoCustom;
import com.buswe.moudle.cms.entity.Article;

@Repository 
public class ArticleDaoImpl  implements ArticleDaoCustom{
	  private static Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Page<Article> search(  EntityManager em,String keyWords, Pageable pageable) {
	Long time =System.nanoTime();
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
		
		QueryParser queryParser=new QueryParser(Version.LUCENE_4_9_1, keyWords, new IKAnalyzer()) ;
		/**
		 * 
		 */
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title", "outline", "articleData.lobContent")
				.matching(keyWords).createQuery();
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Article.class);
		jpaQuery.setFirstResult(pageable.getOffset());
		jpaQuery.setMaxResults(pageable.getPageSize());
		Integer total = jpaQuery.getResultSize();
		List<Article> result = jpaQuery.getResultList();
		PageImpl<Article> page = new PageImpl<Article>(result, pageable, total);
	Long totalTime=System.nanoTime()-time;
	logger.debug("Seach total time is "+totalTime);
		return page;
	}
 public void reindex(EntityManager em)
 {
	 FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
	 try {
		fullTextEntityManager.createIndexer().startAndWait();
	} catch (InterruptedException e) {
	 
		e.printStackTrace();
	}
 }
}
