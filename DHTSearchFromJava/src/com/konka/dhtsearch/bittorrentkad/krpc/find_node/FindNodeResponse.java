package com.konka.dhtsearch.bittorrentkad.krpc.find_node;

import java.util.List;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.krpc.KadResponse;
import com.konka.dhtsearch.util.Util;

/**
 * A findNode response as defined in the kademlia protocol
 * 
 */
public class FindNodeResponse extends KadResponse {

	private static final long serialVersionUID = 2103126060969733458L;
	private List<Node> nodes;
	private boolean cachedResults;
	// not in openKad - for vision.
	private boolean needed;

	public FindNodeResponse(String transaction, Node src) {
		super(transaction, src);
	}

	public FindNodeResponse setNodes(List<Node> nodes) {
		this.nodes = nodes;
		return this;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public FindNodeResponse setCachedResults(boolean cachedResults) {
		this.cachedResults = cachedResults;
		return this;
	}

	public boolean isCachedResults() {
		return cachedResults;
	}

	public boolean isNeeeded() {
		return needed;
	}

	public void setNeeeded(boolean neeeded) {
		this.needed = neeeded;
	}

	@Override
	public byte[] getBencodeData(Node localNode) {

		BMap bMap = new HashBMap();
		bMap.put(TRANSACTION, Util.HexString2Bytes(transaction));
		bMap.put(Y, R);
		// ----------------------------------
		BMap a = new HashBMap();
		a.put(ID, localNode.getKey().getBytes());// 自己的节点id

		byte[] nodesbyte = Util.nodesToBytes(getNodes());
		a.put(NODES, nodesbyte);// 对方要查找的节点id
		bMap.put("r", a);
		// ----------------------------------
		// System.out.println("响应findnode-----------"+bMap);
		return BEncodedOutputStream.bencode(bMap);
	}
}
