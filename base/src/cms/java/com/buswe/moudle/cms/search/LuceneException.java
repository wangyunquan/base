package com.buswe.moudle.cms.search;

import org.springframework.core.NestedRuntimeException;

public class LuceneException
  extends NestedRuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public LuceneException(String msg)
  {
    super(msg);
  }
  
  public LuceneException(String msg, Throwable ex)
  {
    super(msg, ex);
  }
}
