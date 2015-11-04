package com.konka.dhtsearch.bittorrentkad.net.filter;

import com.konka.dhtsearch.bittorrentkad.krpc.KadMessage;

/**
 * 消息过滤器
 *
 */
public class TagMessageFilter implements MessageFilter {

	private final String tag;
	
	
	public TagMessageFilter(String tag) {
		this.tag = tag;
	}
	
	@Override
	public boolean shouldHandle(KadMessage m) {
//		String tag = null;
//		if (m instanceof GetPeersRequest)
////			tag = ((GetPeersRequest)m).getTag();
//		else if (m instanceof ContentMessage)
//			tag = ((ContentMessage)m).getTag();
//		else
//			return false;
		
		return this.tag.equals(tag);
	}

}
