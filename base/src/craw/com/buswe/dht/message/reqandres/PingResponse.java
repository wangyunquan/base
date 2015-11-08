package com.buswe.dht.message.reqandres;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.buswe.dht.message.KadResponse;
import com.buswe.dht.node.Node;
import com.buswe.dht.util.ByteUtil;

 

/**
 * A ping response as defined in the kademlia protocol
 */
public class PingResponse extends KadResponse {

	private static final long serialVersionUID = -5054944878934710372L;

	public PingResponse(String transaction, Node src) {
		super(transaction, src);
	}

	@Override
	public byte[] getBencodeData(Node localNode) {
		BMap bMap = new HashBMap();
		bMap.put(TRANSACTION, ByteUtil.HexString2Bytes(transaction));
		bMap.put(Y, R);
		// ----------------------------------
		BMap a = new HashBMap();
		a.put(ID, localNode.getKey().getBytes());// 自己的节点id
		bMap.put(R, a);
		// ----------------------------------
		// System.out.println("响应ping-----------"+bMap);
		return BEncodedOutputStream.bencode(bMap);
	}

}
