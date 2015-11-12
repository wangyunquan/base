package com.buswe.moudle.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_menu")
public class Menu
  extends IdEntity
{
  private static final long serialVersionUID = 5116227047728493994L;
  Integer level;
  private String authority;
  private String name;
  private String htmlId;
  private String url;
  private Integer orderNo;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="parent_id")
  private Menu parent;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="parent")
  @OrderBy("orderNo DESC")
  private Set<Menu> children = new HashSet<Menu>();
  
  public Integer getLevel()
  {
    return this.level;
  }
  
  public void setLevel(Integer level)
  {
    this.level = level;
  }
  
  public String getAuthority()
  {
    return this.authority;
  }
  
  public void setAuthority(String authority)
  {
    this.authority = authority;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getHtmlId()
  {
    return this.htmlId;
  }
  
  public void setHtmlId(String htmlId)
  {
    this.htmlId = htmlId;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public Integer getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(Integer orderNo)
  {
    this.orderNo = orderNo;
  }
  
  public Menu getParent()
  {
    return this.parent;
  }
  
  public void setParent(Menu parent)
  {
    this.parent = parent;
  }
  
  public Set<Menu> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(Set<Menu> children)
  {
    this.children = children;
  }
}
