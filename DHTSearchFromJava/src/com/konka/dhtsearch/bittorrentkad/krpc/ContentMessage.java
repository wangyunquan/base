package com.konka.dhtsearch.bittorrentkad.krpc;

import com.konka.dhtsearch.Node;

/**
 * A message containing arbitrary data to be used by the KeybasedRouting.sendMessage method
 * 
 *
 */
public class ContentMessage extends KadMessage {
	// "t" with a string value representing a transaction ID

	private static final long serialVersionUID = -57547778613163861L;

	private String tag;

	public ContentMessage(String transaction, Node src) {
		super(transaction, src);
	}

	/**
	 * Every content request has a tag associated with it. This is the same tag given in the KeybasedRouting.sendMessage or sendRequest methods.
	 * 
	 * @return the message's tag
	 */
	public String getTag() {
		return tag;
	}

	public ContentMessage setTag(String tag) {
		this.tag = tag;
		return this;
	}

	@Override
	public byte[] getBencodeData(Node localNode) {
		// TODO Auto-generated method stub
		return null;
	}

}
