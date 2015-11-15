package com.buswe.dht.server;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.base.config.ContextHolder;
import com.buswe.base.utils.Threads;
import com.buswe.dht.message.KadMessage;
import com.buswe.dht.message.reqandres.FindNodeRequest;
import com.buswe.dht.node.KadNet;
import com.buswe.dht.node.KadNode;
import com.buswe.dht.node.Node;

/**
 * 发送消息查找node
 * 
 * 
 */
public class KadSendMsgServer implements Runnable {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private final KadNet kadNet;
	private final Integer eachSendTime;

	public KadSendMsgServer(KadNet kadNet) {
		startThread = new Thread(this);
		this.kadNet = kadNet;
		String sendTime = ContextHolder.getProperty("dht.craw.config.sendFindNodeTime");
		eachSendTime = Integer.valueOf(sendTime);
	}
	/**
	 * 只发送findnode操作，其他请求请使用KadSendMsgServer
	 * 
	 * @param msg
	 *            要发送的消息（一般是具体实现）
	 * @throws IOException
	 *             any socket exception
	 */
	public void send(final KadMessage msg) throws IOException {
		kadNet.sendMessage(msg);

	}
	/**
	 * 不停发送消息
	 * TODO ,定期向其它的节点，发送一次GetPeersRequest 查询，告诉自己的活跃，infohash随机产生，或从配置文件中读取
	 */
	@Override
	public void run() {
		this.isActive.set(true);
		while (this.isActive.get()) {
			try {
				List<KadNode> nodes = kadNet.getAllNodes();
				logger.debug("本地节点" + kadNet.getKey() + "桶内的节点:" + nodes.size());
				for (int i = 0; i < nodes.size(); i++) { //每次全部发送？，还是只发送一部分？
					KadNode node = null;
					try {
						node = nodes.get(i);
						send(node.getNode());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(node);
						System.out.println(node.getNode());
					}
				}
				nodes.clear();
				nodes = null;
				displayAvailableMemory();
				Threads.sleep(1000 * eachSendTime); // 间隔 eachSendTime秒一次，否则会导致CPU过高
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void send(Node to) throws IOException {
		FindNodeRequest msg = FindNodeRequest.creatLocalFindNodeRequest(to);
		send(msg);
	}

	public void displayAvailableMemory() {
		DecimalFormat df = new DecimalFormat("0.00");
		Runtime.getRuntime().gc();
		long freeMem1 = Runtime.getRuntime().freeMemory();
		logger.debug("-----空闲内存" + (df.format(freeMem1 / (1024F * 1024F)) + "MB"));
		int size = Thread.getAllStackTraces().size();
		System.out.println("系统中的线程数=" + size);
	}

	/**
	 * 用于查找某个节点，以获得其地址信息。当某个节点接收到该请求后，如果目标节点不在自己的路由表里，那么就返回离目标节点较近的K个节点。
	 * 这个消息可用于节点启动时构建路由表。通过find_node方式构建路由表，其实现方式为向DHT网络查询自己。那么，
	 * 接收该查询的节点就会一直返回其他节点了列表，查询者递归查询，直到无法查询为止。那么，什么时候无法继续查询呢？这一点我也不太清楚。
	 * 每一次查询得到的都是离目标节点更接近的节点集，那么理论上经过若干次递归查询后，就无法找到离目标节点更近的节点了，因为最近的节点是自己，
	 * 但自己还未完全加入网络。这意味着最后所有节点都会返回空的节点集合，这样就算查询结束？
	 * 
	 * 实际上，通过find_node来构建路由表，以及顺带加入DHT网络，这种方式什么时候停止在我看来并不重要。路由表的构建并不需要在启动时构建完成，
	 * 在以后与其他节点的交互过程中，路由表本身就会慢慢地得到构建。在初始阶段尽可能地通过find_node去与其他节点交互，
	 * 最大的好处无非就是尽早地让网络中的其他节点认识自己。
	 */
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
