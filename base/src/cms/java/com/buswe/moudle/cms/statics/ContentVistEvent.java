package com.buswe.moudle.cms.statics;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class ContentVistEvent extends ApplicationContextEvent {

private 	String ip;
private	String type; //content tag 
private	String id;

	
	public ContentVistEvent(ApplicationContext source) {
		super(source);
	}

	
	
}
