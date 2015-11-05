package com.buswe.dhtcrawler.db.models;

import java.net.InetSocketAddress;

import com.buswe.dhtcrawler.AppManager;
import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.db.mongodb.MongoCollection;
@MongoCollection(value="peerinfo")
public class PeerInfo {
	public PeerInfo() {
		super();
	}

	public PeerInfo(String ipAddress, int port, boolean flag) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
		this.flag = flag;
	}
	private String ipAddress;
	private int port;
	private boolean flag;//

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Node getNode(){
		Key key = AppManager.getKeyFactory().generate();
		Node node = new Node(key).setSocketAddress(new InetSocketAddress(ipAddress, port));
		return node;
	}
}
