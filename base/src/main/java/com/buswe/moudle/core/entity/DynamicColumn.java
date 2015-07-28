package com.buswe.moudle.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_report_column")
public class DynamicColumn
  extends IdEntity
{
  @Column(name="name", length=50)
  private String name;
  @Column(name="title", length=50)
  private String title;
  @Column(name="type", length=20)
  private String type;
  @Column(name="patter", length=30)
  private String pattern;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="report_id")
  private DynamicReport dynamicReport;
  
  public DynamicColumn() {}
  
  public DynamicColumn(String title, String name, String type)
  {
    this.name = name;
    this.type = type;
    this.title = title;
  }
  
  public DynamicReport getDynamicReport()
  {
    return this.dynamicReport;
  }
  
  public void setDynamicReport(DynamicReport dynamicReport)
  {
    this.dynamicReport = dynamicReport;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getPattern()
  {
    return this.pattern;
  }
  
  public void setPattern(String pattern)
  {
    this.pattern = pattern;
  }
}
