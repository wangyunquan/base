package com.buswe.dhtcrawler.bittorrentkad.cache;

import java.util.List;

import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;

/**
 * A default stupid implementation of KadCache that stores nothing
 * @author eyal.kibbar@gmail.com
 *
 */
public class DummyKadCache implements KadCache {

	@Override
	public void insert(Key key, List<Node> nodes) {
	}

	@Override
	public List<Node> search(Key key) {
		return null;
	}

	@Override
	public void clear() {
	}

}
