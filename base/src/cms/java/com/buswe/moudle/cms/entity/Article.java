package com.buswe.moudle.cms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import com.buswe.base.domain.IdEntity;
import com.buswe.moudle.core.entity.UserBasic;

@Entity
@Table(name="cms_article")
@Indexed
public class Article
  extends IdEntity
{
  private static final long serialVersionUID = 1L;
  @ManyToOne
  @JoinColumn(name="category_id")
  @NotFound(action=NotFoundAction.IGNORE)
  private Category category;
  @ManyToOne
  @JoinColumn(name="user_id")
  @NotFound(action=NotFoundAction.IGNORE)
  private UserBasic user;
  @ManyToOne
  @JoinColumn(name="site_id")
  @NotFound(action=NotFoundAction.IGNORE)
  private Site site;
  @OneToMany(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY, mappedBy="article")
  private List<Comment> comments = new ArrayList();
  @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
  private String title;
  private String color;
  private String thumb;
  private String keywords;
  private String desciption;
  private String status;
  private Integer weight = Integer.valueOf(0);
  private Integer hits;
  private String posid;
  private Date inputDate;
  @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES)
  @DateBridge(resolution=Resolution.DAY)
  private Date updateDate;
  private String content;
  private String copyfrom;
  private String allowComment;
  private String templates;
  private String tags;
  @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
  private String outline;
  @ManyToMany
  @JoinTable(name="cms_articletag", joinColumns={@JoinColumn(name="article_id")}, inverseJoinColumns={@JoinColumn(name="tags_id")})
  @IndexedEmbedded
  private List<Tags> tagList;
  @OneToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.REMOVE}, mappedBy="article")
  @IndexedEmbedded
  private ArticleData articleData;
  
  @DocumentId  //索引ID
  public String getArticleId()
  {
	  
	  return this.id;
  }
  
  public List<Tags> getTagList()
  {
    return this.tagList;
  }
  
  public void setTagList(List<Tags> tagList)
  {
    this.tagList = tagList;
  }
  
  public List<Comment> getComments()
  {
    return this.comments;
  }
  
  public void setComments(List<Comment> comments)
  {
    this.comments = comments;
  }
  
  public Site getSite()
  {
    return this.site;
  }
  
  public void setSite(Site site)
  {
    this.site = site;
  }
  
  public String getOutline()
  {
    return this.outline;
  }
  
  public void setOutline(String outline)
  {
    this.outline = outline;
  }
  
  public String getTags()
  {
    return this.tags;
  }
  
  public void setTags(String tags)
  {
    this.tags = tags;
  }
  
  public Article() {}
  
  public Article(String id, String userId, String userName, String title, String desciption, Date updateDate)
  {
    UserBasic user = new UserBasic();
    user.setId(userId);
    user.setName(userName);
    setUser(user);
    setTitle(title);
    setDesciption(desciption);
    setUpdateDate(updateDate);
  }
  
  public String getTemplates()
  {
    return this.templates;
  }
  
  public void setTemplates(String templates)
  {
    this.templates = templates;
  }
  
  public Category getCategory()
  {
    return this.category;
  }
  
  public void setCategory(Category category)
  {
    this.category = category;
  }
  
  public UserBasic getUser()
  {
    return this.user;
  }
  
  public void setUser(UserBasic user)
  {
    this.user = user;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getColor()
  {
    return this.color;
  }
  
  public void setColor(String color)
  {
    this.color = color;
  }
  
  public String getThumb()
  {
    return this.thumb;
  }
  
  public void setThumb(String thumb)
  {
    this.thumb = thumb;
  }
  
  public String getKeywords()
  {
    return this.keywords;
  }
  
  public void setKeywords(String keywords)
  {
    this.keywords = keywords;
  }
  
  public String getDesciption()
  {
    return this.desciption;
  }
  
  public void setDesciption(String desciption)
  {
    this.desciption = desciption;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Integer getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(Integer weight)
  {
    this.weight = weight;
  }
  
  public Integer getHits()
  {
    return this.hits;
  }
  
  public void setHits(Integer hits)
  {
    this.hits = hits;
  }
  
  public String getPosid()
  {
    return this.posid;
  }
  
  public void setPosid(String posid)
  {
    this.posid = posid;
  }
  
  public Date getInputDate()
  {
    return this.inputDate;
  }
  
  public void setInputDate(Date inputDate)
  {
    this.inputDate = inputDate;
  }
  
  public Date getUpdateDate()
  {
    return this.updateDate;
  }
  
  public void setUpdateDate(Date updateDate)
  {
    this.updateDate = updateDate;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getCopyfrom()
  {
    return this.copyfrom;
  }
  
  public void setCopyfrom(String copyfrom)
  {
    this.copyfrom = copyfrom;
  }
  
  public String getAllowComment()
  {
    return this.allowComment;
  }
  
  public void setAllowComment(String allowComment)
  {
    this.allowComment = allowComment;
  }
  
  public ArticleData getArticleData()
  {
    return this.articleData;
  }
  
  public void setArticleData(ArticleData articleData)
  {
    this.articleData = articleData;
  }
}
