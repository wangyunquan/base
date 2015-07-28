package com.buswe.moudle.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_operation")
public class Operation
  extends IdEntity
{
  @Column(name="username", length=50)
  private String userName;
  @Column(name="creattime")
  private Date createTime;
  @Column(name="model", length=30)
  private String model;
  @Column(name="operation", length=50)
  private String operation;
  @Column(name="parameter", length=50)
  private String parameter;
  @Column(name="success", length=1)
  private Boolean success;
  @Column(name="info", length=200)
  private String info;
  @Column(name="ip", length=30)
  private String ipAddr;
  
  public String getIpAddr()
  {
    return this.ipAddr;
  }
  
  public void setIpAddr(String ipAddr)
  {
    this.ipAddr = ipAddr;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getModel()
  {
    return this.model;
  }
  
  public void setModel(String model)
  {
    this.model = model;
  }
  
  public String getOperation()
  {
    return this.operation;
  }
  
  public void setOperation(String operation)
  {
    this.operation = operation;
  }
  
  public String getParameter()
  {
    return this.parameter;
  }
  
  public void setParameter(String parameter)
  {
    this.parameter = parameter;
  }
  
  public Boolean getSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(Boolean success)
  {
    this.success = success;
  }
  
  public String getInfo()
  {
    return this.info;
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public String toString()
  {
    return 
    

      "Operation [userName=" + this.userName + ", createTime=" + this.createTime + ", model=" + this.model + ", operation=" + this.operation + ", parameter=" + this.parameter + ", success=" + this.success + ", info=" + this.info + ", ipAddr=" + this.ipAddr + "]";
  }
}
