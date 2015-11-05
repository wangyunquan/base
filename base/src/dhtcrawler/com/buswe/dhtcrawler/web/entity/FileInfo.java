package com.buswe.dhtcrawler.web.entity;

import java.util.List;

public class FileInfo {
	private String name;
	private String fileSize;
	private String info_hash;
	private String createTime;
	private int subFileCount;
	private String fileType;
	private List<SubFileInfo> subfileInfos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getInfo_hash() {
		return info_hash;
	}

	public void setInfo_hash(String info_hash) {
		this.info_hash = info_hash;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getSubFileCount() {
		return subFileCount;
	}

	public void setSubFileCount(int subFileCount) {
		this.subFileCount = subFileCount;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<SubFileInfo> getSubfileInfos() {
		return subfileInfos;
	}

	public void setSubfileInfos(List<SubFileInfo> subfileInfos) {
		this.subfileInfos = subfileInfos;
	}

}
