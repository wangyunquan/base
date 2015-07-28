package com.buswe.moudle.cms.helper;

import java.util.List;

public class TitleText
{
  private String title;
  private String text;
  
  public TitleText() {}
  
  public TitleText(String title, String text)
  {
    this.title = title;
    this.text = text;
  }
  
  public static TitleText getTitleText(List<TitleText> textList, Integer page)
  {
    if ((page == null) || (page.intValue() < 1)) {
      return (TitleText)textList.get(0);
    }
    if (page.intValue() >= textList.size()) {
      return (TitleText)textList.get(textList.size() - 1);
    }
    return (TitleText)textList.get(page.intValue() - 1);
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
}
