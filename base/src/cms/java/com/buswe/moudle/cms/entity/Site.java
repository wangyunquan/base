package com.buswe.moudle.cms.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="cms_site")
public class Site
  extends IdEntity
{
  private static final long serialVersionUID = 1L;
  private String name;
  private String title;
  private String path;
  private String desciption;
  private String keywords;
  private String theme;
  private String copyright;
  private Integer delFlag;
  
  public Site()
  {
    this.delFlag = Integer.valueOf(1);
  }
  
  public String getPath()
  {
    return this.path;
  }
  
  public void setPath(String path)
  {
    this.path = path;
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
  
  public String getDesciption()
  {
    return this.desciption;
  }
  
  public void setDesciption(String desciption)
  {
    this.desciption = desciption;
  }
  
  public String getKeywords()
  {
    return this.keywords;
  }
  
  public void setKeywords(String keywords)
  {
    this.keywords = keywords;
  }
  
  public String getTheme()
  {
    return this.theme;
  }
  
  public void setTheme(String theme)
  {
    this.theme = theme;
  }
  
  public String getCopyright()
  {
    return this.copyright;
  }
  
  public void setCopyright(String copyright)
  {
    this.copyright = copyright;
  }
  
  public Integer getDelFlag()
  {
    return this.delFlag;
  }
  
  public void setDelFlag(Integer delFlag)
  {
    this.delFlag = delFlag;
  }
  
  @Transient
  public static boolean isDefault(Long id)
  {
    return (id != null) && (id.equals(Long.valueOf(1L)));
  }
}
