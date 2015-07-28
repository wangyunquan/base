package com.buswe.moudle.cms.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;
import com.buswe.moudle.core.entity.UserBasic;

@Entity
@Table(name="cms_comment")
public class Comment
  extends IdEntity
{
  private static final long serialVersionUID = 1L;
  private String module;
  private Long contentId;
  private String title;
  private String content;
  private String name;
  private String ip;
  private Date createDate;
  private UserBasic auditUser;
  private Date auditDate;
  private Integer status;
  @ManyToOne
  @JoinColumn(name="article_id")
  private Article article;
  
  public String getModule()
  {
    return this.module;
  }
  
  public void setModule(String module)
  {
    this.module = module;
  }
  
  public Long getContentId()
  {
    return this.contentId;
  }
  
  public void setContentId(Long contentId)
  {
    this.contentId = contentId;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
  
  public UserBasic getAuditUser()
  {
    return this.auditUser;
  }
  
  public void setAuditUser(UserBasic auditUser)
  {
    this.auditUser = auditUser;
  }
  
  public Date getAuditDate()
  {
    return this.auditDate;
  }
  
  public void setAuditDate(Date auditDate)
  {
    this.auditDate = auditDate;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public Article getArticle()
  {
    return this.article;
  }
  
  public void setArticle(Article article)
  {
    this.article = article;
  }
}
