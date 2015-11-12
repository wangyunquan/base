package com.buswe.dht.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buswe.dht.service.CrawlService;

@Controller
@RequestMapping({ "/dht/admin/system" })
public class DhtAdminController {
	
	@Resource
	CrawlService service;
	
	@RequestMapping()
	public String config()
	{
		
		return "dht/config/index";
	}
	@RequestMapping("/creatIndex")
	public @ResponseBody String creatindex()
	{
		try {
			service.creatIndex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping("/stop")
	public @ResponseBody String stopdht()
	{
		
		service.stopDhtService();
		return "success";
	}
	
	@RequestMapping("/start")
	public @ResponseBody String startService()
	{
		//TODO
		SecurityUtils.getSubject();
		service.startDhtService(3);//开启三个节点
		return "success";
	}
	

}
