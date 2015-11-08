package com.buswe.dht.save;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.base.config.ContextHolder;
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
	private int icrease = 0;// 本线程内的计数器
	private int bachnum = 1000;
	private List<Dhtinfo> dhtinfoList;
	private DhtinfoService dhtinfoService;
	public SaveDhtThread(Integer bachnum) {
		if (bachnum != null && bachnum != 0) {
			this.bachnum = bachnum;
		}
		dhtinfoList = new ArrayList<Dhtinfo>(bachnum);
	}
	@Override
	public void run() {
		dhtinfoService=ContextHolder.getBean(DhtinfoService.class);
		while (true) {
			if (DhtContextHolder.SAVEDHT_THREAD_RUNNING) { //TODO 这种启停线程的方式存在问题的
				try {
					// 阻塞，一直等到获取到
					Dhtinfo info = DhtContextHolder.PUBLIC_DHTINFO_QUEUE.take();
					icrease++;
					dhtinfoList.add(info);
					if (dhtinfoList.size() == bachnum) {
						Boolean saveSuccess = false;
						try {
							saveSuccess = dhtinfoService.saveBatchDhtinfo(dhtinfoList);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("保存信息失败", e);
						}
						if (saveSuccess)
							logger.debug("保存到信息成功,本次已保存:" + icrease + "批次 :" + bachnum);
						else {
							logger.debug("保存dhtinfo信息失败，数量：" + icrease + "批次 :" + bachnum);
						}
						dhtinfoList.clear();// 清空
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else// 线程要停止，将剩下的不到批量的数都保存起来
			{
				for (Dhtinfo info : dhtinfoList) {
					dhtinfoService.insertDhtinfo(info);
				}
			}
		}

	}

}
