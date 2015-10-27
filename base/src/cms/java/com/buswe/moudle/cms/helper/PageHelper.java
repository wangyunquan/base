package com.buswe.moudle.cms.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.buswe.moudle.cms.CmsConstants;
import com.buswe.moudle.cms.entity.Article;

public class PageHelper
{
  public static String getTextWithoutPageBreak(String content)
  {
    if (StringUtils.isBlank(content)) {
      return content;
    }
    StringBuilder sb = new StringBuilder();
    int start = 0;
    for (;;)
    {
      int end = content.indexOf(CmsConstants.PAGEBREAK_OPEN, start);
      if (end != -1)
      {
        sb.append(content, start, end);
        start = end + CmsConstants.PAGEBREAK_OPEN.length();
      }
      else
      {
        sb.append(content, start, content.length());
        break;
      }
      end = content.indexOf(CmsConstants.PAGEBREAK_CLOSE, start);
      if (end == -1) {
        break;
      }
      start = end + CmsConstants.PAGEBREAK_CLOSE.length();
    }
    int end;
    return sb.toString();
  }
  
  public static List<TitleText> getContent(Article info)
  {
    List<TitleText> list = new ArrayList();
    String t = info.getLobContent();
    String ftt = info.getTitle();
    String title = ftt;
    if (t != null)
    {
      int start = 0;
      for (;;)
      {
        int end = t.indexOf(CmsConstants.PAGEBREAK_OPEN, start);
        String text;
        if (end != -1)
        {
           text = t.substring(start, end);
          start = end + CmsConstants.PAGEBREAK_OPEN.length();
        }
        else
        {
          text = t.substring(start);
        }
        list.add(new TitleText(title, text));
        if (end == -1) {
          return list;
        }
        end = t.indexOf(CmsConstants.PAGEBREAK_CLOSE, start);
        if (end == -1) {
          break;
        }
        title = t.substring(start, end);
        if (StringUtils.isBlank(title)) {
          title = ftt;
        }
        start = end + CmsConstants.PAGEBREAK_CLOSE.length();
      }
      title = t.substring(start);
      if (StringUtils.isBlank(title)) {
        title = ftt;
      }
    }
    else
    {
      list.add(new TitleText(title, ""));
    }
    return list;
  }
}
