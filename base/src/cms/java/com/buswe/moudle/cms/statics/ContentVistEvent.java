package com.buswe.moudle.cms.statics;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class ContentVistEvent extends ApplicationContextEvent {

	private String contentId;
	
	public ContentVistEvent(ApplicationContext source) {
		super(source);
	}

	public ContentVistEvent(ApplicationContext source, String contentId)

	{
		super(source);
       this.contentId=contentId;
	}

	public String getContentId() {
		return contentId;
	}

	 public String toString()
	 {
		 return "content  visit "+contentId+"    source"+getSource();
	 }
	
}
