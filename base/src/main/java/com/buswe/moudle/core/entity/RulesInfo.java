package com.buswe.moudle.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_rulesinfo")
public class RulesInfo
  extends IdEntity
{
  @Column(name="info", length=20)
  private String info;
  @Column(name="type", length=10)
  private String type;
  @Column(name="name", length=20)
  private String name;
  @Column(name="value", length=30)
  private String value;
  @Column(name="value_from")
  private String valueFrom;
  private String expression;
  
  public String getValueFrom()
  {
    return this.valueFrom;
  }
  
  public void setValueFrom(String valueFrom)
  {
    this.valueFrom = valueFrom;
  }
  
  public String getExpression()
  {
    return this.expression;
  }
  
  public void setExpression(String expression)
  {
    this.expression = expression;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  @ManyToMany(cascade={javax.persistence.CascadeType.REFRESH}, mappedBy="rulesInfos", fetch=FetchType.LAZY)
  private Set<RolesAuthority> rolesAuthority = new HashSet(0);
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getInfo()
  {
    return this.info;
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Set<RolesAuthority> getRolesAuthority()
  {
    return this.rolesAuthority;
  }
  
  public void setRolesAuthority(Set<RolesAuthority> rolesAuthority)
  {
    this.rolesAuthority = rolesAuthority;
  }
}
