package com.buswe.dht.paser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.entity.DhtinfoState;
import com.buswe.dht.service.DhtinfoService;

public class DhtinfoParser implements Runnable {
	  protected Logger logger = LoggerFactory.getLogger(getClass());
	private Dhtinfo dhtinfo;
	private DhtinfoService service;
private List<Dhtinfo>dhtInfos;
	public DhtinfoParser (Dhtinfo dhtinfo,DhtinfoService service,	 List<Dhtinfo>dhtInfos)
	{
		this.dhtinfo=dhtinfo;
		this.service=service;
		this.dhtInfos=dhtInfos;
	}
	@Override
	public void run() {
		String info_hash=dhtinfo.getInfohash();
		String dowloadInfoHash=info_hash.trim().toUpperCase();
		InputStream inputStream=null;
		for(int i=0;i<TorrentinfoUrl.urls.length;i++)
		{
			String url=TorrentinfoUrl.formatUrl(dowloadInfoHash, i);
		   inputStream=openConnection(url);
			if(inputStream==null) //无法打开链接
			{
				dhtinfo.setDhtstate(DhtinfoState.DHTSTATE_DOWNLOAD_FAIL);
				continue ;
			} else
			{
			Boolean result=	 TorrentParser.parse(inputStream, dhtinfo);
			if(result) 
				break ;//如果解析成功，则不再执行下一个地址去解析了
			}
		}
		service.updateDhtinfoDownLoad(dhtinfo, true);
		dhtInfos.remove(dhtinfo);
		
	}
	private  InputStream  openConnection(String url)
	{
		InputStream inputStream = null;
		try {
			URL parsedUrl = new URL(url);
			HttpURLConnection connection=(HttpURLConnection)parsedUrl.openConnection();
			connection.setConnectTimeout( 3 * 1000);
			connection.setReadTimeout( 3* 1000);
			connection.setUseCaches(false);
			connection.setDoInput(true);//重要
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode ==HttpURLConnection.HTTP_OK) {
			inputStream =connection.getInputStream();
			}
		} catch (Exception e) {
		
		//	e.printStackTrace(); nothing todo 
			//logger.debug("下载失败："+url);
		}
		return inputStream;
		
		}
 
	public static void main(String aa [])
	{
		DhtinfoParser paser=new DhtinfoParser(null,null,null);
		InputStream inputStream=null;
		String dowloadInfoHash="8761e9485d810059bdd07bccc1a635aa8212497b";
		Dhtinfo dhtinfo =new Dhtinfo ();
		dhtinfo.setInfohash(dowloadInfoHash);
		for(int i=0;i<TorrentinfoUrl.urls.length;i++)
		{
			String url=TorrentinfoUrl.formatUrl(dowloadInfoHash, i);
			   inputStream=paser.openConnection(url);
			   if(inputStream!=null)
			   {
				Boolean result=	 TorrentParser.parse(inputStream, dhtinfo);
				System.out.println(result);
			   }
		}
	}
}

