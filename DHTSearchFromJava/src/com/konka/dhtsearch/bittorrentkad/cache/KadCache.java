package com.konka.dhtsearch.bittorrentkad.cache;

import java.util.List;

import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;

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
