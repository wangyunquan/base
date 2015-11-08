package com.buswe.dht.message;

import java.io.Serializable;

import com.buswe.dht.DHTConstant;
import com.buswe.dht.node.Node;

 

/**
 * Base class for all openkad messages. All messages must be in this package
 * 
 * kadmessage id也就是bittorrent中的tt
 */
public abstract class KadMessage implements Serializable,DHTConstant {
	
	protected static final long serialVersionUID = -6975403100655787398L;
	protected final String transaction;
	protected   Node src;// 目的地的节点信息

	protected KadMessage(String transaction, Node src) {
		this.transaction = transaction;
		this.src = src;
	}

	public void setNode(Node src) {
		this.src = src;
	}

	public Node getSrc() {
		return src;
	}

	public String getTransaction() {
		return transaction;
	}

	public abstract byte[] getBencodeData(Node localNode);// 对方的节点

}
