package com.konka.dhtsearch.bittorrentkad.krpc.ping;

import org.yaircc.torrent.bencoding.BEncodedOutputStream;
import org.yaircc.torrent.bencoding.BMap;
import org.yaircc.torrent.bencoding.HashBMap;

import com.konka.dhtsearch.Node;
import com.konka.dhtsearch.bittorrentkad.krpc.KadRequest;
import com.konka.dhtsearch.util.Util;

/**
 * A ping request as defined in the kademlia protocol
 * 
 */
public class PingRequest extends KadRequest {

	private static final long serialVersionUID = 4646089493549742900L;

	public PingRequest(String transaction, Node src) {
		super(transaction, src);
	}

	@Override
	public PingResponse generateResponse(Node localNode) {
		return new PingResponse(getTransaction(), localNode);
	}

	@Override
	public byte[] getBencodeData(Node localNode) {// ping 不需要对方的节点
		BMap bMap = new HashBMap();
		bMap.put(TRANSACTION, Util.HexString2Bytes(transaction));
		bMap.put(Y, Q);
		bMap.put(Q, PING);
		// ----------------------------------
		BMap a = new HashBMap();
		a.put(ID, localNode.getKey().getBytes());// 自己的节点id
		bMap.put(A, a);
		// ----------------------------------
		return BEncodedOutputStream.bencode(bMap);
	}

}
