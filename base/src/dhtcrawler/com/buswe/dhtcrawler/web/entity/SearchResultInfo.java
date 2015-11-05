package com.buswe.dhtcrawler.web.entity;

import java.util.List;

public class SearchResultInfo {
	private int total;// 搜索结果数
	private List<FileInfo> fileInfos;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}

}
