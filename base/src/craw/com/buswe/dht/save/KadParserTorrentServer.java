package com.buswe.dht.save;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.search.util.impl.Executors;
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
 
	public KadParserTorrentServer() {
		startThread = new Thread(this);
	}
	@Override
	public void run() {
		//TODO移动到配置文件中去
		 ExecutorService excutorService = Executors.newFixedThreadPool(5,"parseTorrent");
		this.isActive.set(true);
		DhtinfoService service=ContextHolder.getBean("dhtinfoService");
		while (this.isActive.get()) {
			 List<Dhtinfo>dhtInfos = null;
			try {
				System.gc();
				dhtInfos =service.getDhtinfosByState(DhtinfoState.DHTSTATE_NOT_DOWNLOAD, 100);
				logger.debug("获取未下载的种子项:"+dhtInfos.size());
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			//TODO 启动多个线程去下载，会更快
			if (dhtInfos!=null&&dhtInfos.size()>0) {
				for (Dhtinfo dhtInfo : dhtInfos) {
					DhtinfoParser parser=new DhtinfoParser(dhtInfo,service);
					excutorService.execute(parser);
				}
			}
			Threads.sleep(60 * 1000);
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
