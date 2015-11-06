package com.buswe.dhtcrawler.bittorrentkad.bucket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.NodeComparator;
import com.buswe.dhtcrawler.bittorrentkad.KadNode;

/**
 * A bucket with the following policy: Any new node is inserted, if the bucket has reached its max size the oldest node in the bucket is removed.
 * 
 */
public class SlackBucket implements Bucket {

	private final List<KadNode> bucket;
	private final int maxSize;
	private final List<KadNode> publicBucket;

	public SlackBucket(int maxSize) {
		this.maxSize = maxSize;
		bucket = new LinkedList<KadNode>();
		publicBucket = new LinkedList<KadNode>();
	}
	@Override
	public void insertToPublicBucket(KadNode n) {
		synchronized (publicBucket) {
			if (publicBucket.contains(n))
				return;
			publicBucket.add(n);
		}
	}

	@Override
	public void insert(KadNode n) {
		// dont bother with other people wrong information
		if (n.hasNeverContacted())
			return;

		synchronized (bucket) {
			if (bucket.contains(n))
				return;
			if (bucket.size() == maxSize)
				bucket.remove(0);
			bucket.add(n);
		}
	}

	@Override
	public void markDead(Node n) {
		// nothing to do
	}

	@Override
	public List<KadNode> getAllNodes() {
		List<KadNode> allBucket=new ArrayList<KadNode>();
		synchronized (bucket) {
			allBucket.addAll(bucket);
			allBucket.addAll(publicBucket);
		}
		return allBucket;
	}
	@Override
	public List<Node> getClosestNodesByKey(Key key, int numNodesRequired) {
		 TreeSet<KadNode> sortedSet = new TreeSet<KadNode>(new NodeComparator(key));
		 sortedSet.addAll(bucket);
	       List<Node> closest = new ArrayList<>(numNodesRequired);
	        /* Now we have the sorted set, lets get the top numRequired */
	        int count = 0;
	        for (KadNode n : sortedSet)
	        {
	            closest.add(n.getNode());
	            if (++count == numNodesRequired)
	            {
	                break;
	            }
	        }
	        return closest;
	}
}
