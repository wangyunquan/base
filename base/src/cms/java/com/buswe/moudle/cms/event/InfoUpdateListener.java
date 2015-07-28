package com.buswe.moudle.cms.event;

import org.springframework.context.ApplicationEvent;

import com.buswe.base.event.YunquanEventListener;
import com.buswe.moudle.cms.entity.Article;

public class InfoUpdateListener
  extends YunquanEventListener
{
  public void onApplicationEvent(ApplicationEvent event)
  {
    Article info = (Article)event.getSource();
  }
}
