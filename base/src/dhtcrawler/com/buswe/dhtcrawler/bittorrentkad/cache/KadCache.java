package com.buswe.dhtcrawler.bittorrentkad.cache;

import java.util.List;

import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;

/**
 * Caches the results of find node operations
 * 
 * @author eyal.kibbar@gmail.com
 *
 */
public interface KadCache {

	public void insert(Key key, List<Node> nodes);
	
	public List<Node> search(Key key);
	
	public void clear();
	
}
