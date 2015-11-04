package org.konkakjb.text;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.konka.dhtsearch.AppManager;
import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.KadNet;
import com.konka.dhtsearch.db.mysql.exception.DhtException;
import com.konka.dhtsearch.exception.ErrHandler;
import com.konka.dhtsearch.util.ThreadUtil;

public class SearchText {
	private static final InetSocketAddress[] BOOTSTRAP_NODES = { //
	new InetSocketAddress("router.bittorrent.com", 6881), //
			new InetSocketAddress("dht.transmissionbt.com", 6881),//
			new InetSocketAddress("router.utorrent.com", 6881), };

	public static void main(String[] args) throws DhtException {
		startservice();
	}

	public static void startservice() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
				startKadNet();
			}
		};
		thread.setUncaughtExceptionHandler(new ErrHandler() {
			@Override
			public void caughtEnd() {
				startservice();
			}
		});
		thread.start();
	}

	public static void startKadNet() {
		System.out.println("1");
		int size = 1;
		try {
			for (int i = 0; i < size; i++) {
				AppManager.init();
				Key key = AppManager.getKeyFactory().generate();
				Node localNode = new Node(key).setInetAddress(InetAddress.getByName("0.0.0.0")).setPoint(20200 + i);// 这里注意InetAddress.getLocalHost();为空
				new KadNet(null, localNode).join(BOOTSTRAP_NODES).create();
				ThreadUtil.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
