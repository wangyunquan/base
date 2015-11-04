package com.konka.dhtsearch.bittorrentkad.net;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.KadNet;
import com.konka.dhtsearch.bittorrentkad.KadNode;
import com.konka.dhtsearch.bittorrentkad.krpc.KadMessage;
import com.konka.dhtsearch.bittorrentkad.krpc.find_node.FindNodeRequest;
import com.konka.dhtsearch.util.ThreadUtil;

/**
 * 发送消息查找node
 * 
 * @author 耳东 (cgp@0731life.com)
 * 
 */
public class KadSendMsgServer implements Runnable {

	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Thread startThread;
	private final KadNet kadNet;

	public KadSendMsgServer(KadNet kadNet) {
		startThread = new Thread(this);

		this.kadNet = kadNet;
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
	 */
	@Override
	public void run() {
		this.isActive.set(true);
		while (this.isActive.get()) {
			try {
				List<KadNode> nodes = kadNet.getAllNodes();

				System.out.println(nodes.size());
				for (int i = 0; i < nodes.size(); i++) {
					KadNode node = null;
					try {
						  node = nodes.get(i);
						send(node.getNode());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(node);
						System.out.println(node.getNode());
					}
					// System.out.println(node.getNode().getKey().toString()+"--"+node.getNode().getSocketAddress());
				}
				nodes.clear();
				System.out.println(nodes.size());
				nodes = null;
				displayAvailableMemory();
				ThreadUtil.sleep(5000);
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
		// 显示JVM总内存
		// long totalMem = Runtime.getRuntime().totalMemory();
		// System.out.println(df.format(totalMem/(1024F*1024F)) + "MB");
		// 显示JVM尝试使用的最大内存
		// long maxMem = Runtime.getRuntime().maxMemory();
		// System.out.println(df.format(maxMem/(1024F*1024F)) + "MB");
		// 空闲内存
		// long freeMem = Runtime.getRuntime().freeMemory();
		// System.out.println("空闲内存-----"+(df.format(freeMem/(1024F*1024F)) + "MB"));
		// Runtime.getRuntime().
		Runtime.getRuntime().gc();
		long freeMem1 = Runtime.getRuntime().freeMemory();
		System.out.println("-----" + (df.format(freeMem1 / (1024F * 1024F)) + "MB"));

		// int size=Thread.getAllStackTraces().size();
		// System.out.println("系统中的线程数="+size);
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
