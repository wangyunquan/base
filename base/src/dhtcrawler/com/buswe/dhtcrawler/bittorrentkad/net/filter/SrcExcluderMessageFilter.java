package com.buswe.dhtcrawler.bittorrentkad.net.filter;

import com.buswe.dhtcrawler.Node;
import com.buswe.dhtcrawler.bittorrentkad.krpc.KadMessage;

/**
 * 只接受指定节点的消息
 */
public class SrcExcluderMessageFilter implements MessageFilter {

	private final Node src;
	
	public SrcExcluderMessageFilter(Node src) {
		this.src = src;
	}
	
	
	
	@Override
	public boolean shouldHandle(KadMessage m) {
		return !src.equals(m.getSrc());
	}
	
}
