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
@Table(name="base_adminrank")
public class AdminRank
  extends IdEntity
{
  private static final long serialVersionUID = -5680784590019343239L;
  @Column(name="name", length=20)
  private String name;
  @Column(name="info", length=50)
  private String info;
  @Column(name="RANK", length=1)
  private Byte rank;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="adminRank")
  private Set<Position> positions = new HashSet();
  
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
  
  public Byte getRank()
  {
    return this.rank;
  }
  
  public void setRank(Byte rank)
  {
    this.rank = rank;
  }
  
  public Set<Position> getPositions()
  {
    return this.positions;
  }
  
  public void setPositions(Set<Position> positions)
  {
    this.positions = positions;
  }
}
