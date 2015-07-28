package com.buswe.base.constants;

import java.util.Date;

public enum ClassType
{
  string(String.class),  integer(Integer.class),  date(Date.class),  doubles(Double.class);
  
  private Class<?> clazz;
  
  private ClassType(Class<?> clazz)
  {
    this.clazz = clazz;
  }
  
  public Class<?> getValue()
  {
    return this.clazz;
  }
}
