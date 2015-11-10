package com.buswe.crawler.test;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.buswe.base.config.annotation.ContextConfig;
import com.buswe.dht.service.CrawlService;
@ContextConfiguration(classes={ContextConfig.class})
public class CrawTest  extends AbstractTransactionalJUnit4SpringContextTests{
//	@Resource
 CrawlService service;
	@Test
	public   void test() {
 	 service.startDhtService(1);
 
	}

}
