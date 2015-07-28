package com.buswe.moudle.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_attachmentInfo")
public class AttachmentInfo
  extends IdEntity
{
  private static final long serialVersionUID = 2287463837521573424L;
  @Column(name="filename", length=20)
  private String fileName;
  @Column(name="create_userid", length=36)
  private String createUserId;
  @Column(name="createdate")
  private Date createDate;
  @Column(name="size", length=7)
  private Long size;
  @Column(name="filetype", length=5)
  private String fileType;
  @Column(name="store_type", length=1)
  private Integer storeType;
  @Column(name="store_url", length=50)
  private String stroreURL;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="messages_id")
  private Messages messages;
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }
  
  public String getCreateUserId()
  {
    return this.createUserId;
  }
  
  public void setCreateUserId(String createUserId)
  {
    this.createUserId = createUserId;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
  
  public Long getSize()
  {
    return this.size;
  }
  
  public void setSize(Long size)
  {
    this.size = size;
  }
  
  public String getFileType()
  {
    return this.fileType;
  }
  
  public void setFileType(String fileType)
  {
    this.fileType = fileType;
  }
  
  public Integer getStoreType()
  {
    return this.storeType;
  }
  
  public void setStoreType(Integer storeType)
  {
    this.storeType = storeType;
  }
  
  public String getStroreURL()
  {
    return this.stroreURL;
  }
  
  public void setStroreURL(String stroreURL)
  {
    this.stroreURL = stroreURL;
  }
  
  public Messages getMessages()
  {
    return this.messages;
  }
  
  public void setMessages(Messages messages)
  {
    this.messages = messages;
  }
}
