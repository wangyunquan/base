package com.konka.dhtsearch.bittorrentkad.net.filter;

import com.konka.dhtsearch.bittorrentkad.krpc.KadMessage;

/**
 * Reject all messages not in the given class
 * 类型过滤器
 * @author eyal.kibbar@gmail.com
 *
 */
public class TypeMessageFilter implements MessageFilter {

	private final Class<? extends KadMessage> clazz;
	
	public TypeMessageFilter(Class<? extends KadMessage> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public boolean shouldHandle(KadMessage m) {
//		System.out.println(clazz);
//		System.out.println(m.getClass());
		return m.getClass().equals(clazz);
	}

	
}
