package com.buswe.dht;

import java.util.Map;

import com.dampcake.bencode.BencodeInputStream;

public class DhtMessageFactory {

	public DhtMessage createMessage(BencodeInputStream din) throws Exception {
		Map<String, Object> obj=	din.readDictionary();
		//TODO
		
		
		System.out.println(obj);
		return null;
	}

	public DhtReceiver createReceiver(DhtMessageType comm, DhtServer dhtServer) {
 //TODO
		return null;
	}
	
	

}
