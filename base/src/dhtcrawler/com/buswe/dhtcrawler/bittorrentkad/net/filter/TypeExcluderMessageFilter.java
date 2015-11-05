package com.buswe.dhtcrawler.bittorrentkad.net.filter;

import com.buswe.dhtcrawler.bittorrentkad.krpc.KadMessage;

/**
 * Rejects all messages in the given class
 * 过滤给定类的所有消息
 *
 */
public class TypeExcluderMessageFilter implements MessageFilter {

	private final Class<? extends KadMessage> clazz;
	
	public TypeExcluderMessageFilter(Class<? extends KadMessage> clazz) {
		this.clazz = clazz;
	}
	@Override
	public boolean shouldHandle(KadMessage m) {
		return !m.getClass().equals(clazz);
	}

}
