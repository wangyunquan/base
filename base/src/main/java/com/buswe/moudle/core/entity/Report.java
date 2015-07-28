package com.buswe.moudle.core.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_report")
public class Report
  extends IdEntity
{
  @Column(name="name", length=50)
  private String name;
  @Column(name="templatepath", length=100)
  private String templatePath;
  @Column(name="templatesrc", length=100)
  private String templateSrc;
  @Column(name="sqlstring", length=500)
  private String sqlString;
  @Column(name="sqltype", length=10)
  private String sqlType;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="report")
  private List<ReportParameter> reportParameters;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTemplatePath()
  {
    return this.templatePath;
  }
  
  public void setTemplatePath(String templatePath)
  {
    this.templatePath = templatePath;
  }
  
  public String getTemplateSrc()
  {
    return this.templateSrc;
  }
  
  public void setTemplateSrc(String templateSrc)
  {
    this.templateSrc = templateSrc;
  }
  
  public String getSqlString()
  {
    return this.sqlString;
  }
  
  public void setSqlString(String sqlString)
  {
    this.sqlString = sqlString;
  }
  
  public String getSqlType()
  {
    return this.sqlType;
  }
  
  public void setSqlType(String sqlType)
  {
    this.sqlType = sqlType;
  }
  
  public List<ReportParameter> getReportParameters()
  {
    return this.reportParameters;
  }
  
  public void setReportParameters(List<ReportParameter> reportParameters)
  {
    this.reportParameters = reportParameters;
  }
}
