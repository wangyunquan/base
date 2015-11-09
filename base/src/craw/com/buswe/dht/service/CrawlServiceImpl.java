package com.buswe.dht.service;

import com.buswe.dht.save.SaveDhtThread;

public class CrawlServiceImpl {
	
	
	public void startService()
	{
		Integer batch=500;
		SaveDhtThread saveToDbThread=new SaveDhtThread(batch); //保存到数据库的线程
		
		//解析 dhtinfo的线程
		
		KadParserTorrentServer parseServer=new 
 
		
		
	}

}
