package com.buswe.base.web.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.data.domain.Page;

import com.buswe.base.web.WebHelper;

public class PageTaglib
  extends TagSupport
{
  private Integer pageCount = Integer.valueOf(10);
  private Page page;
  private String jumpFunction;
  
  public String getJumpFunction()
  {
    return this.jumpFunction;
  }
  
  public void setJumpFunction(String jumpFunction)
  {
    this.jumpFunction = jumpFunction;
  }
  
  public Page getPage()
  {
    return this.page;
  }
  
  public void setPage(Page page)
  {
    this.page = page;
  }
  
  public int doEndTag()
    throws JspException
  {
    JspWriter out = this.pageContext.getOut();
    StringBuffer sbf = new StringBuffer();
    if (this.jumpFunction == null) {
      this.jumpFunction = "pageJump";
    }
    if (this.page != null)
    {
      int pageNumber = this.page.getNumber() + 1;
      sbf.append(" <div style=\" text-align: right;\"><ul class=\"pagination page-top\" >");
      sbf.append("<li><a>共有记录");
      sbf.append(this.page.getTotalElements());
      sbf.append("条  当前显示");
      sbf.append(pageNumber);
      sbf.append("页");
      sbf.append(" 每页");
      sbf.append(this.page.getSize());
      sbf.append("条 ");
      sbf.append("</a></li>");
      

      List<Long> pageList = WebHelper.getPageSlider(this.pageCount.intValue(), pageNumber, this.page.getTotalPages());
      if (pageNumber > 1) {
        sbf.append("<li> <a href=\"javascript:" + this.jumpFunction + "(" + (pageNumber - 1) + ");\">&laquo;</a></li>");
      } else {
        sbf.append("<li class=\"disabled\"><a href=\"#\">&laquo;</a></li> ");
      }
      for (Long item : pageList) {
        if (item.intValue() == pageNumber) {
          sbf.append("<li class=\"active\"><a href=\"#\">" + item + " <span class=\"sr-only\">(current)</span></a></li>");
        } else {
          sbf.append("  <li><a href=\"javascript:" + this.jumpFunction + "(" + item + ");\">" + item + "</a></li>");
        }
      }
      if ((pageList.size() > 0) && (pageNumber != this.page.getTotalPages()) && (((Long)pageList.get(pageList.size() - 1)).longValue() <= this.page.getTotalPages())) {
        sbf.append("  <li><a href=\"javascript:" + this.jumpFunction + "(" + (pageNumber + 1) + ");\">&raquo;</a></li>");
      } else {
        sbf.append("<li class=\"disabled\"><a href=\"#\">&raquo;</a></li> ");
      }
      sbf.append("  </ul></div> ");
      try
      {
        out.print(sbf.toString());
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return 6;
  }
}
