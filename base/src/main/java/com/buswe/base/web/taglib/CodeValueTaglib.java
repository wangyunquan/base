package com.buswe.base.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.buswe.base.service.CodeHolder;

public class CodeValueTaglib
  extends TagSupport
{
  private String type;
  private Object key;
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public void setKey(Object key)
  {
    this.key = key;
  }
  
  public int doEndTag()
    throws JspException
  {
    JspWriter out = this.pageContext.getOut();
    if (this.key != null) {
      try
      {
        Object codeValue = CodeHolder.getCodeValue(this.type, this.key.toString());
        if (codeValue == null) {
          throw new JspException("码表未被初始化");
        }
        out.print(codeValue);
      }
      catch (IOException localIOException) {}
    }
    return 6;
  }
}
