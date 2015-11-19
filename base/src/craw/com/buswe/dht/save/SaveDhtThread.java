package com.buswe.dht.save;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.base.config.ContextHolder;
import com.buswe.base.utils.Threads;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.service.DhtinfoService;
import com.buswe.dht.util.context.DhtContextHolder;
 

/**
 * 将抓取到的DHTinfo放入到数据库中的线程 只需要启动一个线程去操作，采用批量 ， 从队列中获取待保存的信息，异步操作
 * 全局就此一个线程
 * 
 * @author 王云权
 *
 */
public class SaveDhtThread implements Runnable {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private int icrease = 0;// 本线程内的计数器
	private DhtinfoService dhtinfoService;
	public SaveDhtThread() {
		startThread = new Thread(this);
	}
	@Override
	public void run() {
		dhtinfoService=ContextHolder.getBean(DhtinfoService.class);
		while (true) {
				try {
					// 阻塞，一直等到获取到
					Dhtinfo info = DhtContextHolder.PUBLIC_DHTINFO_QUEUE.take();
						Boolean saveSuccess = false;
						try {
							saveSuccess = dhtinfoService.saveDhtinfo(info);
							icrease++;
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("保存信息失败", e);
							logger.error("保存dhtinfo信息失败，数量：" + icrease + "infohash :" + info.getInfohash());
						}
						if (!saveSuccess)
							logger.error("保存dhtinfo信息失败，数量：" + icrease + "infohash :" + info.getInfohash());
						else {
							   logger.info("本次已抓取到信息："+icrease);
						}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	     Threads.sleep(60*1000);
		}

	}

	public void shutdown() {
		{
			this.isActive.set(false);
			startThread.interrupt();
		}
	
	}
	public void start() {
		startThread.start();
	}
}
