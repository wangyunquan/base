package com.buswe.dhtcrawler.bittorrentkad;

import java.util.List;

import com.buswe.dhtcrawler.Node;


public interface NodeStorage {
	/**
	 * Register this data structure to listen to incoming messages and update itself
	 * accordingly.
	 * Invoke this method after creating the entire system
	 */
	public void registerIncomingMessageHandler();
	/**
	 * 
	 * @return a list containing all the nodes in the data structure
	 */
	public List<Node> getAllNodes();

}
