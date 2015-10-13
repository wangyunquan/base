package com.buswe.moudle.cms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.cms.dao.ArticleDao;
import com.buswe.moudle.cms.dao.ArticleDataDao;
import com.buswe.moudle.cms.dao.CategoryDao;
import com.buswe.moudle.cms.dao.CommentDao;
import com.buswe.moudle.cms.dao.TagsDao;
import com.buswe.moudle.cms.entity.Article;
import com.buswe.moudle.cms.entity.ArticleData;
import com.buswe.moudle.cms.entity.Category;
import com.buswe.moudle.cms.entity.Comment;
import com.buswe.moudle.cms.entity.Site;
import com.buswe.moudle.cms.entity.Tags;
import com.buswe.moudle.cms.helper.CmsUtil;
import com.buswe.moudle.cms.service.ArticleService;
import com.buswe.moudle.cms.service.CategoryService;

@Service
@Transactional("jpaTransaction")
public class ArticleServiceImpl
  extends BaseServiceImpl<Article>
  implements ArticleService
{
  @Resource
  private ArticleDao articleDao;
  @Resource
  private CategoryDao catDao;
  @Resource
  private ArticleDataDao articleDataDao;
  @Resource
  private TagsDao tagsDao;
  @Resource
  private CommentDao commentDao;
  @Resource
  CategoryService catService;
  
  public BaseRepository<Article, String> getDao()
  {
    return this.articleDao;
  }
  
  public Article save(Article entity)
  {
    Category cat = (Category)this.catDao.findOne(entity.getCategory().getId());
    entity.setCategory(cat);
    Site site = cat.getSite();
    entity.setSite(site);
    entity.setOutline(CmsUtil.getOutLine(entity.getArticleData().getLobContent()));
    
    entity.getArticleData().setArticle(entity);
    ArticleData data = (ArticleData)this.articleDataDao.save(entity.getArticleData());
    entity.setArticleData(data);
    entity = (Article)this.articleDao.save(entity);
    String tag = entity.getTags();
    List<String> tags = new ArrayList();
    if (tag.contains("，")) {
      tags = Arrays.asList(tag.split("，"));
    } else if (tag.contains(",")) {
      tags = Arrays.asList(tag.split(","));
    }
    for (String taginput : tags)
    {
      Tags tagsData = this.tagsDao.findByTagNameAndSiteId(taginput,entity.getSite().getId());
      if (tagsData == null)
      {
        tagsData = new Tags(taginput);
        tagsData.setPublishCount(Integer.valueOf(1));
        tagsData.setRefCount(Integer.valueOf(1));
        tagsData.setSiteId(site.getId());
        tagsData = (Tags)this.tagsDao.save(tagsData);
      }
      else
      {
        tagsData.setPublishCount(Integer.valueOf(tagsData.getPublishCount().intValue() + 1));
        tagsData.setRefCount(Integer.valueOf(tagsData.getRefCount().intValue() + 1));
        tagsData = (Tags)this.tagsDao.save(tagsData);
      }
    }
    return entity;
  }
  
  public List<Article> queryCatArticle(String catId, Date beginDate, Date endDate)
  {
    return this.articleDao.queryCatArticle(catId, beginDate, endDate);
  }
  
  public List<Tags> getTopTags(Integer num, String siteId)
  {
    return this.tagsDao.findBySiteIdOrderByRefCountDesc(new PageRequest(0, num.intValue()), siteId);
  }
  
  public Page<Article> findCatArticle(String catId, Pageable page)
  {
    return this.articleDao.findByCatId(catId, page);
  }
  
  public Article getArticle(String artId)
  {
    Article article = this.articleDao.getArticle(artId);
    article.getArticleData().getLobContent();
    List<Comment> comment = this.commentDao.findByArticleId(article.getId());
    article.setComments(comment);
    return article;
  }
  
  public Page<Article> findByTags(Tags tags, Pageable page)
  {
	List<Tags>  article= Arrays.asList(tags);
    return this.articleDao.findByTagListInAndStatusOrderByUpdateDateDesc(article, "0",page);
  }
@Override
public Tags getTagbyName(String tagName, String siteId) {
	 
	return tagsDao.findByTagNameAndSiteId(tagName, siteId);
}

@Override
public Page<Article> search(String keyWords, Pageable pageable) {
	
	if(keyWords.startsWith(":index")) //TODO   and must be admin
	{
		articleDao.reindex(articleDao.getEntityManager());
	}
	
	return articleDao.search(articleDao.getEntityManager(),keyWords, pageable);
}
}
