package com.buswe.moudle.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_loginfo")
public class Loginfo
  extends IdEntity
{
  @Column(name="thread", length=20)
  private String threadName;
  @Column(name="logger", length=100)
  private String loggerName;
  @Column(name="logtime")
  private Date logTime;
  @Column(name="level", length=10)
  private String level;
  @Column(name="message", length=255)
  private String message;
  
  public String getThreadName()
  {
    return this.threadName;
  }
  
  public void setThreadName(String threadName)
  {
    this.threadName = threadName;
  }
  
  public String getLoggerName()
  {
    return this.loggerName;
  }
  
  public void setLoggerName(String loggerName)
  {
    this.loggerName = loggerName;
  }
  
  public Date getLogTime()
  {
    return this.logTime;
  }
  
  public void setLogTime(Date logTime)
  {
    this.logTime = logTime;
  }
  
  public String getLevel()
  {
    return this.level;
  }
  
  public void setLevel(String level)
  {
    this.level = level;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
}
