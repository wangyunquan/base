package com.buswe.moudle.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_posion")
public class Position
  extends IdEntity
{
  private static final long serialVersionUID = -7271546787472110958L;
  @Column(name="name", length=50)
  private String name;
  @Column(name="info", length=80)
  private String info;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="adminrank_id")
  private AdminRank adminRank;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="position")
  private Set<UserBasic> userBasics = new HashSet();
  
  public Set<UserBasic> getUserBasics()
  {
    return this.userBasics;
  }
  
  public void setUserBasics(Set<UserBasic> userBasics)
  {
    this.userBasics = userBasics;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getInfo()
  {
    return this.info;
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public AdminRank getAdminRank()
  {
    return this.adminRank;
  }
  
  public void setAdminRank(AdminRank adminRank)
  {
    this.adminRank = adminRank;
  }
}
