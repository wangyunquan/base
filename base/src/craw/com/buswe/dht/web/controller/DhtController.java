package com.buswe.dht.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.service.CrawlService;

@Controller
@RequestMapping({ "/" })
public class DhtController {

	private static final Integer pageSize = 20;
	@Resource
	CrawlService service;

	@RequestMapping("/index.html")
	public String index(HttpServletRequest request, Model model) {
		return "/dht/index";
	}
	@RequestMapping("/search.html")
	public String search(HttpServletRequest request, Model model) {
		String q = request.getParameter("q");
		String p = request.getParameter("p");
		Integer pageNum = 0;
		if (StringUtils.isBlank(q)) {
			return "/dht/index";
		}
		if (StringUtils.isNotBlank(p)) {
			pageNum = Integer.valueOf(p);
		}
		PageRequest pageRequest = new PageRequest(pageNum, pageSize);
		Page<Dhtinfo> page = null;
		try {
			page = service.search(q, pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		return "/dht/search";
	}
	@RequestMapping("/get")
	public String get(String infohash, Model model) 
	{
		model.addAttribute("infohash", service.loadDhtinfo(infohash));
		return "infohash";
	}
	

}
