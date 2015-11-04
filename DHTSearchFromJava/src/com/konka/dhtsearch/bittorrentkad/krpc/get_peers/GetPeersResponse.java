package com.konka.dhtsearch.bittorrentkad.krpc.get_peers;

import java.io.Serializable;
import java.util.List;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.krpc.KadResponse;
import com.konka.dhtsearch.util.Util;

/**
 * A message containing arbitrary data to be used by the KeybasedRouting.sendRequest methods
 * 
 * @author eyal.kibbar@gmail.com
 *
 */
public class GetPeersResponse extends KadResponse {

	private static final long serialVersionUID = -4479208136049358778L;
	List<Node> nodes;
	private Serializable content;

	public GetPeersResponse(String transaction, Node src) {
		super(transaction, src);
	}

	public Serializable getContent() {
		return content;
	}

	public GetPeersResponse setNodes(List<Node> nodes) {
		this.nodes = nodes;
		return this;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public GetPeersResponse setContent(Serializable content) {
		this.content = content;
		return this;
	}

	// Response with closest nodes = {"t":"aa", "y":"r", "r": {"id":"abcdefghij0123456789", "token":"aoeusnth", "nodes": "def456..."}}
	@Override
	public byte[] getBencodeData(Node localNode) {

		BMap bMap = new HashBMap();
		bMap.put(TRANSACTION, Util.HexString2Bytes(transaction));
		bMap.put(Y, R);
		// ----------------------------------
		BMap a = new HashBMap();
		a.put(ID, localNode.getKey().getBytes());// 自己的节点id

		byte[] nodesbyte = Util.nodesToBytes(getNodes());
		a.put("token", "cgpddd".getBytes());// 对方要查找的节点id
		a.put(NODES, nodesbyte);// 对方要查找的节点id
		bMap.put("r", a);
		// ----------------------------------
		// System.out.println("响应getpeer-----------"+bMap);
		return BEncodedOutputStream.bencode(bMap);
	}

}
