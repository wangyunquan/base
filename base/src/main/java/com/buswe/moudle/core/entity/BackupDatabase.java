package com.buswe.moudle.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_backupdata")
public class BackupDatabase
  extends IdEntity
{
  @Column(name="bakname", length=30)
  private String backName;
  @Column(name="filepath", length=100)
  private String filepath;
  @Column(name="filename", length=30)
  private String fileName;
  @Column(name="filesize", length=7)
  private Integer filesize;
  @Column(name="success", length=1)
  private Boolean success;
  @Column(name="creattime")
  private Date createTime;
  @Column(name="userid", length=36)
  private String userId;
  @Column(name="ignortables", length=255)
  private String ignorTables;
  
  public String getBackName()
  {
    return this.backName;
  }
  
  public void setBackName(String backName)
  {
    this.backName = backName;
  }
  
  public String getFilepath()
  {
    return this.filepath;
  }
  
  public void setFilepath(String filepath)
  {
    this.filepath = filepath;
  }
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }
  
  public Integer getFilesize()
  {
    return this.filesize;
  }
  
  public void setFilesize(Integer filesize)
  {
    this.filesize = filesize;
  }
  
  public Boolean getSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(Boolean success)
  {
    this.success = success;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getIgnorTables()
  {
    return this.ignorTables;
  }
  
  public void setIgnorTables(String ignorTables)
  {
    this.ignorTables = ignorTables;
  }
}
