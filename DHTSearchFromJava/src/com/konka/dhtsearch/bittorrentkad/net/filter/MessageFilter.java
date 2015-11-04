package com.konka.dhtsearch.bittorrentkad.net.filter;

import com.konka.dhtsearch.bittorrentkad.krpc.KadMessage;

/**
 * 过滤器接口
 * @author eyal.kibbar@gmail.com
 *
 */
public interface MessageFilter {

	boolean shouldHandle(KadMessage m);
	
}
