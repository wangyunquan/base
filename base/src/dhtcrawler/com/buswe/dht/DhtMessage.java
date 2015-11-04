package com.buswe.dht;

import java.util.Map;

public interface DhtMessage {
	
	public DhtMessageType getMessageType();
	
	Map<String, Object> getMessageContent();
	
}
