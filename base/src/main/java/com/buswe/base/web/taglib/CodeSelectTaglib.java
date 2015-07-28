package com.buswe.base.web.taglib;

import java.io.IOException;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.buswe.base.service.CodeHolder;
import com.buswe.moudle.core.entity.CodeValue;

public class CodeSelectTaglib
  extends TagSupport
{
  private String type;
  private String name;
  private Object value;
  private String css;
  private String ref;
  
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
  
  public String getCss()
  {
    return this.css;
  }
  
  public void setCss(String css)
  {
    this.css = css;
  }
  
  public String getRef()
  {
    return this.ref;
  }
  
  public void setRef(String ref)
  {
    this.ref = ref;
  }
  
  public int doEndTag()
    throws JspException
  {
    JspWriter out = this.pageContext.getOut();
    StringBuffer sbf = new StringBuffer();
    sbf.append("<select name=\"" + this.name + "\" id=\"" + this.name + "\" class=\"form-control");
    if (StringUtils.isNotBlank(this.css)) {
      sbf.append(" " + this.css);
    }
    sbf.append("\">");
    Set<CodeValue> codeValues = CodeHolder.getAllCodeValue(this.type);
    for (CodeValue item : codeValues)
    {
      sbf.append("<option value=\"" + item.getCode() + "\"");
      if ((this.value != null) && (this.value.toString().equals(item.getCode().toString()))) {
        sbf.append(" selected ");
      }
      sbf.append(">" + item.getValue() + "</option>");
    }
    sbf.append("</select>");
    sbf.append("");
    sbf.append("");
    sbf.append("");
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
}
