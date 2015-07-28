package com.buswe.moudle.cms.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="cms_tags")
public class Tags
  extends IdEntity
{
  @ManyToMany
  @JoinTable(name="cms_articletag", joinColumns={@javax.persistence.JoinColumn(name="tags_id")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="article_id")})
  private List<Article> articles;
  private String tagName;
  private Integer publishCount;
  private Integer refCount;
  private String siteId;
  
  public String getTagName()
  {
    return this.tagName;
  }
  
  public Tags() {}
  
  public List<Article> getArticles()
  {
    return this.articles;
  }
  
  public void setArticles(List<Article> articles)
  {
    this.articles = articles;
  }
  
  public String getSiteId()
  {
    return this.siteId;
  }
  
  public void setSiteId(String siteId)
  {
    this.siteId = siteId;
  }
  
  public Tags(String tagName)
  {
    this.tagName = tagName;
  }
  
  public void setTagName(String tagName)
  {
    this.tagName = tagName;
  }
  
  public Integer getPublishCount()
  {
    return this.publishCount;
  }
  
  public void setPublishCount(Integer publishCount)
  {
    this.publishCount = publishCount;
  }
  
  public Integer getRefCount()
  {
    return this.refCount;
  }
  
  public void setRefCount(Integer refCount)
  {
    this.refCount = refCount;
  }
}
