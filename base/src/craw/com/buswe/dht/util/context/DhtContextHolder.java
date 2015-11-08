package com.buswe.dht.util.context;

import java.util.concurrent.LinkedBlockingQueue;

import com.buswe.dht.entity.Dhtinfo;

 
/**
 * 全局的队列持有者
 * @author 王云权
 *
 */
public class DhtContextHolder {
	/**
	 * 当收到一个信息的时候，放到这来。由专门的读取线程去异步读取掉，以提高性能
	 */
	public static 	LinkedBlockingQueue<Dhtinfo> PUBLIC_DHTINFO_QUEUE=new LinkedBlockingQueue<Dhtinfo>();
 
	/**
	 * 控制保存DHT信息的线程启停的标识。
	 */
	public static Boolean SAVEDHT_THREAD_RUNNING=true;
	
	
}
