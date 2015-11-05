package com.buswe.dhtcrawler.bittorrentkad;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.buswe.dhtcrawler.Node;

/**
 * Wrapper for Node type. Used to keep track on the following:
 * 节点式包装。用于跟踪在后：
 * 1. last contact time - the most recent time a message was received from this node
 * 最后联系时间最新的消息是从该节点接收
 * 2. being ping - expecting a ping to be received from this node
 * 在平等平是从本节点接收
 * 
 *
 */
public class KadNode {

	private final AtomicLong lastContactTimestamp = new AtomicLong(0);
	private final AtomicBoolean beingPinged = new AtomicBoolean(false);
	protected Node node;
	
	
	public KadNode() {
	}
	
	@Override
	public int hashCode() {
		return getNode().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !getClass().equals(obj.getClass()))
			return false;
		return getNode().equals(((KadNode)obj).getNode());
	}
	
	/**
	 * Sets the contained node.
	 * Do not re-set the node after you start using this class
	 * 
	 * @param node the wrapped node
	 * @return this for fluent interface
	 */
	public KadNode setNode(Node node) {
		this.node = node;
		return this;
	}

	/**
	 * Signals this node is being pinged.
	 * 这个节点被ping信号。
	 * Important: If true is returned, you must call releasePingLock to allow this node
	 * 重要：如果返回true，你必须调用releasepinglock允许该节点
	 * to be pinged in the future
	 * @return true if the node was not being pinged, false if it was already locked
	 * “如果节点没有被调用返回true，false如果它已锁定
	 */
	public boolean lockForPing() {
		return beingPinged.compareAndSet(false, true);
	}
	
	/**
	 * Signals this node is no longer being pinged
	 */
	public void releasePingLock() {
		beingPinged.set(false);
	}
	
	/**
	 * When a message from the wrapped node is received, use this method to
	 * record the time
	 * @return this for fluent interface
	 */
	public KadNode setNodeWasContacted() {
		lastContactTimestamp.set(System.currentTimeMillis());
		return this;
	}
	
	/**
	 * Get the last time a message was received from this node
	 * (the last time setNodeWasContacted was invoked)
	 * @return the last time setNodeWasContacted was invoked (in millies)
	 */
	public long getLastContact() {
		return lastContactTimestamp.get();
	}
	
	/**
	 * Check if we never received a message from this node
	 * @return true if no message was received from this node
	 */
	public boolean hasNeverContacted() {
		return lastContactTimestamp.get() == 0;
	}

	/**
	 * Check if the last message from this node was received less than the given time span
	 * @param validTimespan maximum time span in millies
	 * @return true if setNodeWasContacted was invoked in the passed validTimespan millies
	 */
	public boolean isPingStillValid(long validTimespan) {
		return lastContactTimestamp.get() + validTimespan > System.currentTimeMillis();
	}

	/**
	 * @return the wrapped node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Explicitly sets the last time a message was received from the wrapping node
	 * @param lastContact
	 */
	public void setNodeWasContacted(long lastContact) {
		lastContactTimestamp.set(lastContact);
	}
	
	@Override
	public String toString() {
		return getNode().toString();
	}

	/**
	 * @return true if a message was recieved from this node
	 */
	public boolean hasContacted() {
		return !hasNeverContacted();
	}

	/**
	 * Sets the last contact time to 0, which will cause
	 * hasNeverContacted to return true
	 */
	public void markDead() {
		setNodeWasContacted(0);
	}
}
