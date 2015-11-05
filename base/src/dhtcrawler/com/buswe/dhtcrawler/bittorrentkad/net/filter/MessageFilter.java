package com.buswe.dhtcrawler.bittorrentkad.net.filter;

import com.buswe.dhtcrawler.bittorrentkad.krpc.KadMessage;

/**
 * 过滤器接口
 * @author eyal.kibbar@gmail.com
 *
 */
public interface MessageFilter {

	boolean shouldHandle(KadMessage m);
	
}
