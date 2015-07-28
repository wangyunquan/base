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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_codetype")
public class CodeType
  extends IdEntity
{
  private static final long serialVersionUID = 3302072285582614068L;
  @Column(name="type", length=30)
  @NotNull
  @Size(min=1, max=25)
  private String type;
  @Column(name="name", length=50)
  @Size(min=1, max=20)
  private String name;
  @Column(name="valuetype", length=20)
  @Size(min=1, max=20)
  private String valueType;
  @Column(name="editable", length=1)
  private Boolean editable;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="codeType")
  private Set<CodeValue> codeValues = new HashSet();
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="parent_id")
  private CodeType parent;
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="parent")
  private Set<CodeType> children = new HashSet();
  
  public CodeType getParent()
  {
    return this.parent;
  }
  
  public void setParent(CodeType parent)
  {
    this.parent = parent;
  }
  
  public Set<CodeType> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(Set<CodeType> children)
  {
    this.children = children;
  }
  
  public Boolean getEditable()
  {
    return this.editable;
  }
  
  public void setEditable(Boolean editable)
  {
    this.editable = editable;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getValueType()
  {
    return this.valueType;
  }
  
  public void setValueType(String valueType)
  {
    this.valueType = valueType;
  }
  
  public Set<CodeValue> getCodeValues()
  {
    return this.codeValues;
  }
  
  public void setCodeValues(Set<CodeValue> codeValues)
  {
    this.codeValues = codeValues;
  }
}
