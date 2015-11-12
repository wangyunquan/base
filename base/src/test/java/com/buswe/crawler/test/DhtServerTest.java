//package com.buswe.crawler.test;
//
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//
//import com.buswe.dhtcrawler.AppManager;
//import com.buswe.dhtcrawler.Key;
//import com.buswe.dhtcrawler.Node;
//import com.buswe.dhtcrawler.bittorrentkad.KadNet;
//
//public class DhtServerTest {
//
//	public static void main(String[] args) throws Exception {
//
//		InetSocketAddress[] BOOTSTRAP_NODES = { //
//				new InetSocketAddress("router.bittorrent.com", 6881), //
//						new InetSocketAddress("dht.transmissionbt.com", 6881),//
//						new InetSocketAddress("router.utorrent.com", 6881), };
//		
//		AppManager.init();// 1---
//		Key key = AppManager.getKeyFactory().generate();
//		Node localNode = new Node(key).setInetAddress(InetAddress.getByName("0.0.0.0")).setPoint(20200 );// 这里注意InetAddress.getLocalHost();为空
//
//		KadNet kadNet = new KadNet(null, localNode);
//		kadNet.join(BOOTSTRAP_NODES).create();
//
//	}
//
//}
