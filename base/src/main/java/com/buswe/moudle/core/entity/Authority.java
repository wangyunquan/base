package com.buswe.moudle.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_authority")
public class Authority
  extends IdEntity
{
  private static final long serialVersionUID = 402510134125468707L;
  @Column(name="name", length=30)
  private String name;
  @Column(name="authinfo", length=30)
  private String authinfo;
  @Column(name="authtype", length=20)
  private String authtype;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="authority")
  private Set<RolesAuthority> rolesAuthority = new HashSet(0);
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getAuthinfo()
  {
    return this.authinfo;
  }
  
  public void setAuthinfo(String authinfo)
  {
    this.authinfo = authinfo;
  }
  
  public String getAuthtype()
  {
    return this.authtype;
  }
  
  public void setAuthtype(String authtype)
  {
    this.authtype = authtype;
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
