package com.buswe.dhtcrawler.web.entity;


public class Page {

	private int  pageNumber;// 当前页码
	private String uri;
	private String dispalyName;

	public String getDispalyName() {
		return dispalyName;
	}

	public void setDispalyName(String dispalyName) {
		this.dispalyName = dispalyName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
 
 
}
