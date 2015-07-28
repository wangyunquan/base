package com.buswe.moudle.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_systemconfig")
public class SystemConfig
  extends IdEntity
{
  @Column(name="description", length=50)
  private String description;
  @Column(name="propertyname", length=50)
  private String propertyName;
  @Column(name="propertyvalue", length=50)
  private String propertyValue;
  @Column(name="privilage", length=1)
  private Integer privilage;
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public String getPropertyName()
  {
    return this.propertyName;
  }
  
  public void setPropertyName(String propertyName)
  {
    this.propertyName = propertyName;
  }
  
  public String getPropertyValue()
  {
    return this.propertyValue;
  }
  
  public void setPropertyValue(String propertyValue)
  {
    this.propertyValue = propertyValue;
  }
  
  public Integer getPrivilage()
  {
    return this.privilage;
  }
  
  public void setPrivilage(Integer privilage)
  {
    this.privilage = privilage;
  }
}
