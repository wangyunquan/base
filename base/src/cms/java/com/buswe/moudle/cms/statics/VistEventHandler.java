package com.buswe.moudle.cms.statics;

import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import com.buswe.base.event.YunquanEventListener;
import com.buswe.moudle.cms.service.ArticleService;

public class VistEventHandler   extends YunquanEventListener
{
	  private Log eventLogger = this.logger;

	@Override
	public void onApplicationEvent(ApplicationEvent paramApplicationEvent) {
		ContentVistEvent event=(ContentVistEvent)paramApplicationEvent;
		ApplicationContext context=	event.getApplicationContext();
		ArticleService service=	context.getBean(ArticleService.class);
		service.hitAricle(event.getContentId());
	}  

}
