package com.buswe.dhtcrawler.bittorrentkad.net;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.db.mongodb.MongodbUtil;
import com.buswe.dhtcrawler.db.mongodb.MongodbUtilProvider;
import com.buswe.dhtcrawler.util.ArrayUtils;
import com.buswe.dhtcrawler.util.ThreadUtil;
import com.buswe.dhtcrawler.util.TorrentParser;

/**
 * 发送消息查找node
 * 
 * @author 耳东 (cgp@0731life.com)
 */
public class KadParserTorrentServer implements Runnable {
	  protected Logger logger = LoggerFactory.getLogger(getClass());
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private final MongodbUtil dhtInfoDao = MongodbUtilProvider.getMongodbUtil();
	public KadParserTorrentServer() {
		startThread = new Thread(this);
	}

	/**
	 * 不停发送消息
	 * 
	 * @category http://torrage.com/torrent/66B106B04F931DA3485282C43CF66F6BD795C8C4.torrent
	 * @category http://torcache.net/torrent/66B106B04F931DA3485282C43CF66F6BD795C8C4.torrent
	 * @category http://zoink.it/torrent/66B106B04F931DA3485282C43CF66F6BD795C8C4.torrent
	 * @category http://magnet.vuze.com/magnetLookup?hash=ANRBNFHQ5CZM5BZBNSM4WXFDV4RQFHRX
	 * @category http://bt.box.n0808.com/05/A5/05153F611B337A378F73F0D32D2C16D362D06BA5.torrent;
	 */
	@Override
	public void run() {
		this.isActive.set(true);
		while (this.isActive.get()) {
			List<DhtInfo_MongoDbPojo> dhtInfos = null;
			try {
				System.gc();
				dhtInfos = dhtInfoDao.getNoAnalyticDhtInfos(50);
				logger.debug("获取未下载的种子项:"+dhtInfos.size());
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			if (!ArrayUtils.isEmpty(dhtInfos)) {
				for (DhtInfo_MongoDbPojo dhtInfo : dhtInfos) {
					TorrentParser.parseDhtInfo(dhtInfo);
				}
			}
			ThreadUtil.sleep(5 * 1000);
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
