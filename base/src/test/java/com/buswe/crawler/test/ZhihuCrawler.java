//package com.buswe.crawler.test;
//
//import java.util.regex.Pattern;
//
//import com.buswe.crawler.BreadthCrawler;
//import com.buswe.crawler.model.Links;
//import com.buswe.crawler.model.Page;
//
//public class ZhihuCrawler extends  BreadthCrawler{
//
//	public ZhihuCrawler(String crawlPath, boolean autoParse) {
//		super(crawlPath, autoParse);
//	}
//
//	public static void main(String[] args) {
//		 ZhihuCrawler crawler=new ZhihuCrawler("D:\\code\\crawl",true);  
//	     crawler.addSeed("http://www.zhihu.com");  
//	      crawler.addRegex("http://www.zhihu.com/.*");  
//       try {
//		crawler.start(2);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}    
//
//
//	}
//
//	@Override
//	public void visit(Page page, Links nextLinks) {
//		  String question_regex="^http://www.zhihu.com/question/[0-9]+";  
//	   if(Pattern.matches(question_regex, page.getUrl())){  
//	        System.out.println("正在抽取"+page.getUrl());  
//	      /*抽取标题*/  
//	        String title=page.getDoc().title();  
//	          System.out.println(title);  
//	        /*抽取提问内容*/  
//	    String question=page.getDoc().select("div[id=zh-question-detail]").text();  
//           System.out.println(question);  
//	  
//    }  
//		
//	}
//
//}
