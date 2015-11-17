package com.buswe.dht.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.service.CrawlService;
import com.buswe.dht.service.CrawlServiceImpl;

@Controller
@RequestMapping({ "/" })
public class DhtController {

	private static final Integer pageSize = 20;
	@Resource
	CrawlService service;

	@RequestMapping("/index.htm")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("tatalDhtinfo", CrawlServiceImpl.tatalDhtinfo);
		return "/dht/index";
	}
	@RequestMapping("/search.htm")
	public String search(HttpServletRequest request, Model model) {
		java.text.DecimalFormat df=new java.text.DecimalFormat("#.####"); 
		Double begintime=new Double(System.currentTimeMillis());
		String q = request.getParameter("q");
		String p = request.getParameter("p");
		model.addAttribute("q", q);
		Integer pageNum = 0;
		if (StringUtils.isBlank(q)) {
			return "/dht/list";
		}
		if (StringUtils.isNotBlank(p)) {
			pageNum = Integer.valueOf(p)-1;
		}
		PageRequest pageRequest = new PageRequest(pageNum, pageSize);
		Page<Dhtinfo> page = null;
		try {
			page = service.search(q, pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("totalTime", df.format((new Double(System.currentTimeMillis())-begintime) /new Double(1000)));
		model.addAttribute("page", page);
		return "/dht/list";
	}
	
	
	@RequestMapping("/get/{infohash}.htm")
	public String get(@PathVariable("infohash")  String infohash, Model model) 
	{
     model.addAttribute("dhtinfo", service.loadDhtinfo(infohash));
		return "/dht/infohash";
	}
	

}
