package com.buswe.moudle.cms.search;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.buswe.moudle.cms.entity.Article;

public class SearchHelper {

	public static Page<Article> queryList(EntityManager em, String keyWords, Pageable pageable) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		em.getTransaction().begin();
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title", "outline", "articleData.lobContent")
				.matching(keyWords).createQuery();
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Article.class);
		jpaQuery.setFirstResult(pageable.getOffset());
		jpaQuery.setMaxResults(pageable.getPageSize());
		Integer total = jpaQuery.getResultSize();
		List<Article> result = jpaQuery.getResultList();
		em.getTransaction().commit();
		em.close();
		PageImpl<Article> page = new PageImpl<Article>(result, pageable, total);
		return page;
	}
	public static void index(EntityManager em)
	{
	FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
	try {
		fullTextEntityManager.createIndexer().startAndWait();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
}
