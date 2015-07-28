package com.buswe.moudle.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_recevie_message")
public class RecevieMessage
  extends IdEntity
{
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="messages_id")
  private Messages messages;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="recevie_userid")
  private UserBasic recevieUser;
  @Column(name="hasread", length=1)
  private Boolean hasRead;
  
  public Messages getMessages()
  {
    return this.messages;
  }
  
  public void setMessages(Messages messages)
  {
    this.messages = messages;
  }
  
  public UserBasic getRecevieUser()
  {
    return this.recevieUser;
  }
  
  public void setRecevieUser(UserBasic recevieUser)
  {
    this.recevieUser = recevieUser;
  }
  
  public Boolean getHasRead()
  {
    return this.hasRead;
  }
  
  public void setHasRead(Boolean hasRead)
  {
    this.hasRead = hasRead;
  }
}
