package com.buswe.moudle.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_report")
public class DynamicReport
  extends IdEntity
{
  private static final long serialVersionUID = 9053362939361457961L;
  private String title;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="dynamicReport")
  private List<DynamicColumn> columns = new ArrayList();
  @Column(name="groups", length=100)
  private String groups;
  @Column(name="subtotals", length=50)
  private String subtotals;
  @Column(name="show_page_number", length=1)
  private boolean showPageNumber;
  @Column(name="sqlstring", length=500)
  private String sqlString;
  @Column(name="sqltype", length=10)
  private String sqlType;
  @Column(name="fileurl", length=50)
  private String fileUrl;
  
  public String getFileUrl()
  {
    return this.fileUrl;
  }
  
  public void setFileUrl(String fileUrl)
  {
    this.fileUrl = fileUrl;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public List<DynamicColumn> getColumns()
  {
    return this.columns;
  }
  
  public void setColumns(List<DynamicColumn> columns)
  {
    this.columns = columns;
  }
  
  public String getGroups()
  {
    return this.groups;
  }
  
  public void setGroups(String groups)
  {
    this.groups = groups;
  }
  
  public String getSubtotals()
  {
    return this.subtotals;
  }
  
  public void setSubtotals(String subtotals)
  {
    this.subtotals = subtotals;
  }
  
  public boolean isShowPageNumber()
  {
    return this.showPageNumber;
  }
  
  public void setShowPageNumber(boolean showPageNumber)
  {
    this.showPageNumber = showPageNumber;
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
}
