package com.buswe.moudle.core.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_roles_authority")
public class RolesAuthority
  extends IdEntity
{
  private static final long serialVersionUID = -7993761731640759971L;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="rolesinfo_id")
  private Rolesinfo rolesinfo;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="authority_id")
  private Authority authority;
  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name="base_roles_authority_rules", joinColumns={@JoinColumn(name="roles_auth_id")}, inverseJoinColumns={@JoinColumn(name="rules_id")})
  private Set<RulesInfo> rulesInfos;
  
  public Rolesinfo getRolesinfo()
  {
    return this.rolesinfo;
  }
  
  public void setRolesinfo(Rolesinfo rolesinfo)
  {
    this.rolesinfo = rolesinfo;
  }
  
  public Authority getAuthority()
  {
    return this.authority;
  }
  
  public void setAuthority(Authority authority)
  {
    this.authority = authority;
  }
  
  public Set<RulesInfo> getRulesInfos()
  {
    return this.rulesInfos;
  }
  
  public void setRulesInfos(Set<RulesInfo> rulesInfos)
  {
    this.rulesInfos = rulesInfos;
  }
}
