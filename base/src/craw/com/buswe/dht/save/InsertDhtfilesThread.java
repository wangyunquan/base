package com.buswe.dht.save;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.base.config.ContextHolder;
import com.buswe.dht.entity.Dhtinfo;
import com.buswe.dht.service.DhtinfoService;
import com.buswe.dht.util.context.DhtContextHolder;
 

/**
	 * 专门用来更新dhtFile的队列，避免锁表，因为需要删除掉原有的，再重新插入，所以锁表。改为依次执行
 * 全局就此一个线程
 * 
 * @author 王云权
 *
 */
public class InsertDhtfilesThread implements Runnable {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private DhtinfoService dhtinfoService;
	public InsertDhtfilesThread() {
		startThread = new Thread(this);
	}
	@Override
	public void run() {
		dhtinfoService=ContextHolder.getBean(DhtinfoService.class);
		while (true) {
				try {
					// 阻塞，一直等到获取到
					Dhtinfo info = DhtContextHolder.PUBLIC_DHTFILEINSERT_QUEUE.take();
						Boolean saveSuccess = false;
						try {
							saveSuccess = dhtinfoService.updateDhtFiles(info);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(info.getInfohash()+ "     保存dhtfiles失败", e);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	public void shutdown() {
		this.isActive.set(false);
		startThread.interrupt();
	}
	public void start() {
		startThread.start();
		logger.info("插入dhfiles的线程启动！");
	}
}
