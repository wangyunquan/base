package com.buswe.dht.node.bucket;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.buswe.base.config.ContextHolder;
import com.buswe.dht.node.KadNode;
import com.buswe.dht.node.Key;
import com.buswe.dht.node.Node;
import com.buswe.dht.node.NodeComparator;
 
 

/**
 * A bucket with the following policy: Any new node is inserted, if the bucket has reached its max size the oldest node in the bucket is removed.
 * 
 */
public class SlackBucket implements Bucket {

	private final LinkedHashSet<KadNode> bucket;
	private final int maxSize;
	private final List<KadNode> publicBucket;

	public SlackBucket() {
	 String buckSize=       ContextHolder.getProperty("dht.craw.config.node.buckSize");
	 this.maxSize=Integer.valueOf(buckSize);
		bucket = new LinkedHashSet<KadNode>();
		publicBucket = new LinkedList<KadNode>();
	}
	
	public List<Node> getRandomCosetNode(Integer size)
	{
		List<Node> list =new ArrayList<Node>();
		if(bucket.size()<=size)
		{
			for(KadNode kadNode:bucket)
			{
				list.add(kadNode.getNode());
			}
			
		}
		else
		{
			int i=0;
			for(KadNode kadNode:bucket)
			{
				list.add(kadNode.getNode());
				i++;
				if(i==size)
					return list;
			}
		}
		
		
		return list;
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
 
			if (bucket.contains(n))
				return;
			if (bucket.size() == maxSize)
				bucket.remove(0);
			bucket.add(n);
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
