package com.buswe.base.web.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.buswe.base.web.menu.MenuHolder;
import com.buswe.moudle.core.entity.Menu;

public class TopMenuTag
  extends TagSupport
{
  public int doEndTag()
    throws JspException
  {
    HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
    StringBuffer sbf = new StringBuffer();
    List<Menu> allMenu = MenuHolder.getAllMenu();
    for (Menu top : allMenu)
    {
      sbf.append("<li class=\"dropdown\">");
      String topUrl = top.getUrl();
      sbf.append("<a href=\"");
      if (StringUtils.isNotBlank(topUrl)) {
        sbf.append("/" + request.getContextPath() +  topUrl);
      } else {
        sbf.append("#");
      }
      sbf.append("\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">");
      sbf.append(top.getName());
      sbf.append("<b class=\"caret\"></b>");
      sbf.append("<ul class=\"dropdown-menu\">");
      for (Menu sec : top.getChildren())
      {
        String secUrl = sec.getUrl();
        sbf.append("<li><a href=\"");
        if (StringUtils.isNotBlank(secUrl)) {
          sbf.append(request.getContextPath() + secUrl);
        } else {
          sbf.append("#");
        }
        sbf.append("\">");
        sbf.append(sec.getName());
        sbf.append("</a></li>");
      }
      sbf.append(" </ul> </li>");
    }
    try
    {
      this.pageContext.getOut().print(sbf.toString());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return 6;
  }
}
