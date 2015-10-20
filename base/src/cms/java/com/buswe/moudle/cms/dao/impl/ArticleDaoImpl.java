package com.buswe.moudle.cms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
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
	 
		
		/**
		   QueryParser parser = new QueryParser(Version.LUCENE_31, "content",  
                new SimpleAnalyzer(Version.LUCENE_31));  
        org.apache.lucene.search.Query luceneQuery = null;  
        try {  
            luceneQuery = parser.parse(content);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        
      
		 */
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title", "outline", "articleData.lobContent")
				.matching(keyWords).createQuery();
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Article.class);
		jpaQuery.setFirstResult(pageable.getOffset());
		jpaQuery.setMaxResults(pageable.getPageSize());
		Integer total = jpaQuery.getResultSize();
		List<Article> result = jpaQuery.getResultList();
		//高亮显示
		  SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
					"<b><font color='red'>", "</font></b>");
					QueryScorer qs = new QueryScorer(luceneQuery);
					Highlighter highlighter = new Highlighter(formatter, qs);  
					// 这个20是指定关键字字符串的context的长度，你可以自己设定，因为不可能返回整篇正文内容  
			        highlighter.setTextFragmenter(new SimpleFragmenter(20));  
			        String contentStr="";
			        for (Article article : result) {  
			            Analyzer analyzer = new IKAnalyzer();  
			            try {  
			                contentStr = article.getArticleData().getLobContent();
			                // 去掉所有html元素,  
			                contentStr = contentStr.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>",  
			                        "").replaceAll("</[a-zA-Z]+[1-9]?>", "");  
			  
			                String contentHighLighter = highlighter.getBestFragment(  
			                        analyzer, keyWords, contentStr);  
			                article.getArticleData().setLobContent(contentHighLighter);
			            } catch (Exception e) {  
			                e.printStackTrace();  
			            }  
			        }  
					
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
