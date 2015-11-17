package com.buswe.dht.save;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.base.config.ContextHolder;
import com.buswe.base.utils.Threads;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.entity.DhtinfoState;
import com.buswe.dht.paser.DhtinfoParser;
import com.buswe.dht.service.DhtinfoService;

/**
根据dhtinfo去下载种子的线程类
 */
public class KadParserTorrentServer implements Runnable {
	  protected Logger logger = LoggerFactory.getLogger(getClass());
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private final  ExecutorService excutorService;
	private Integer everyFetchSleep;
	public KadParserTorrentServer() {
		startThread = new Thread(this);
		String downloadThread=ContextHolder.getProperty("dht.craw.config.downloadThread");
		excutorService = Executors.newFixedThreadPool(Integer.valueOf(downloadThread)); //同时启动多个线程去下载
		everyFetchSleep=Integer.valueOf(ContextHolder.getProperty("dht.craw.config.everyFetchSleep"));
	}
	@Override
	public void run() {
		this.isActive.set(true);
		DhtinfoService service=ContextHolder.getBean("dhtinfoService");
		 List<Dhtinfo>dhtInfos = null;
		while (this.isActive.get()) {
	
			try {
				System.gc();
				if(dhtInfos==null||dhtInfos.size()==0)
				{
				dhtInfos =service.getDhtinfosByState(DhtinfoState.DHTSTATE_NOT_DOWNLOAD, 1000);
				logger.info("获取未下载的种子项:"+dhtInfos.size());
				if(dhtInfos.size()==0)
				{
					Threads.sleep(everyFetchSleep * 1000);
				}
				}

				
				if (dhtInfos!=null&&dhtInfos.size()>0) {
					for (Dhtinfo dhtInfo : dhtInfos) {
						DhtinfoParser parser=new DhtinfoParser(dhtInfo,service,dhtInfos );
						excutorService.execute(parser);
					}
				}
				
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		
		}
	}
	public boolean isRunning(){
		return this.isActive.get();
	}


	/**
	 * Shutdown the server and closes the socket 关闭服务
	 * 
	 * @param kadServerThread
	 */
	public void shutdown() {
		this.isActive.set(false);
		startThread.interrupt();
		try {
			startThread.join();
		} catch (final InterruptedException e) {
		}
	}
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		startThread.setUncaughtExceptionHandler(eh);
	}
	public void start() {
		startThread.start();
	}
}
