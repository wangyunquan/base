package com.buswe.base.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.buswe.moudle.core.entity.RulesInfo;

public class UserDetail
  extends User
{
  private static final long serialVersionUID = 8245612885796640999L;
  private String userId;
  private List<String> roleId;
  private String groupId;
  private String groupTypeId;
  private Set<RulesInfo> rulesInfo;
  
  public Set<RulesInfo> getRulesInfo()
  {
    return this.rulesInfo;
  }
  
  public void setRulesInfo(Set<RulesInfo> rulesInfo)
  {
    this.rulesInfo = rulesInfo;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public List<String> getRoleId()
  {
    return this.roleId;
  }
  
  public void setRoleId(List<String> roleId)
  {
    this.roleId = roleId;
  }
  
  public String getGroupId()
  {
    return this.groupId;
  }
  
  public void setGroupId(String groupId)
  {
    this.groupId = groupId;
  }
  
  public String getGroupTypeId()
  {
    return this.groupTypeId;
  }
  
  public void setGroupTypeId(String groupTypeId)
  {
    this.groupTypeId = groupTypeId;
  }
  
  public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities)
  {
    super(username, password, authorities);
  }
  
  public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
  {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }
}
