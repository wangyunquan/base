package com.buswe.moudle.core.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_groupinfo")
public class GroupInfo
  extends IdEntity
{
  private static final long serialVersionUID = 8369539425609344086L;
  @Column(name="groupname", length=50)
  private String groupName;
  @Column(name="groupinfo", length=50)
  private String groupInfo;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="parent_id")
  private GroupInfo parent;
  @Column(name="grouptype", length=2)
  private Integer groupType;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="parent")
  private List<GroupInfo> childs;
  
  public Integer getGroupType()
  {
    return this.groupType;
  }
  
  public void setGroupType(Integer groupType)
  {
    this.groupType = groupType;
  }
  
  public GroupInfo getParent()
  {
    return this.parent;
  }
  
  public void setParent(GroupInfo parent)
  {
    this.parent = parent;
  }
  
  public List<GroupInfo> getChilds()
  {
    return this.childs;
  }
  
  public void setChilds(List<GroupInfo> childs)
  {
    this.childs = childs;
  }
  
  public String getGroupName()
  {
    return this.groupName;
  }
  
  public void setGroupName(String groupName)
  {
    this.groupName = groupName;
  }
  
  public String getGroupInfo()
  {
    return this.groupInfo;
  }
  
  public void setGroupInfo(String groupInfo)
  {
    this.groupInfo = groupInfo;
  }
}
