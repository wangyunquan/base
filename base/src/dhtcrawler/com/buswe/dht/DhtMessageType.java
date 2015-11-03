package com.buswe.dht;

public enum DhtMessageType {

	PING("ping"),FIND_NODE("find_node"),
	GET_PEER("get_peer")
	,ANNOUNCE_PEER("announce_peer");
	private String messageType;
	private DhtMessageType(String messageType)
	{
		this.messageType=messageType;
	}
	public String getMessageType() {
		return messageType;
	}
	
}
