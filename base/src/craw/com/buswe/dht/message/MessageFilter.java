package com.buswe.dht.message;

 

/**
 * 过滤器接口
 * @author eyal.kibbar@gmail.com
 *
 */
public interface MessageFilter {

	boolean shouldHandle(KadMessage m);
	
}
