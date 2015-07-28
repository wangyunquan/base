package com.buswe.base.dao;

public class PropertyFilter
{
  public String fieldName;
  public Object value;
  public MatchType matchType;
  
  public PropertyFilter(String name, Object value)
  {
    this.fieldName = name;
    this.value = value;
    this.matchType = MatchType.EQ;
  }
  
  public PropertyFilter(String name, MatchType matchType, Object value)
  {
    this.fieldName = name;
    this.value = value;
    this.matchType = matchType;
  }
}
