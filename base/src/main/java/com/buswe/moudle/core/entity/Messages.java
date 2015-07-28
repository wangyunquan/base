package com.buswe.moudle.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_message")
public class Messages
  extends IdEntity
{
  @Column(name="title", length=100)
  private String title;
  @Column(name="recevie_userids", length=100)
  private String recevieUserIds;
  @Column(name="recevie_usernames", length=200)
  private String recevieUsernames;
  @Column(name="sendDate")
  private Date sendDate;
  @Column(name="success", length=1)
  private Boolean success;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="send_userid")
  private UserBasic sendUser;
  @Column(name="senddelete", length=1)
  private Boolean senddelete;
  @Column(name="content", length=500)
  private String content;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="messages")
  private List<AttachmentInfo> attachmentInfos = new ArrayList();
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="messages")
  private List<RecevieMessage> recevieMessages = new ArrayList();
  
  public List<RecevieMessage> getRecevieMessages()
  {
    return this.recevieMessages;
  }
  
  public void setRecevieMessages(List<RecevieMessage> recevieMessages)
  {
    this.recevieMessages = recevieMessages;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getRecevieUserIds()
  {
    return this.recevieUserIds;
  }
  
  public void setRecevieUserIds(String recevieUserIds)
  {
    this.recevieUserIds = recevieUserIds;
  }
  
  public String getRecevieUsernames()
  {
    return this.recevieUsernames;
  }
  
  public void setRecevieUsernames(String recevieUsernames)
  {
    this.recevieUsernames = recevieUsernames;
  }
  
  public Date getSendDate()
  {
    return this.sendDate;
  }
  
  public void setSendDate(Date sendDate)
  {
    this.sendDate = sendDate;
  }
  
  public Boolean getSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(Boolean success)
  {
    this.success = success;
  }
  
  public UserBasic getSendUser()
  {
    return this.sendUser;
  }
  
  public void setSendUser(UserBasic sendUser)
  {
    this.sendUser = sendUser;
  }
  
  public Boolean getSenddelete()
  {
    return this.senddelete;
  }
  
  public void setSenddelete(Boolean senddelete)
  {
    this.senddelete = senddelete;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public List<AttachmentInfo> getAttachmentInfos()
  {
    return this.attachmentInfos;
  }
  
  public void setAttachmentInfos(List<AttachmentInfo> attachmentInfos)
  {
    this.attachmentInfos = attachmentInfos;
  }
}
