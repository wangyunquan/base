package com.buswe.dht.paser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.entity.DhtinfoState;
import com.buswe.dht.service.DhtinfoService;

public class DhtinfoParser implements Runnable {
	  protected Logger logger = LoggerFactory.getLogger(getClass());
	private Dhtinfo dhtinfo;
	private DhtinfoService service;
	public DhtinfoParser (Dhtinfo dhtinfo,DhtinfoService service)
	{
		this.dhtinfo=dhtinfo;
		this.service=service;
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
		
	}
	private  InputStream  openConnection(String url)
	{
		InputStream inputStream = null;
		try {
			URL parsedUrl = new URL(url);
			HttpURLConnection connection=(HttpURLConnection)parsedUrl.openConnection();
			connection.setConnectTimeout( 5 * 1000);
			connection.setReadTimeout( 5 * 1000);
			connection.setUseCaches(false);
			connection.setDoInput(false);
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
		DhtinfoParser paser=new DhtinfoParser(null,null);
		InputStream inputStream=null;
		String dowloadInfoHash="1AB00671E2D76CDA7ED625AB5A2491352F821F39";
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

