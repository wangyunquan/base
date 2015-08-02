package com.buswe.moudle.cms.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.Tags;

public abstract interface ArticleDao extends BaseRepository<Article, String>,ArticleDaoSearch {
	@Query("select new Article(id,user.id,user.name,title,desciption,updateDate)  from Article   where  category.id=:catId and status =0  and updateDate between :beginDate and:endDate  order by updateDate, weight")
	public abstract List<Article> queryCatArticle(@Param("catId") String paramString,
			@Param("beginDate") Date paramDate1, @Param("endDate") Date paramDate2);

	@Query("select entity  from Article entity where entity.id=:articleId")
	public abstract Article getArticle(@Param("articleId") String paramString);

	@Query(value = "select entity from Article entity where  entity.category.id=:catId and entity.status=0 order by entity.weight desc,entity.updateDate desc ", countQuery = "select count(1)  from Article where category.id=:catId and status=0 ")
	public abstract Page<Article> findByCatId(@Param("catId") String catId, Pageable paramPageable);

//	@Query(value=" select entity  from Article  entity      where  entity.tagList in(:tagId) and entity.status=0 order by entity.weight desc,entity.updateDate desc ",countQuery="select count(1)  from Article  entity   where  entity.tagList in(:tagId) and entity.status=0 ")
	public abstract Page<Article> findByTagListInAndStatusOrderByUpdateDateDesc(Collection <Tags> tagId, String status,Pageable paramPageable);
}
