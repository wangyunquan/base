package com.buswe.moudle.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_codevalue")
public class CodeValue
  extends IdEntity
{
  private static final long serialVersionUID = 2239655854863225273L;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="codetype_id")
  private CodeType codeType;
  @Column(name="code", length=50)
  private String code;
  @Column(name="stringvalue", length=50)
  private String stringValue;
  @Column(name="cnvalue", length=30)
  private String cnValue;
  @Column(name="enable", length=1)
  private Boolean enable;
  @Transient
  private Object value;
  
  public Boolean getEnable()
  {
    return this.enable;
  }
  
  public void setEnable(Boolean enable)
  {
    this.enable = enable;
  }
  
  public CodeType getCodeType()
  {
    return this.codeType;
  }
  
  public void setCodeType(CodeType codeType)
  {
    this.codeType = codeType;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getStringValue()
  {
    return this.stringValue;
  }
  
  public void setStringValue(String stringValue)
  {
    this.stringValue = stringValue;
  }
  
  public String getCnValue()
  {
    return this.cnValue;
  }
  
  public void setCnValue(String cnValue)
  {
    this.cnValue = cnValue;
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }
}
