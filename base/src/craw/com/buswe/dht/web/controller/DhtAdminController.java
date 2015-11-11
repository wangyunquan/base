package com.buswe.dht.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.dht.service.CrawlService;

@Controller
@RequestMapping({ "/dht" })
public class DhtAdminController {
	
	@Resource
	CrawlService service;
	
	@RequestMapping("/creatIndex")
	public String creatindex()
	{
		try {
			service.creatIndex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/start")
	public String startService()
	{
		//TODO
		SecurityUtils.getSubject();
		service.startDhtService(1);//开启三个节点
		return null;
	}
	

}
