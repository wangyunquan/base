package com.buswe.moudle.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_menu")
public class Statistic
  extends IdEntity
{
  private String type;
  private String value;
  private String defaultvalue;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public String getDefaultvalue()
  {
    return this.defaultvalue;
  }
  
  public void setDefaultvalue(String defaultvalue)
  {
    this.defaultvalue = defaultvalue;
  }
}
