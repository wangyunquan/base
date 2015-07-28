package com.buswe.moudle.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_report_parameter")
public class ReportParameter
  extends IdEntity
{
  @Column(name="name", length=30)
  private String name;
  @Column(name="resoucename", length=50)
  private String resourceName;
  @Column(name="type", length=3)
  private Integer type;
  @Column(name="fromtype", length=3)
  private Integer fromtype;
  @Column(name="inorout", length=1)
  private Integer inorout;
  @Column(name="value", length=20)
  private String value;
  @Column(name="default_value", length=20)
  private String defaultValue;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="report_id")
  private Report report;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getResourceName()
  {
    return this.resourceName;
  }
  
  public void setResourceName(String resourceName)
  {
    this.resourceName = resourceName;
  }
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
  
  public Integer getFromtype()
  {
    return this.fromtype;
  }
  
  public void setFromtype(Integer fromtype)
  {
    this.fromtype = fromtype;
  }
  
  public Integer getInorout()
  {
    return this.inorout;
  }
  
  public void setInorout(Integer inorout)
  {
    this.inorout = inorout;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public String getDefaultValue()
  {
    return this.defaultValue;
  }
  
  public void setDefaultValue(String defaultValue)
  {
    this.defaultValue = defaultValue;
  }
  
  public Report getReport()
  {
    return this.report;
  }
  
  public void setReport(Report report)
  {
    this.report = report;
  }
}
