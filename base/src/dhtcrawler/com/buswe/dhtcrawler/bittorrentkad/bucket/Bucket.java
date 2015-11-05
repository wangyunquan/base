package com.buswe.dhtcrawler.bittorrentkad.bucket;

import java.util.List;

import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.bittorrentkad.KadNode;

/**
 * Represents A finate container for nodes
 * 
 */
public interface Bucket {

	/**
	 * Adds a new node to the bucket
	 * 
	 * @param n
	 *            the new node
	 */
	public void insert(KadNode n);

	/**
	 * 长期可用的节点
	 * @param n
	 */
	public void insertToPublicBucket(KadNode n);

	/**
	 * Marks a node as dead: the dead node will be replace if insert was invoked
	 * 
	 * @param n
	 *            the dead node
	 */
	public void markDead(Node n);

	public List<KadNode> getAllNodes();

	public List<Node> getClosestNodesByKey(Key key, int i);
}
