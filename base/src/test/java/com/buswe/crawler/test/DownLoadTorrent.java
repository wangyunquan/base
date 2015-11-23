package com.buswe.crawler.test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.buswe.dht.dao.DhtInfoDaoImpl;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.entity.DhtinfoState;
import com.buswe.dht.paser.TorrentParser;
import com.buswe.dht.paser.TorrentinfoUrl;

public class DownLoadTorrent {
	
	public static void main(String  [] aa)
	{
		
		ExecutorService excutorService = Executors.newFixedThreadPool(Integer.valueOf(300)); 
		  DruidDataSource ds = new DruidDataSource();
		    ds.setUsername("root");
		    ds.setPassword("wangJue741369");
		    ds.setUrl("jdbc:mysql://123.56.227.196/yiding?useUnicode=true&characterEncoding=utf-8");
		    ds.setInitialSize(1);
		    ds.setMaxActive(20);
			  JdbcTemplate jdbcTemplate =new JdbcTemplate(ds);
			  NamedParameterJdbcTemplate namedjdbc=new NamedParameterJdbcTemplate(jdbcTemplate);
			  final DhtInfoDaoImpl dhtinfoDao	=new 	  DhtInfoDaoImpl ();
			  dhtinfoDao.setNamedjdbc(namedjdbc);
			  dhtinfoDao.setSimpleJdbc(jdbcTemplate);
				 String findSql="select * from dhtinfo  WHERE dhtstate=3 ";
				 BeanPropertyRowMapper<Dhtinfo> infoRowMaper=new BeanPropertyRowMapper<Dhtinfo>(Dhtinfo.class);
				 List<Dhtinfo> list= jdbcTemplate.query(findSql, infoRowMaper);
				 for(final Dhtinfo info :list)
				 {
					 excutorService.execute(new Runnable( ){

						@Override
						public void run() {
							String info_hash=info.getInfohash();
								String dowloadInfoHash=info_hash.trim().toUpperCase();
								InputStream inputStream=null;
								for(int i=0;i<TorrentinfoUrl.urls.length;i++)
								{
									String url=TorrentinfoUrl.formatUrl(dowloadInfoHash, i);
								   inputStream=openConnection(url);
									if(inputStream==null) //无法打开链接
									{
										info.setDhtstate(DhtinfoState.DHTSTATE_DOWNLOAD_FAIL);
										continue ;
									} else
									{
									Boolean result=	 TorrentParser.parse(inputStream, info);
									if(result) 
									{
										break ;//如果解析成功，则不再执行下一个地址去解析了
									}
									}
								}
								Integer dhtinfoState=info.getDhtstate();
								
								switch(dhtinfoState)
								{
								case DhtinfoState.DHTSTATE_DOWNLOAD_FAIL :
							 dhtinfoDao.updateDhtinfoSate(info.getInfohash(), DhtinfoState.DHTSTATE_DOWNLOAD_FAIL);
									 break ;
								case DhtinfoState.DHTSTATE_PARSING_FAIL :
						 	dhtinfoDao.updateDhtinfoSate(info.getInfohash(), DhtinfoState.DHTSTATE_PARSING_FAIL );
							 break ;
								case DhtinfoState.DHTSTATE_OK:
								{
								Boolean saveinfo=	dhtinfoDao.updateParseSuccess(info);
								Boolean savefile=true;
									 savefile=	dhtinfoDao.updateDhtFiles(info);
									 break ;
								}
								default :
								System.out.println (info.getInfohash()+  "  状态异常");
								 break ;
								}
								
								System.out.println(info_hash+ "  解析完成 "+dhtinfoState);
						}	 });

				 }
			  
			
			  
	}

	
	
	private static   InputStream  openConnection(String url)
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
}
