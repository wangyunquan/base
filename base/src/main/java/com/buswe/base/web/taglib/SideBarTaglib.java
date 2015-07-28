package com.buswe.base.web.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.buswe.base.web.menu.MenuHolder;
import com.buswe.moudle.core.entity.Menu;

public class SideBarTaglib
  extends TagSupport
{
  public int doEndTag()
    throws JspException
  {
    HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
    
    String path = request
      .getAttribute(
      "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern").toString();
    StringBuffer sbd = new StringBuffer();
    String[] pathArray = path.split("/");
    sbd.append("<div class=\"list-group\">");
    List<Menu> allMenu = MenuHolder.getAllMenu();
    for (Menu top : allMenu) {
      if (top.getAuthority().equals(pathArray[1])) {
        for (Menu sec : top.getChildren()) {
          if (sec.getUrl().equals("/" + pathArray[1] + "/" + pathArray[2] + "/")) {
            for (Menu three : sec.getChildren())
            {
              sbd.append("<a href=\"");
              sbd.append(request.getContextPath()+ three.getUrl());
              sbd.append("\" class=\"list-group-item");
              if ((pathArray.length > 3) && (("/" + pathArray[1] + "/" + pathArray[2] + "/" + pathArray[3] + "/").equals(three.getUrl()))) {
                sbd.append(" active");
              }
              sbd.append("\">");
              sbd.append(three.getName());
              sbd.append("</a>");
            }
          }
        }
      }
    }
    sbd.append("\t</div>");
    try
    {
      this.pageContext.getOut().print(sbd.toString());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return 6;
  }
}
