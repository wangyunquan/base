package com.buswe.dht.message.reqandres;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.buswe.dht.message.KadRequest;
import com.buswe.dht.node.Key;
import com.buswe.dht.node.Node;
import com.buswe.dht.util.ByteUtil;

 

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

		/**
		 * 实际上是向目标节点查询它自己，会返回来8个？还是直接去查询它相邻的8个节点？
		 */
		FindNodeRequest findNodeRequest = new FindNodeRequest(ByteUtil.random_tranctionId(4), src);
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
		bMap.put(TRANSACTION, ByteUtil.HexString2Bytes(transaction));
		bMap.put(Y, Q);
		bMap.put(Q, FIND_NODE);
		// ----------------------------------
		BMap a = new HashBMap();
		a.put(ID, localNode.getKey().getBytes());// 自己的节点id
	//	a.put(TARGET, getSrc().getKey().getBytes());// 对方的节点id **这里应该是你要查询的id
		a.put(TARGET, localNode.getKey().getBytes());
		//不停的查询自己，理论上就会返回离自己越来越近的节点,上面的方法是参照别人的实现
		bMap.put(A, a);
		byte[] bb = BEncodedOutputStream.bencode(bMap);
		return bb;
	}

}
