package com.buswe.dht.paser;

import org.springframework.context.ApplicationContext;

import com.buswe.dht.entity.Dhtinfo;

public class DhtinfoParser implements Runnable {
	private Dhtinfo dhtinfo;
	public DhtinfoParser (Dhtinfo dhtinfo)
	{
		this.dhtinfo=dhtinfo;
	}
	@Override
	public void run() {
		String info_hash=dhtinfo.getInfoHash();
		String dowloadInfoHash=info_hash.trim().toUpperCase();
		for(int i=0;i<TorrentinfoUrl.urls.length;i++)
		{
			
		}
	}

}
