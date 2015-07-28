package com.buswe.base.web.taglib;

import java.io.IOException;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.buswe.base.service.CodeHolder;
import com.buswe.moudle.core.entity.CodeValue;

public class CodeRadioTaglib
  extends TagSupport
{
  private String type;
  private String name;
  private Object value;
  
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
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }
  
  public int doEndTag()
    throws JspException
  {
    JspWriter out = this.pageContext.getOut();
    StringBuffer sbf = new StringBuffer();
    Set<CodeValue> codeValues = CodeHolder.getAllCodeValue(this.type);
    for (CodeValue codeValue : codeValues)
    {
      sbf.append("<label class=\"radio-inline\">");
      sbf.append("<input  name=\"" + this.name);
      sbf.append("\" type=\"radio\"  value=\"" + codeValue.getCode() + "\"");
      if ((this.value != null) && (codeValue.getCode().equals(this.value.toString()))) {
        sbf.append(" checked ");
      }
      sbf.append("/>");
      sbf.append(codeValue.getValue());
      sbf.append("</label>");
    }
    try
    {
      out.print(sbf);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return 6;
  }
  
  public static void main(String[] args) {}
}
