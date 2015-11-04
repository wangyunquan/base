package com.konka.dhtsearch.bittorrentkad.krpc.find_node;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.konka.dhtsearch.Key;
import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.krpc.KadRequest;
import com.konka.dhtsearch.util.Util;

/**
 * A findNode request as defined in the kademlia protocol
 * 
 * 接收请求主要接受两个参数 1，transaction，2 发送者的node（其他参数 对方id）
 */
public class FindNodeRequest extends KadRequest {

	private static final long serialVersionUID = -7084922793331210968L;
	private Key key;
	private boolean searchCache;

	public FindNodeRequest(String transaction, Node src) {
		super(transaction, src);
	}

	public static FindNodeRequest creatLocalFindNodeRequest(Node src) {

		FindNodeRequest findNodeRequest = new FindNodeRequest(Util.random_tranctionId(4), src);
		return findNodeRequest;
	}

	public Key getKey() {
		return key;
	}

	public FindNodeRequest setKey(Key key) {
		this.key = key;
		return this;
	}

	@Override
	public FindNodeResponse generateResponse(Node localNode) {// 回复对方请求时候调用
		return new FindNodeResponse(getTransaction(), localNode);
	}

	public FindNodeRequest setSearchCache(boolean searchCache) {
		this.searchCache = searchCache;
		return this;
	}

	public boolean shouldSearchCache() {
		return searchCache;
	}

	/**
	 * 编码
	 */
	@Override
	public byte[] getBencodeData(Node localNode) {
		BMap bMap = new HashBMap();
		bMap.put(TRANSACTION, Util.HexString2Bytes(transaction));
		bMap.put(Y, Q);
		bMap.put(Q, FIND_NODE);
		// ----------------------------------
		BMap a = new HashBMap();
		// a.put("id", Util.random_tranctionId());// 自己的节点id
		// a.put("id", AppManager.getKeyFactory().generate().getBytes());// 自己的节点id
		a.put(ID, localNode.getKey().getBytes());// 自己的节点id
		// a.put("target", getSrc().getKey().getBytes());// 对方的节点id **这里应该是你要查询的id
		// System.out.println("空吗="+getKey());
		a.put(TARGET, getSrc().getKey().getBytes());// 对方的节点id **这里应该是你要查询的id
		bMap.put(A, a);
		// ----------------------------------
		// System.out.println("发送findnode请求-----------"+bMap);
		// System.out.println("findnode请求的编码字符串="+new String(new BEncoder().bencode(bMap)));
		byte[] bb = BEncodedOutputStream.bencode(bMap);
		return bb;
	}

}
