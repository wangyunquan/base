package com.buswe.dhtcrawler.entity;

public class DhtinfoState {
	public static final int DHTSTATE_OK=0;//状态正常，下载正常，解析成功
	public static final int DHTSTATE_PARSING_FAIL=1;//解析失败，无效
	public static final int DHTSTATE_NOT_DOWNLOAD=2;//还没下载
	public static final int DHTSTATE_DOWNLOAD_FAIL=3;//下载失败

	 public static final int DHTCONTENT_NOTLEGEL=0;//已处理为不合法，不予展示
	 public static final int DHTCONTENT_DEFAULT=1;//默认状态；有效
	 public static final int DHTCONTENT_COMPLAN=2;//被投诉或举报
	 
 
}
