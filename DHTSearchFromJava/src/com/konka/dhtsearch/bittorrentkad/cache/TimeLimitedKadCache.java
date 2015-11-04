package com.konka.dhtsearch.bittorrentkad.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;

/**
 * Caches all results and never removes them. This policy is obviously only good for testing
 * 
 * @author eyal.kibbar@gmail.com
 * 
 */
public class TimeLimitedKadCache implements KadCache {

	private final long validTime;
	private final AtomicInteger optimalCacheMaxSize;

	protected class CacheEntry {
		private final List<Node> nodes;
		private final long timestamp;
		private final Key key;

		CacheEntry(final List<Node> nodes, final Key key) {
			this.nodes = nodes;
			this.key = key;
			this.timestamp = System.currentTimeMillis();
		}

		public List<Node> getNodes() {
			return nodes;
		}

		public boolean isValid() {
			return timestamp + validTime > System.currentTimeMillis();
		}

		public Key getKey() {
			return key;
		}
	}

	protected final Map<Key, CacheEntry> cache = new HashMap<Key, CacheEntry>();

	TimeLimitedKadCache(final long validTime, final AtomicInteger optimalCacheMaxSize) {
		this.validTime = validTime;
		this.optimalCacheMaxSize = optimalCacheMaxSize;
	}

	@Override
	public synchronized void insert(final Key key, final List<Node> nodes) {
		// System.out.println(localNode.getKey()+": inserting "+key+" => "+nodes);
		cache.put(key, new CacheEntry(nodes, key));

		if (optimalCacheMaxSize.get() < cache.size())
			optimalCacheMaxSize.set(cache.size());
	}

	@Override
	public synchronized List<Node> search(final Key key) {
		final CacheEntry cacheEntry = searchCacheEntry(key);
		if (cacheEntry == null)
			return null;

		if (!cacheEntry.isValid()) {
			remove(cacheEntry);
			return null;
		}

		// System.out.println(localNode.getKey()+": Cache hit !");

		return cacheEntry.getNodes();
	}

	protected void remove(final CacheEntry entry) {
		cache.remove(entry.getKey());
	}

	protected CacheEntry searchCacheEntry(final Key key) {
		return cache.get(key);
	}

	protected void insertCacheEntry(final CacheEntry c) {
		cache.put(c.getKey(), c);
	}

	@Override
	public void clear() {
		cache.clear();
	}
}
