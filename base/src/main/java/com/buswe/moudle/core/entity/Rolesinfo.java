package com.buswe.moudle.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_rolesinfo")
public class Rolesinfo
  extends IdEntity
{
  private static final long serialVersionUID = -4005516268335345213L;
  @Column(name="rolename", length=50)
  private String rolename;
  @ManyToMany(cascade={javax.persistence.CascadeType.REFRESH}, mappedBy="rolesinfos", fetch=FetchType.LAZY)
  private Set<UserBasic> userBasics = new HashSet(0);
  @Column(name="rolesinfo", length=50)
  private String rolesinfo;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="rolesinfo")
  private Set<RolesAuthority> rolesAuthority = new HashSet(0);
  
  public String getRolename()
  {
    return this.rolename;
  }
  
  public Rolesinfo() {}
  
  public Rolesinfo(Object entity, Object[] list)
  {
    this.rolename = "";
  }
  
  public Rolesinfo(Rolesinfo entity, Set<RolesAuthority> list)
  {
    this.rolename = entity.rolename;
    this.id = entity.id;
    this.rolesAuthority = list;
  }
  
  public String getRolesinfo()
  {
    return this.rolesinfo;
  }
  
  public void setRolesinfo(String rolesinfo)
  {
    this.rolesinfo = rolesinfo;
  }
  
  public void setRolename(String rolename)
  {
    this.rolename = rolename;
  }
  
  public Set<UserBasic> getUserBasics()
  {
    return this.userBasics;
  }
  
  public void setUserBasics(Set<UserBasic> userBasics)
  {
    this.userBasics = userBasics;
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
