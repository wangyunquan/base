package com.konka.dhtsearch.bittorrentkad.cache;

import java.util.List;

import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;

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
