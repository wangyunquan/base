package com.buswe.dhtcrawler.bittorrentkad.bucket;

import java.util.List;

import com.buswe.dhtcrawler.Key;
import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.bittorrentkad.KadNode;
import com.buswe.dhtcrawler.bittorrentkad.NodeStorage;

public interface KBuckets extends NodeStorage {

	/**
	 * Uses the keyFactory to generate keys which will fit to different buckets
	 * @return a list of random keys where no 2 keys will fit into the same bucket
	 */
	public abstract List<Key> randomKeysForAllBuckets();

	/**
	 * Inserts a node to the data structure
	 * The can be rejected, depending on the bucket policy
	 * @param node
	 */
	public abstract void insert(KadNode node);


	
	public abstract void markAsDead(Node n);

	/**
	 * Returns a single bucket's content. The bucket number is calculated
	 * using the given key according to its prefix with the local node's key
	 * as explained above.
	 * 
	 * @param k key to calculate the bucket from
	 * @return a list of nodes from a particular bucket
	 */
	public abstract List<Node> getAllFromBucket(Key k);

	/**
	 * Gets all nodes with keys closest to the given k.
	 * The size of the list will be MIN(n, total number of nodes in the data structure)
	 * @param k the key which the result's nodes are close to
	 * @param n the maximum number of nodes expected
	 * @return a list of nodes sorted by proximity to k
	 */
	public abstract List<Node> getClosestNodesByKey(Key k, int n);


	@Override
	public abstract String toString();

}