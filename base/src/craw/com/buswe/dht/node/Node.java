package com.buswe.dht.node;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Represents contact information about a node in the network.
 * This class is serializable and can be sent/saved for other/later use by
 * any KeybasedRouting local or remote.
 * 
 *
 */
public class Node implements Serializable,Comparable<Node> {

	private static final long serialVersionUID = 2520444508318328765L;
	
	private final Key key;
	private InetAddress addr = null;
	private Integer port;
	
	public InetAddress getAddr() {
		return addr;
	}
	/**
	 * Creates a dummy node with no key in it
	 */
	public Node() {
		this(null);
	}
	public Node setSocketAddress(InetSocketAddress socketAddress){
		addr=socketAddress.getAddress();
		port=socketAddress.getPort();
		return this;
	}
	/**
	 * Create a node with only a key and no IP address
	 * @param key
	 */
	public Node(Key key) {
		this.key = key;
	}
	
	/**
	 * 
	 * @return the node's key
	 */
	public Key getKey() {
		return key;
	}
	
	
	/**
	 * Creates a SocketAddress from a protocol name
	 * @param scheme the protocol name
	 * @return
	 */
	public SocketAddress getSocketAddress() {
		return new InetSocketAddress(addr,port);
	}
	
	public Node setPoint(Integer point) {
		this.port = point;
		return this;
	}
	/**
	 * 
	 * @return the IP address of this node
	 */
	public InetAddress getInetAddress() {
		return addr;
	}
	
	
	/**
	 * Sets the IP address of this node
	 * @param addr
	 */
	public Node setInetAddress(InetAddress addr) {
		this.addr = addr;
		return this;
	}
	
	/**
	 * Get the port number of a given protocol name.
	 * May throw a NullPointerException if protocol was not added
	 * @param scheme the protocol name
	 * @return the port number
	 */
	public int getPort() {
		return port;
	}
	
	@Override
	public String toString() {
		return getKey().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + key.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return getKey().equals(other.getKey());
	}

	@Override
	public int compareTo(Node node) {
		return key.compareTo(node.key);
	}
	
	
	
}
