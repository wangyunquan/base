package com.buswe.moudle.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_userbasic")
public class UserBasic
  extends IdEntity
{
  private static final long serialVersionUID = 5666028763146017893L;
  @Column(name="loginname", length=50)
  private String loginName;
  @Column(name="password", length=50)
  private String password;
  @Column(name="name", length=30)
  private String name;
  @Column(name="email", length=50)
  private String email;
  @Column(name="enabled", length=1)
  private Boolean enabled = Boolean.valueOf(false);
  @Column(name="locked", length=1)
  private Boolean accountNonLocked;
  @Column(name="online", length=1)
  private Boolean isOnline;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="userBasic")
  private Set<Userinfo> userinfos = new HashSet(0);
  @ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY)
  @JoinTable(name="base_user_roles", inverseJoinColumns={@JoinColumn(name="role_id")}, joinColumns={@JoinColumn(name="user_id")})
  private Set<Rolesinfo> rolesinfos = new HashSet(0);
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="position_id")
  private Position position;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="group_id")
  private GroupInfo groupInfo;
  @Transient
  private String[] roleIds;
  
  public String[] getRoleIds()
  {
    return this.roleIds;
  }
  
  public void setRoleIds(String[] roleIds)
  {
    this.roleIds = roleIds;
  }
  
  public UserBasic() {}
  
  public UserBasic(UserBasic user, String[] ids)
  {
    this.id = user.id;
    this.roleIds = ids;
  }
  
  public String getLoginName()
  {
    return this.loginName;
  }
  
  public void setLoginName(String loginName)
  {
    this.loginName = loginName;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public Boolean getEnabled()
  {
    return this.enabled;
  }
  
  public void setEnabled(Boolean enabled)
  {
    this.enabled = enabled;
  }
  
  public Boolean getAccountNonLocked()
  {
    return this.accountNonLocked;
  }
  
  public void setAccountNonLocked(Boolean accountNonLocked)
  {
    this.accountNonLocked = accountNonLocked;
  }
  
  public Boolean getIsOnline()
  {
    return this.isOnline;
  }
  
  public void setIsOnline(Boolean isOnline)
  {
    this.isOnline = isOnline;
  }
  
  public Set<Userinfo> getUserinfos()
  {
    return this.userinfos;
  }
  
  public void setUserinfos(Set<Userinfo> userinfos)
  {
    this.userinfos = userinfos;
  }
  
  public Set<Rolesinfo> getRolesinfos()
  {
    return this.rolesinfos;
  }
  
  public void setRolesinfos(Set<Rolesinfo> rolesinfos)
  {
    this.rolesinfos = rolesinfos;
  }
  
  public Position getPosition()
  {
    return this.position;
  }
  
  public void setPosition(Position position)
  {
    this.position = position;
  }
  
  public GroupInfo getGroupInfo()
  {
    return this.groupInfo;
  }
  
  public void setGroupInfo(GroupInfo groupInfo)
  {
    this.groupInfo = groupInfo;
  }
}
