package com.konka.dhtsearch.bittorrentkad.cache;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;

public class StoppableCache implements KadCache {

	private final AtomicBoolean isChangeable = new AtomicBoolean(true);
	
	private final KadCache realCache;
	
	StoppableCache( KadCache realCache) {
		this.realCache = realCache;
	}
	
	public void stopUpdating() {
		isChangeable.set(false);
	}
	
	@Override
	public synchronized void insert(Key key, List<Node> nodes) {
		if (isChangeable.get())
			realCache.insert(key, nodes);
	}

	@Override
	public List<Node> search(Key key) {
		return realCache.search(key);
	}

	@Override
	public void clear() {
		realCache.clear();
	}
	
}
